package mix;

import java.lang.classfile.Opcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.*;

class OpCode {
    public static HashMap<String, OpCode> strCodes;
    public static HashMap<Integer, ArrayList<OpCode>> opCodes;
    public static HashMap<String, Integer> charCodes;

    public int C; // - код операции после команды (5:5)
    public int F; // - уточнение кода операции, после команды (4:4)
    public int M; // - адрес команды после индексации 
    public int V; // - содержимое поля F ячейки M
    public String OP; // - символический код операции
    public int LR;// - стандартное значение F = 8L+R
    public int L;
    public int R;
    public int t; // - время выполнения
    public int T; // - время блокировки

    public OpCode(){}

    public OpCode(int C, String OP, int F, int t){
        this.C = C;
        this.OP = OP;
        this.F = F;
        this.t = t;
    }

    public OpCode(int C, String OP, int L, int R, int t){
        this.C = C;
        this.OP = OP;
        this.L = L;
        this.R = R;
        this.LR = 8*L+R;
        this.t = t;
    }

    public static ArrayList<OpCode> ops = new ArrayList<>();
    static {
        
        ops.add(new OpCode(0, "NOP", -1,1));           //0   NOP         1 
        ops.add(new OpCode(1, "ADD", 0,5,2));     //1   ADD(0:5)    2
        ops.add(new OpCode(1, "FADD", 6, 2));       //1   FADD(6)     2

        ops.add(new OpCode(2, "SUB", 0,5,2));       //2   SUB(0:5)    2
        ops.add(new OpCode(2, "FSUB", 6,2));         //2    FSUB(6)     2
    
        ops.add(new OpCode(3, "MUL", 0,5,10));     //3   MUL(0:5)    10
        ops.add(new OpCode(3, "FMUL", 6,10));       //3    FMUL(6)     10
    
        ops.add(new OpCode(4, "DIV", 0,5, 12));   //4   DIV(0:5)    12
        ops.add(new OpCode(4, "FDIV", 6,12));       //4    FDIV(6)     12
    
        ops.add(new OpCode(5, "NUM", 0,10));        //5   NUM(0)      10
        ops.add(new OpCode(5, "CHAR", 1,10));       //5    CHAR(1)     10
        ops.add(new OpCode(5, "HLT", 2,10));       //5    HLT(2)      10
    
        ops.add(new OpCode(6, "SLA", 0,2));         //6   SLA(0)      2
        ops.add(new OpCode(6, "SRA", 1,2));         //6    SRA(1)      2
        ops.add(new OpCode(6, "SLAX", 2,2));        //6    SLAX(2)     2
        ops.add(new OpCode(6, "SRAX", 3,2));        //6    SRAX(3)     2
        ops.add(new OpCode(6, "SLC", 4,2));         //6    SLC(4)      2
        ops.add(new OpCode(6, "SRC", 5,2));         //6    SRC(5)      2
        
        ops.add(new OpCode(7, "MOVE", 1,-1));         //7   MOVE(1)     1+2*F
        ops.add(new OpCode(8, "LDA", 0,5,2));     //8   LDA(0:5)    2
        
        ops.add(new OpCode(9, "LD1", 0,5,2));     //9   LD1(0:5)    2
        ops.add(new OpCode(10, "LD2", 0,5,2));    //10  LD2(0:5)    2
        ops.add(new OpCode(11, "LD3", 0,5,2));    //11  LD3(0:5)    2
        ops.add(new OpCode(12, "LD4", 0,5,2));    //12  LD4(0:5)    2
        ops.add(new OpCode(13, "LD5", 0,5,2));    //13  LD5(0:5)    2
        ops.add(new OpCode(14, "LD6", 0,5,2));    //14  LD6(0:5)    2
        ops.add(new OpCode(15, "LDX", 0,5,2));    //15  LDX(0:5)    2
    
        ops.add(new OpCode(16, "LDAN", 0,5,2));   //16  LDAN(0:5)   2
        ops.add(new OpCode(17, "LD1N", 0,5,2));   //17  LD1N(0:5)   2
        ops.add(new OpCode(18, "LD2N", 0,5,2));   //18  LD2N(0:5)   2
        ops.add(new OpCode(19, "LD3N", 0,5,2));   //19  LD3N(0:5)   2
        ops.add(new OpCode(20, "LD4N", 0,5,2));   //20  LD4N(0:5)   2
        ops.add(new OpCode(21, "LD5N", 0,5,2));   //21  LD5N(0:5)   2
        ops.add(new OpCode(22, "LD6N", 0,5,2));   //22  LD6N(0:5)   2
        ops.add(new OpCode(23, "LDXN", 0,5,2));   //23  LDXN(0:5)   2
        
        ops.add(new OpCode(24, "STA", 0,5,2));   //24  STA(0:5)    2
        ops.add(new OpCode(25, "ST1", 0,5,2));   //25  ST1(0:5)    2
        ops.add(new OpCode(26, "ST2", 0,5,2));   //26  ST2(0:5)    2
        ops.add(new OpCode(27, "ST3", 0,5,2));   //27  ST3(0:5)    2
        ops.add(new OpCode(28, "ST4", 0,5,2));   //28  ST4(0:5)    2
        ops.add(new OpCode(29, "ST5", 0,5,2));   //29  ST5(0:5)    2
        ops.add(new OpCode(30, "ST6", 0,5,2));   //30  ST6(0:5)    2
        ops.add(new OpCode(31, "STX", 0,5,2));   //31  STX(0:5)    2
        ops.add(new OpCode(32, "STJ", 0,2,2));   //32  STJ(0:2)    2
        ops.add(new OpCode(33, "STZ", 0,5,2));   //33  STZ(0:5)    2
    
        ops.add(new OpCode(34, "JBUS", 0,2));      //34  JBUS(0)     2
        ops.add(new OpCode(35, "IOC", 0,2));       //35  IOC(0)      2
        ops.add(new OpCode(36, "IN", 0,2));        //36  IN(0)       2
        ops.add(new OpCode(37, "OUT", 0,2));       //37  OUT(0)      2
        ops.add(new OpCode(38, "JRED", 0,1));      //38  JRED(0)     1
        
        ops.add(new OpCode(39, "JMP", 0,1));       //39  JMP(0)      1
        ops.add(new OpCode(39, "JSJ", 1,1));       //39  JSJ(1)      1
        ops.add(new OpCode(39, "JOV", 2,1));       //39  JOV(2)      1
        ops.add(new OpCode(39, "JNOV", 3,1));      //39  JNOV(3)     1
        ops.add(new OpCode(39, "JL", 4,1));        //39  JL(4)       1
        ops.add(new OpCode(39, "JE", 5,1));        //39  JE(5)       1
        ops.add(new OpCode(39, "JG", 6,1));        //39  JG(6)       1
        ops.add(new OpCode(39, "JGE", 7,1));       //39  JGE(7)      1
        ops.add(new OpCode(39, "JNE", 8,1));       //39  JNE(8)      1
        ops.add(new OpCode(39, "JLE", 9,1));       //39  JLE(9)      1

        ops.add(new OpCode(40, "JAN", 0,1));       //40  JAN(0)      1
        ops.add(new OpCode(40, "JAZ", 1,1));       //40  JAZ(1)      1
        ops.add(new OpCode(40, "JAP", 2,1));       //40  JAP(2)      1
        ops.add(new OpCode(40, "JANN", 3,1));      //40  JANN(3)     1
        ops.add(new OpCode(40, "JANZ", 4,1));      //40  JANZ(4)     1
        ops.add(new OpCode(40, "JANP", 5,1));      //40  JANP(5)     1

        ops.add(new OpCode(41, "J1N", 0,1));      //41  J1N(0)      1
        ops.add(new OpCode(41, "J1Z", 1,1));      //41  J1Z(1)      1
        ops.add(new OpCode(41, "J1P", 2,1));      //41  J1P(2)      1
        ops.add(new OpCode(41, "J1NN", 3,1));     //41  J1NN(3)     1
        ops.add(new OpCode(41, "J1NZ", 4,1));     //41  J1NZ(4)     1
        ops.add(new OpCode(41, "J1NP", 5,1));     //41  J1NP(5)     1

        ops.add(new OpCode(42, "J2N", 0,1));     //42  J2N(0)      1
        ops.add(new OpCode(42, "J2Z", 1,1));     //42  J2Z(1)      1
        ops.add(new OpCode(42, "J2P", 2,1));     //42  J2P(2)      1
        ops.add(new OpCode(42, "J2NN", 3,1));    //42  J2NN(3)     1
        ops.add(new OpCode(42, "J2NZ", 4,1));    //42  J2NZ(4)     1
        ops.add(new OpCode(42, "J2NP", 5,1));    //42  J2NP(5)     1

        ops.add(new OpCode(43, "J3N", 0,1));    //43  J3N(0)      1
        ops.add(new OpCode(43, "J3Z", 1,1));    //43  J3Z(1)      1
        ops.add(new OpCode(43, "J3P", 2,1));    //43  J3P(2)      1
        ops.add(new OpCode(43, "J3NN", 3,1));   //43  J3NN(3)     1
        ops.add(new OpCode(43, "J3NZ", 4,1));   //43  J3NZ(4)     1
        ops.add(new OpCode(43, "J3NP", 5,1));   //43  J3NP(5)     1

        ops.add(new OpCode(44, "J4N", 0,1));    //44  J4N(0)      1
        ops.add(new OpCode(44, "J4Z", 1,1));    //44  J4Z(1)      1
        ops.add(new OpCode(44, "J4P", 2,1));    //44  J4P(2)      1
        ops.add(new OpCode(44, "J4NN", 3,1));   //44  J4NN(3)     1
        ops.add(new OpCode(44, "J4NZ", 4,1));   //44  J4NZ(4)     1
        ops.add(new OpCode(44, "J4NP", 5,1));   //44  J4NP(5)     1

        ops.add(new OpCode(45, "J5N", 0,1));    //45  J5N(0)      1
        ops.add(new OpCode(45, "J5Z", 1,1));    //45  J5Z(1)      1
        ops.add(new OpCode(45, "J5P", 2,1));    //45  J5P(2)      1
        ops.add(new OpCode(45, "J5NN", 3,1));   //45  J5NN(3)     1
        ops.add(new OpCode(45, "J5NZ", 4,1));   //45  J5NZ(4)     1
        ops.add(new OpCode(45, "J5NP", 5,1));   //45  J5NP(5)     1

        ops.add(new OpCode(46, "J6N", 0,1));     //46  J6N(0)      1
        ops.add(new OpCode(46, "J6Z", 1,1));     //46  J6Z(1)      1
        ops.add(new OpCode(46, "J6P", 2,1));     //46  J6P(2)      1
        ops.add(new OpCode(46, "J6NN", 3,1));    //46  J6NN(3)     1
        ops.add(new OpCode(46, "J6NZ", 4,1));    //46  J6NZ(4)     1
        ops.add(new OpCode(46, "J6NP", 5,1));    //46  J6NP(5)     1

        ops.add(new OpCode(47, "JXN", 0,1));    //47  JXN(0)      1
        ops.add(new OpCode(47, "JXZ", 1,1));    //47  JXZ(1)      1
        ops.add(new OpCode(47, "JXP", 2,1));    //47  JXP(2)      1
        ops.add(new OpCode(47, "JXNN", 3,1));   //47  JXNN(3)     1
        ops.add(new OpCode(47, "JXNZ", 4,1));   //47  JXNZ(4)     1
        ops.add(new OpCode(47, "JXNP", 4,1));   //47  JXNP(5)     1

        ops.add(new OpCode(48, "INCA", 0,1));   //48  INCA(0)     1
        ops.add(new OpCode(48, "DECA", 1,1));   //48  DECA(1)     1
        ops.add(new OpCode(48, "ENTA", 2,1));   //48  ENTA(2)     1
        ops.add(new OpCode(48, "ENNA", 3,1));   //48  ENNA(3)     1
    
        ops.add(new OpCode(49, "INC1", 0,1));   //49  INC1(0)     1
        ops.add(new OpCode(49, "DEC1", 1,1));   //49  DEC1(1)     1
        ops.add(new OpCode(49, "ENT1", 2,1));   //49  ENT1(2)     1
        ops.add(new OpCode(49, "ENN1", 3,1));   //49  ENN1(3)     1
    
        ops.add(new OpCode(50, "INC2", 0,1));   //50  INC2(0)     1
        ops.add(new OpCode(50, "DEC2", 1,1));   //50  DEC2(1)     1
        ops.add(new OpCode(50, "ENT2", 2,1));   //50  ENT2(2)     1
        ops.add(new OpCode(50, "ENN2", 3,1));   //50  ENN2(3)     1
    
        ops.add(new OpCode(51, "INC3", 0,1));   //51  INC3(0)     1
        ops.add(new OpCode(51, "DEC3", 1,1));   //51  DEC3(1)     1
        ops.add(new OpCode(51, "ENT3", 2,1));   //51  ENT3(2)     1
        ops.add(new OpCode(51, "ENN3", 3,1));   //51  ENN3(3)     1
    
        ops.add(new OpCode(52, "INC4", 0,1));   //52  INC4(0)     1
        ops.add(new OpCode(52, "DEC4", 1,1));   //52  DEC4(1)     1
        ops.add(new OpCode(52, "ENT4", 2,1));   //52  ENT4(2)     1
        ops.add(new OpCode(52, "ENN4", 3,1));   //52  ENN4(3)     1
    
        ops.add(new OpCode(53, "INC5", 0,1));   //53  INC5(0)     1
        ops.add(new OpCode(53, "DEC5", 1,1));   //53  DEC5(1)     1
        ops.add(new OpCode(53, "ENT5", 2,1));   //53  ENT5(2)     1
        ops.add(new OpCode(53, "ENN5", 3,1));   //53  ENN5(3)     1
    
        ops.add(new OpCode(54, "INC6", 0,1));   //54  INC6(0)     1
        ops.add(new OpCode(54, "DEC6", 1,1));   //54  DEC6(1)     1
        ops.add(new OpCode(54, "ENT6", 2,1));   //54  ENT6(2)     1
        ops.add(new OpCode(54, "ENN6", 3,1));   //54  ENN6(3)     1
    
        ops.add(new OpCode(55, "INCX", 0,1));   //55  INCX(0)     1
        ops.add(new OpCode(55, "DECX", 1,1));   //55  DECX(1)     1
        ops.add(new OpCode(55, "ENTX", 2,1));   //55  ENTX(2)     1
        ops.add(new OpCode(55, "ENNX", 3,1));   //55  ENNX(3)     1
    
        ops.add(new OpCode(56, "CMPA",0, 5,2));   //56  CMPA(0:5)   2
        ops.add(new OpCode(56, "FCMP", 6,2));       //56  FCMP(6)     2
    
        ops.add(new OpCode(57, "CMP1", 0, 5,2));    //57  CMP1(0:5)   2
        ops.add(new OpCode(58, "CMP2", 0, 5,2));    //58  CMP2(0:5)   2
        ops.add(new OpCode(59, "CMP3", 0, 5,2));    //59  CMP3(0:5)   2
        ops.add(new OpCode(60, "CMP4", 0, 5,2));    //60  CMP4(0:5)   2
        ops.add(new OpCode(61, "CMP5", 0, 5,2));    //61  CMP5(0:5)   2
        ops.add(new OpCode(62, "CMP6", 0, 5,2));    //62  CMP6(0:5)   2
        ops.add(new OpCode(63, "CMPX", 0, 5,2));    //63  CMPX(0:5)   2

        strCodes = new HashMap();
        opCodes = new HashMap();
        for (OpCode op : ops){
            strCodes.put(op.OP, op);
            if (opCodes.get(op.C)==null) {
                opCodes.put(op.C, new ArrayList<>());
            }
            
            opCodes.get(op.C).add(op);    
            
        }

        charCodes = new HashMap();
        charCodes.put(" ", 0);
        charCodes.put("A", 1);
        charCodes.put("B", 2);
        charCodes.put("C", 3);
        charCodes.put("D", 4);
        charCodes.put("E", 5);
        charCodes.put("F", 6);
        charCodes.put("G", 7);
        charCodes.put("H", 8);
        charCodes.put("I", 9);
        charCodes.put("𐤃", 10);

        charCodes.put("J", 11);
        charCodes.put("K", 12);
        charCodes.put("L", 13);
        charCodes.put("M", 14);
        charCodes.put("N", 15);
        charCodes.put("O", 16);
        charCodes.put("P", 17);
        charCodes.put("Q", 18);
        charCodes.put("R", 19);

        charCodes.put("Σ", 20);
        charCodes.put("П", 21);

        charCodes.put("S", 22);
    
        charCodes.put("T", 23);
        charCodes.put("U", 24);
        charCodes.put("V", 25);
        charCodes.put("W", 26);
        charCodes.put("X", 27);
        charCodes.put("Y", 28);
        charCodes.put("Z", 29);
        charCodes.put("0", 30);
        charCodes.put("1", 31);
        charCodes.put("2", 32);

        charCodes.put("3", 33);
        charCodes.put("4", 34);
        charCodes.put("5", 35);
        charCodes.put("6", 36);
        charCodes.put("7", 37);
        charCodes.put("8", 38);
        charCodes.put("9", 39);
        charCodes.put(".", 40);
        charCodes.put(",", 41);
        charCodes.put("(", 42);

        charCodes.put(")", 43);
        charCodes.put("+", 44);
        charCodes.put("-", 45);
        charCodes.put("*", 46);
        charCodes.put("/", 47);
        charCodes.put("=", 48);
        charCodes.put("$", 49);
        charCodes.put("<", 50);
        charCodes.put(">", 51);
        charCodes.put("@", 52);

        charCodes.put(";", 53);
        charCodes.put(":", 54);
        charCodes.put("'", 55);
    };
    
    static String toText(Word w) {
        StringBuilder sb = new StringBuilder();
        ArrayList<OpCode> op = opCodes.get(w.getC());
        int op_f = w.getF();
        
        if (op.size()>1) {
            for(OpCode o:op){
                // System.out.println("o.F="+o.F+" o.OP="+o.OP);
                if (o.F == op_f) {
                    sb.append(o.OP).append(" ");
                    // System.out.println("o.OP="+o.OP);
                    if (o.C==5) {
                        return sb.toString();
                    }
                }
            }
        } else {
            sb.append(op.get(0).OP).append(" ");
        }
        
        sb.append(w.getAA());
        int i = w.getI(); 
        if (i!=0) {
            sb.append(",").append(i);
        }
        int f = w.getF();
        OpCode oc = op.get(0);
        if (op.size()==1) {
            //System.out.println("oc.OP="+oc.OP);
            if ((oc.OP.contains("IOC")||oc.OP.contains("OUT"))) {
                sb.append("(").append(f).append(")");
                //System.out.println("##############");

            } else {
                int L = f / 8;
                int R = f % 8;
                //System.out.println("--------------");
                sb.append("(").append(L).append(":").append(R).append(")");
            }
        } else {
            sb.append("(").append(f).append(")");
        }
        //System.out.println(sb);
        return sb.toString();
    }

    public static int parse_int(String str, HashMap<String, Integer> variables){
        try {
            return Integer.parseInt(str);
        } catch (Exception e){
            
            if (variables.get(str)!=null) {
                return variables.get(str);
            }
        }

        String[] s = str.split("\\+");
        if (s.length>1){
            int sum = 0;
            for (int i=0; i<s.length; i++){
                sum += parse_int(s[i],variables);
                System.out.println("parse_int sum="+sum + " s[i]="+s[i]);
            }

            return sum;
        }

        String[] sm = str.split("\\-");
        if (sm.length>1){
            int dif = 0;
            for (int i=0; i<sm.length; i++){
                dif -= parse_int(sm[i], variables);
            }
            return dif;
        }

       // System.out.println("parse_int str= "+str);
        return 0;//parse_int(str, variables);
    }


    /*
     * [0 ][1][2][3][4][5]
     * [+-][A][A][I][F][C]
     * 
     */
    static Word toWord(String str, HashMap<String, Integer> variables){
        // op address, i(f)

        String[] parts_0 = str.split(" ");
        ArrayList<String> p0 = new ArrayList<>();
        for (int i=0; i<parts_0.length; i++) {
            if (parts_0[i].length()>0){
                p0.add(parts_0[i]);
            }
        }
        String[] parts = p0.toArray(new String[0]);
        OpCode op = strCodes.get(parts[0]);
        
        String[] parts1 = parts[1].split("\\,");
        int AA=-1;
        int I = 0;
        int F = 0;
        if (opCodes.get(op.C).size()>1){
            F = op.F;
        }
        int L = 0;
        int R = 0;
        if (parts1.length>1) {
            AA = parse_int(parts1[0], variables);

            String[] parts2 = parts1[1].split("\\(");
            if (parts2.length>1) {
                I = parse_int(parts2[0], variables);
            } else {
                I = parse_int(parts1[1], variables);
            }
        } else {
            String[] parts2 = parts[1].split("\\(");
            if (parts2.length>1) {
                AA = parse_int(parts2[0], variables);
                String[] parts3 = parts2[1].split("\\:");
                if (parts3.length >1){
                    L = parse_int(parts3[0], variables);
                    R = parse_int(parts3[1].split("\\)")[0], variables);
                } else {
                    F = parse_int(parts2[1], variables);
                }
            } else {
                try {
                    AA = parse_int(parts[1], variables);
                } catch (Exception e){
                    System.out.println("TODO: con обработать");
                }
            }
        }

        if (opCodes.get(op.C).size()==1){
            if (R!=0) {
                F = 8*L+R;
            } else {
                F=5;
            }
        } 

        System.out.println("str="+str+"; " + AA + " " + I +" " + " " + F + " "+ op.C);
        int AA1 = 0;
        int znak = AA<0?Word.MINUS:Word.PLUS;
        AA = Math.abs(AA);
        if (AA > 63) {
            AA1 = AA / 64;
            AA = AA % 64;
        }
        System.out.println("str="+str+"; " + znak + " " + AA1 + " " + AA + " " + I +" " + " " + F + " "+ op.C);
        int[] val = {znak, AA1, AA, I, F, op.C};

        return new Word(6, val);
    }

    public static void main(String[] args) {
        String s1 = "LD1 -499"; 
        
        // Word w1 = toWord(s1);
        // System.out.println(w1);
        // System.out.println(toText(w1));
        // System.out.println("----------------");

        // String s2 = "INC1    1";
        // Word w2 = toWord(s2);
        // System.out.println(w2);
        // System.out.println(toText(w2));
        // System.out.println("----------------");

        String s3 = "ST2     499,1";
        Word w3 = toWord(s3);
        System.out.println(w3);
        System.out.println(toText(w3));
        System.out.println("----------------");
    }
}
