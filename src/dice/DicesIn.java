package dice;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Constants;

public class DicesIn {
    private ArrayList<Dice> dicesArray = new ArrayList<Dice>();
    
    public DicesIn(){
    	for (int i = 0; i < Constants.TOTALNUMDICES; i++){
    		Dice d=new Dice();
            this.dicesArray.add(d);
        }
    }
    
    public ArrayList<Dice> getDicesArray(){
        return this.dicesArray;
    }
    
    public void addDice(Dice dice){
        this.dicesArray.add(dice);
    }    
    
    public void removeDice(int diceN){
        for (int i=0;i<dicesArray.size();i++) {
        	if (diceN==dicesArray.get(i).getDice()) {
        		dicesArray.remove(i);
        		return;
        	}
        }
    }    
     
}
