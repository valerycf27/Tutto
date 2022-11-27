/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

/**
 *
 * @author 34649
 */
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
