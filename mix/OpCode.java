package mix;

import java.util.ArrayList;

class OpCode {
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
        ops.add(new OpCode(5, "CHAR", 2,10));       //5    HLT(2)      10
    
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
    };
    
    /*
     * [0 ][1][2][3][4][5]
     * [+-][A][A][I][F][C]
     * 
     */
    Word strToCode(String str){
        Word w = new Word(5);
        String[] parts = str.split(str);
        return w;
    }

    public static void main(String[] args) {
        String s1 = "LDA 2000"; 
        String s2 = "LDA 2000(1:5)";
        String s3 = "LDA 2000(3:5)";
        String s4 = "LDA 2000(0:3)";
        String s5 = "LDA 2000(4:4)";
        String s6 = "LDA 2000(0:0)";
        String s7 = "LDA 2000(1:1)";
    }
}
