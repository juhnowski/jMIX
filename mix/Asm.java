package mix;
import java.util.*;
import mix.OpCode;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Asm {

    public int parse_int(String str, HashMap<String, Integer> variables, HashMap<Integer, Integer> labels){
        try {
            return Integer.parseInt(str);
        } catch (Exception e){
            Integer var = variables.get(str);
            if (var != null) {
                return var;
            }

            Integer lbl = labels.get(str);
            if (lbl != null) {
                return lbl;
            }
        }

        String[] s = str.split("\\+");
        if (s.length>1){
            int sum = parse_int(s[0], variables, labels);
            for (int i=1; i<s.length; i++){
                sum += parse_int(s[i], variables, labels);
            }

            return sum;
        }

        String[] sm = str.split("\\-");
        if (sm.length>1){
            int dif = parse_int(sm[0], variables, labels);;
            for (int i=1; i<sm.length; i++){
                dif -= parse_int(sm[i], variables, labels);
            }
            return dif;
        }

        return 0;
    }

    private int isLabel(String str) {
        String s = str.trim();
        try {
            int n = Integer.parseInt( String.valueOf(s.charAt(0))); 
            if (s.length() == 2 &&
            s.endsWith("H")
            ){
                return n;
            } else {
                return 0;
            }
        } catch (Exception e){
            return 0;
        }
    }

    public Word parse(String str, HashMap<String, Integer> variables, HashMap<Integer, Integer> labels, HashMap<Integer, String> asm_ops){
        int line = 0;
        String label="";
        ArrayList<String> tmp = new ArrayList<>();

        if (str.length() <5) return null;

        String[] s = str.split(" ");
        for(String st:s){
            if (st.trim().length()>0){
                tmp.add(st);       
                System.out.println(st);
            }
        }

        try {
            line = Integer.parseInt(tmp.get(0));
        } catch (Exception e) {
            System.out.println("tmp.get(0)="+tmp.get(0));
            e.printStackTrace();
        }
        
        // if (str.contains("*")) {
        //     asm_ops.put(line, "*");
        //     return null;
        // }

        if (tmp.get(1).contains("ORIG")) {
            int orig_num = parse_int(tmp.get(2), variables, labels); 
            asm_ops.put(line, "ORIG");
            return new Word(orig_num, 6);
        } 

        if (tmp.get(1).contains("ALF")) {
            char[] chars = tmp.get(2).toCharArray();
            ArrayList<String> charList = new ArrayList<>();
            for (char c:chars){
                charList.add(""+c);
            }

            int[] val = {0,0,0,0,0,0};
            for (int i=0; i<charList.size(); i++) {
                val[i] = OpCode.charCodes.get(charList.get(i));
            }

            asm_ops.put(line, "ALF");

            return new Word( 6, val);
        }

        if (tmp.get(1).contains("CON")) {
            
            asm_ops.put(line, "CON");
        }

        if (tmp.get(1).contains("CHAR")||
            tmp.get(1).contains("HLT") ||
            tmp.get(1).contains("NUM")
            ) {
            int[] val = {0,0,0,0,0,0};
            OpCode ch_op = OpCode.strCodes.get(tmp.get(1));
            val[5] = ch_op.C;
            val[4] = ch_op.F;
            return new Word(6, val);
        }


        if (OpCode.strCodes.containsKey(tmp.get(1)) ){
            String addr = tmp.get(2);
            // for (String sv:variables.keySet()){
            //     if (addr.contains(sv)) {
            //         addr = addr.replace(sv, variables.get(sv).toString(line));
            //     }
            // }
            if (addr.contains("=")) {
                String[] eqa = addr.split("=");
                addr = ""+parse_int(eqa[1], variables, labels);
            }

            //int int_add
            if (tmp.get(1).equals("IOC")||tmp.get(1).equals("OUT")) {
                System.out.println(tmp.get(1)+" "+addr);
                
                String tmp_a[] = addr.split("\\(");
                String tmp_I = tmp_a[0]; 
                
                Word tmp_w = OpCode.toWord(tmp.get(1)+" " + tmp_I, variables);
                System.out.println("tmp_I ="+tmp_I);
                String tmp_a1 = tmp_a[1].replace("(", "");
                tmp_a1 = tmp_a1.replace(")", "");
                System.out.println("tmp_a1="+tmp_a1);
                tmp_w.setF(parse_int(tmp_a1, variables, labels));
                return tmp_w; 
            }
            //проверить в лейблах и заменить в адресе
            return OpCode.toWord(tmp.get(1)+" "+addr, variables);
        } else {
            if (tmp.get(1).contains("ORIG")) {

                variables.put("ORIG", parse_int(tmp.get(2), variables, labels));
                return null;
            }
            
            if (tmp.get(1).contains("CON")) {
                return new Word(parse_int(tmp.get(2), variables, labels),6);
            }

            if (tmp.get(1).contains("ALF")) {
                StringBuilder s_alf = new StringBuilder();
                s_alf.append(tmp.get(2));
                if (tmp.size()>2){
                    for (int a = 3; a<tmp.size(); a++) {
                        s_alf.append(" ").append(tmp.get(a)); 
                    }
                }
                
                while(s_alf.length()<6) {
                    s_alf.append(" ");
                }

                int[] val = {0,0,0,0,0,0};

                char[] charArray = s_alf.toString().toCharArray();
                for (int i=0; i<5; i++){
                    val[i+1] = OpCode.charCodes.get(String.valueOf(charArray[i]));
                }
                new Word(6, val);
            }

            if (tmp.get(1).contains("END")) {
                System.out.println("tmp.get(2)"+tmp.get(2));
                System.out.println("parse_int(tmp.get(2)="+parse_int(tmp.get(2), variables, labels));
                return new Word(parse_int(tmp.get(2), variables, labels) ,6);
            }
            
            
            int num = isLabel(tmp.get(1));
            if (num==0){
                if (tmp.get(2).equals("EQU")){
                    variables.put(tmp.get(1), parse_int(tmp.get(3), variables, labels));
                    return null;
                } else {
                    variables.put(tmp.get(1), line);
                }
            } else {
                labels.put(line, num);
            }
        }

        if (OpCode.strCodes.containsKey(tmp.get(2)) ){
            String addr = tmp.get(3);
            for (String sv:variables.keySet()){
                if (addr.contains(sv)) {
                    addr = addr.replace(sv, variables.get(sv).toString(line));
                }
            }

            if (tmp.get(2).equals("IOC")) {
               // System.out.println(tmp.get(2)+" "+addr);
                String ustr = tmp.get(3);  
                String[] us = ustr.split("\\(");
                ustr = us[1].replace(")", "");
                
                Word tmp_w = OpCode.toWord(tmp.get(2)+" 0", variables);
                System.out.println("ustr =" + ustr);
                int u = parse_int(ustr,variables, labels);
                System.out.println("u =" + u);
                System.out.println("1 IOC="+tmp_w);
                tmp_w.setF(u);
                System.out.println("2 IOC="+tmp_w);
                return tmp_w; 
            }
            
            return OpCode.toWord(tmp.get(2)+" "+addr, variables);
            //return OpCode.toWord(tmp.get(1)+" "+tmp.get(2));
        } else {
            if (label.length()>0){
                int num = isLabel(tmp.get(1));
                if (num==0){
                    if (tmp.get(2).equals("EQU")){
                        variables.put(tmp.get(1), parse_int(tmp.get(3), variables, labels));
                        return null;
                    } else {
                        variables.put(tmp.get(1), line);
                    }
                } else {
                    labels.put(line, num);
                }



                if (num>0){
                    labels.put(line,num);
                    return null;
                } else {
                    if (tmp.get(2).contains("EQU")) {
                        String eqs = tmp.get(3);
                        int vv=0;
                        try {
                            vv= Integer.parseInt(eqs);
                        } catch(Exception e) {
                            for (String sv:variables.keySet()){
                                if (eqs.contains(sv)) {
                                    eqs = eqs.replace(sv, variables.get(sv).toString(line));
                                    try {
                                        if (eqs.contains("+")){
                                            String[] ev = eqs.split("\\+");
                                            variables.put(label, Integer.parseInt(ev[0])+Integer.parseInt(ev[1]));
                                        } else {
                                            String[] ev = eqs.split("\\-");
                                            variables.put(label, Integer.parseInt(ev[0])-Integer.parseInt(ev[1]));
                                        }
                                        return null;
                                    } catch (Exception ex){
                                        ex.printStackTrace();
                                        return null;
                                    }
                                    
                                }
                                // System.out.println(s+":"+c.variables.get(s));
                            }
                        }
                        variables.put(label, vv);
                        return null;
                    }
                }
            }
        }

        if (tmp.get(2).contains("ORIG")) {
            variables.put("ORIG", parse_int(tmp.get(3), variables, labels));
            return null;
        }
        
        if (tmp.get(2).contains("CON")) {
            return new Word(parse_int(tmp.get(3), variables, labels),6);
        }

        if (tmp.get(2).contains("ALF")) {
            StringBuilder s_alf = new StringBuilder();
            s_alf.append(tmp.get(3));
            if (tmp.size()>2){
                for (int a = 3; a<tmp.size(); a++) {
                    s_alf.append(" ").append(tmp.get(a)); 
                }
            }
            
            while(s_alf.length()<6) {
                s_alf.append(" ");
            }

            int[] val = {0,0,0,0,0,0};

            char[] charArray = s_alf.toString().toCharArray();
            for (int i=0; i<5; i++){
                System.out.println("i="+i);
                if (i<charArray.length-1) {
                    System.out.println("charArray[i]=|"+charArray[i]+"|");
                    val[i+1] = OpCode.charCodes.get(String.valueOf(charArray[i]));
                } else {
                    val[i+1] = 0;
                }
                
            }
            new Word(6, val);
        }

        if (tmp.get(2).contains("END")) {
            return new Word(labels.get("ORIG"),6);
        }

        return null;
    }


    public String toString() {
        System.out.println("rA=");
        return "";
    }

    public void print_variables(HashMap<String, Integer> variables){
        System.out.println("----- Variables -----");
        if (variables.size()>0){
            variables.forEach((k,v)->{System.out.println(k+"="+v);});
        } else {
            System.out.println("empty");
        }
    }

    public void print_lables(HashMap<Integer, Integer> labels){
        System.out.println("----- Lables -----");
        if (labels.size()>0) {
            labels.forEach((k,v)->{System.out.println(k+"="+v);});
        } else {
            System.out.println("empty");
        }
    }

    public static void main(String[] args) {
        HashMap<String, Integer> variables = new HashMap<>();
        HashMap<Integer, Integer> labels = new HashMap<>();
        HashMap<Integer, String> asm_ops = new HashMap<>();

        Asm asm = new Asm();
        variables.put("L", 500);
        variables.put("PRIME", -1);
        variables.put("PRINTER", 18);
        

        asm.print_variables(variables);
        asm.print_lables(labels);

        // Word w = asm.parse("10          LD1     =1-L=", variables, labels);
        // System.out.println("w="+w);

        // Word w1 = asm.parse("13          ST2     PRIME+L,1", variables, labels);
        // System.out.println("w="+w);
        
        // Word w2 = asm.parse("35          OUT     0,4(PRINTER)", variables, labels);
        // System.out.println("w="+w2);

        // Word w3 = asm.parse("09  START   IOC     0(PRINTER)", variables, labels);
        // System.out.println("w="+w3);
        // System.out.println("w="+w3.toString());

        // Word w4 = asm.parse("38          HLT", variables, labels);
        // System.out.println("w="+w4);
        // System.out.println("w="+w4.toString());
        
        Word w5 = asm.parse("47          ALF     RIMES", variables, labels, asm_ops);
        System.out.println("w="+w5);
        System.out.println("w="+w5.toString());
    }
}
