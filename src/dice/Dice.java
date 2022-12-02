
package dice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    private int number;
    
    public void throwDice(){
        this.number = ThreadLocalRandom.current().nextInt(1, 6 + 1);
    }
    public int getDie(){
        return this.number;
    }
    public Dice(int n) {
        this.number = n;

    }
    public Dice() {
    }
    
}
