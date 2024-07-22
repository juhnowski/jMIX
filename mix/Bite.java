package mix;

import java.util.ArrayList;

public class Bite {
    public static final int MAX_BITE_VALUE = 64;
    private int value = 0;

    void set(int value) throws Exception{
      if (value > 64) throw new Exception("Bites limit exceeded yay MAX_BITE_VALUE");
      this.value = value;
    }

    int get() {
      return value;
    }

    public Bite(int value){
      this.value = value;
    }

    public String toString(){
      return ""+value;
    }
  
    public Bite copy()
    {
      return new Bite(value);
    }

    public static void main(String[] args) {
      Bite myObj = new Bite(10);
      System.out.println(myObj);
    }
  }

  