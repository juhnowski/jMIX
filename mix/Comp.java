package mix;

import java.util.ArrayList;

public class Comp {
    private Word rA = new Word(6);
    private Word rX = new Word(6);
    private Word rI1 = new Word(3);
    private Word rI2 = new Word(3);
    private Word rI3 = new Word(3);
    private Word rI4 = new Word(3);
    private Word rI5 = new Word(3);
    private Word rI6 = new Word(3);
    private Word rJ = new Word(3);
    private boolean fp;
    private boolean CI_L;
    private boolean CI_E;
    private boolean CI_G;
    private int maxMem = 4000;
    private ArrayList<Word> mem = new ArrayList();
    private int maxU = 20;
    private ArrayList<Integer> u = new ArrayList();

    public Comp(){
        for (int i=0; i<maxMem; i++){
            mem.add(new Word(6));
        }
    }
}
