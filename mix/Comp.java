package mix;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class Comp {
    public Word rA = new Word(6);
    public Word rX = new Word(6);
    public Word rI1 = new Word(3);
    public Word rI2 = new Word(3);
    public Word rI3 = new Word(3);
    public Word rI4 = new Word(3);
    public Word rI5 = new Word(3);
    public Word rI6 = new Word(3);
    public Word rJ = new Word(3);
    public boolean fp;
    public boolean CI_L;
    public boolean CI_E;
    public boolean CI_G;
    public int maxMem = 4000;
    public ArrayList<Word> mem = new ArrayList();
    public int maxU = 20;
    public ArrayList<Integer> u = new ArrayList();
    public Asm asm = new Asm();
    public HashMap<String, Integer> variables = new HashMap<>();
    public HashMap<Integer, Integer> labels = new HashMap<>();

    ArrayList<String> lines = new ArrayList<>();

    public Comp(){
        for (int i=0; i<maxMem; i++){
            mem.add(new Word(6));
        }
    }

    public int parse_int(String str){
        try {
            return Integer.parseInt(str);
        } catch (Exception e){
            Integer var = variables.get(str);
            if (var != null) {
                return var;
            }
        }

        String[] s = str.split("\\+");
        if (s.length>1){
            int sum = 0;
            for (int i=0; i<s.length; i++){
                sum += parse_int(s[i]);
            }

            return sum;
        }

        String[] sm = str.split("\\-");
        if (sm.length>1){
            int dif = 0;
            for (int i=0; i<sm.length; i++){
                dif -= parse_int(sm[i]);
            }
            return dif;
        }

        return 0;
    }

    public void loadAsm(String fileName) throws Exception{
        

        lines.addAll(Files.readAllLines(Paths.get(fileName)));
        
        int con_count = 0;
        int s = lines.size();
        for (int i=0; i<s; i++) {
            String l = lines.get(i);
            if (l.contains("\\=")) {
                String[] s_con = l.split("\\=");
                String[] s_con1 = s_con[1].split("\\=");
                String eq = s_con1[0];
                String alias = "con"+(++con_count);
                l=l.replace("="+eq+"=", alias);
                int last_line = Integer.parseInt(lines.getLast().split(" ")[0]);
                lines.add(last_line +" "+alias+" CON "+eq);
                lines.add(i,l);
                variables.put(alias, parse_int(eq) );
            };
        }

        int idx = 0;
        for (int i=0; i<lines.size(); i++) {
           Word w = asm.parse(lines.get(i), variables, labels);
           if (w!=null) {
                if (idx==0) {
                    idx = variables.get("ORIG");
                } else {
                    idx++;
                }
                mem.add(idx, w);
           }
        }
    }


    public void printMem(int from, int to){
        System.out.println("------------- mem -----------------");
        for (int i=from; i<to; i++){
            System.out.print(i+": \t"+mem.get(i)+"\t");
            System.out.print(OpCode.toText(mem.get(i))+"\t");
            int ln = i-3000+8;
            if (ln<lines.size()) {
                System.out.println("\t * "+lines.get(ln));
            }
            
        }
    }

    public static void main(String[] args) {
        Comp c = new Comp();
        
        try {
            c.loadAsm("simple_num.mix");

            // System.out.println("variables:");
            // for (String s:c.variables.keySet()){
            //     System.out.println(s+":"+c.variables.get(s));
            // }

            // System.out.println("labels:");
            // for (String s:c.labels.keySet()){
            //     System.out.println(s+":"+c.labels.get(s));
            // }

            c.printMem(3000, 3060);
            c.asm.print_variables(c.variables);
            c.asm.print_lables(c.labels);
        } catch (Exception e){
            e.printStackTrace();
        }
        
        
        
    }
}
