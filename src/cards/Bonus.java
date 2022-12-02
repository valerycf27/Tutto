
package cards;


public class Bonus extends Card{
    private int worth;
    public Bonus(int w) {
        super("Bonus");
        this.worth=w;
    }

    public int getWorth() {
        return worth;
    }
    
}
