package mix;
import java.util.*;

public class Word {
    private int regCount; 
    private int v;
    public static final int PLUS = 0;
    public static final int MINUS = -1;
    public ArrayList<Bite> value = new ArrayList<>();
    private int[] pretty_val_1_L = {0,0};
    private int[] pretty_val_1_size = {0,0};
    private int pretty_idx = -1;

    public Word(int regCount, int[] val ){
        this.regCount = regCount;
        for (int i=0; i<val.length; i++){
            if (val[i] >65 ) {
                pretty_idx++;
                pretty_val_1_L[pretty_idx] = val[i];
                int v1 = val[i];
                for (int j=regCount-2; j>0; --j){
                    double tmp = Math.pow(64.0, Double.valueOf(j));
                    int tmp1 = (int)(val[i]/tmp);
                    if (tmp1!=0) {
                        value.add(new Bite(tmp1)); 
                        pretty_val_1_size[pretty_idx]++;
                    }
                    v1 = (int)(val[i]%tmp);
                }
                value.add(new Bite(v1));
                pretty_val_1_size[pretty_idx]++;
            } else {
                value.add(new Bite(val[i]));
            }
        }
    }

    public Word(int regCount) {
        this.regCount = regCount;
        for (int i=0; i< regCount;i++){
            value.add(new Bite(0));
        }
    }

    public void set(int v, int sign) {
        value.add(new Bite(sign)); 
        int v1 = v;
        for (int i=regCount-2; i>0; --i){
            double tmp = Math.pow(64.0, Double.valueOf(i));
            value.add(new Bite((int)(v/tmp))); 
            v1 = (int)(v%tmp);
        }
        value.add(new Bite(v1));
    }


    public void set(int v){
        set(Math.abs(v), v<0?MINUS:PLUS);
    }

    public Word(int v, int regCount){
        this.regCount = regCount;
        set(v);
    }

    public Word(int v, int sign, int regCount){
        this.regCount = regCount;
        set(v, sign);
    }

    public String toPrettyString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(value.get(0).get()<0?"-":"+").append("]");
        int j_pretty_idx = pretty_idx==-1?-1:0;
        for (int i=1; i<regCount; i++){
            if ((j_pretty_idx>-1)&&(j_pretty_idx <= pretty_idx)) {
                sb.append("[").append(pretty_val_1_L[j_pretty_idx]).append("]");
                i+= pretty_val_1_size[j_pretty_idx]-1;
                j_pretty_idx++;

            } else {
                sb.append("[").append(value.get(i)).append("]");
            }
        }
        return sb.toString();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("[").append(value.get(0).get()<0?"-":"+").append("]");
        for (int i=1; i<regCount; i++){
            sb.append("[").append(value.get(i)).append("]");
        }
        
        return sb.toString();
    }

    public Word get(int i) {
        int L = i/8;
        int R = i%8;
        return get(L, R);
    }

    public Word get(int L, int R){
        Word res = new Word(6);

        if (R>0){
            int j =0;
            for (int i=R; i>=L; i--) {
                res.value.add(regCount-j++, this.value.get(i));
            }
        }
        return res;
    }

    public void set(Word w){

        if(w.regCount > regCount){
            this.value.add(0, w.value.get(0));
            value.add(0,(Bite)(w.value.get(0).copy()));
            int delta = w.regCount - regCount;
            for (int i = 1; i < regCount; i++){
                value.add(i,(Bite)(w.value.get(i+delta).copy()));
            }
            return;
        } 
        
        if(w.regCount < regCount){
            value.add(0,(Bite)(w.value.get(0)));
            
            int delta = regCount - w.regCount;
            for (int i = 1; i < w.regCount; i++){
                value.add(i+delta,(Bite)(w.value.get(i).copy()));
            }
            return;
        }

        
        value.clear();
        value.addAll(w.value);
        
    }

    public static void main(String[] args) {
        // Word i1 = new Word(-66, 3);
        // System.out.println(i1);
        // Word i2 = new Word(67, 6);
        // System.out.println(i2);
        // i2.set(i1);
        // System.out.println(i2);

        int[] v = {PLUS,2000,3,5,4};
        Word i3 = new Word(6,v);
        System.out.println(i3);
        System.out.println(i3.toPrettyString());
    }
}