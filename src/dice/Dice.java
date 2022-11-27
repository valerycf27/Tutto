/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dice;

import java.util.Random;

/**
 *
 * @author 34649
 */
public class Dice {
    private int number;
    
    public Dice(){
        Random rn = new Random();
        this.number = rn.nextInt(6) +1;
    }
    
    public Dice(int i){
        this.number=i;
    }

    public int getNumber() {
        return number;
    }
    
    
}
