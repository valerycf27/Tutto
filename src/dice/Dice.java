
package dice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    private int number;
    
    public Dice(int n) {
        this.number = n;

    }
    public Dice() {
    	this.number=0;
    }
    public void throwDice(){
        this.number = ThreadLocalRandom.current().nextInt(1, 6 + 1);
    }
    public int getDice(){
        return this.number;
    }   
}
