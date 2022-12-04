package dice;

import java.util.ArrayList;

public class DicesOut {
    private ArrayList<Dice> dicesArray = new ArrayList<Dice>();

    public DicesOut(){

    }
    public ArrayList<Dice> getDicesArray(){
        return this.dicesArray;
    }
    
    public void addDice(Dice dice){
        this.dicesArray.add(dice);
    }
    
    public void removeDice(int position){
        this.dicesArray.remove(position);
    }    
}
