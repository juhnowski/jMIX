package mix;
import java.util.*;
import mix.OpCode;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Asm {
    public Word parse(String str, HashMap<String, Integer> variables, HashMap<String, Integer> labels){
        int line = 0;
        String label="";
        ArrayList<String> tmp = new ArrayList<>();

        if (str.length() <5) return null;

        //01 * ПРИМЕР ПРОГРАММЫ ... ТАБЛИЦА ПРОСТЫХ ЧИСЕЛ
        if (str.contains("\\*")) return null;

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
        

        if (OpCode.strCodes.containsKey(tmp.get(1)) ){
            String addr = tmp.get(2);
            for (String sv:variables.keySet()){
                if (addr.contains(sv)) {
                    addr = addr.replace(sv, variables.get(sv).toString(line));
                }
            }
            if (tmp.get(1).equals("IOC")) {
                System.out.println(tmp.get(1)+" "+addr);
                Word tmp_w = OpCode.toWord(tmp.get(1)+" 0");
                addr = addr.replace("(", "");
                addr = addr.replace(")", "");
                System.out.println("addr="+addr);
                tmp_w.setF(Integer.parseInt(addr));
                return tmp_w; 
            }
            
            return OpCode.toWord(tmp.get(1)+" "+addr);
        } else {
            if (tmp.get(1).contains("ORIG")) {
                labels.put("ORIG", Integer.parseInt(tmp.get(2)));
                return null;
            }
            
            if (tmp.get(1).contains("CON")) {
                return new Word(Integer.parseInt(tmp.get(2)),6);
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

                int[] val = {0,0,0,0,0};

                char[] charArray = s_alf.toString().toCharArray();
                for (int i=0; i<6; i++){
                    val[i] = OpCode.charCodes.get(String.valueOf(charArray[i]));
                }
                new Word(6, val);
            }

            if (tmp.get(1).contains("END")) {
                return new Word(labels.get("ORIG"),6);
            }
            
            label = tmp.get(1);
        }

        if (OpCode.strCodes.containsKey(tmp.get(2)) ){
            String addr = tmp.get(3);
            for (String sv:variables.keySet()){
                if (addr.contains(sv)) {
                    addr = addr.replace(sv, variables.get(sv).toString(line));
                }
            }
            if (tmp.get(2).equals("IOC")) {
                System.out.println(tmp.get(2)+" "+addr);
                Word tmp_w = OpCode.toWord(tmp.get(2)+" 0");
                addr = addr.replace("(", "");
                addr = addr.replace(")", "");
                System.out.println("addr="+addr);
                tmp_w.setF(Integer.parseInt(addr));
                return tmp_w; 
            }
            
            return OpCode.toWord(tmp.get(2)+" "+addr);
            //return OpCode.toWord(tmp.get(1)+" "+tmp.get(2));
        } else {
            if (label.length()>0){
                if (label.contains("H")){
                    labels.put(tmp.get(2), line);
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
            labels.put("ORIG", Integer.parseInt(tmp.get(3)));
            return null;
        }
        
        if (tmp.get(2).contains("CON")) {
            return new Word(Integer.parseInt(tmp.get(3)),6);
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

            int[] val = {0,0,0,0,0};

            char[] charArray = s_alf.toString().toCharArray();
            for (int i=0; i<6; i++){
                val[i] = OpCode.charCodes.get(String.valueOf(charArray[i]));
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

    public static void main(String[] args) {
        HashMap<String, Integer> variables = new HashMap<>();
        HashMap<String, Integer> labels = new HashMap<>();
        Asm asm = new Asm();
        Word w = asm.parse("03  L       EQU     500", variables, labels);
        System.out.println(w);
    }
}
