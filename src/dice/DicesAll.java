package dice;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Constants;

public class DicesAll {
    private DicesIn dicesIn;
    private DicesOut dicesOut;
    
    public DicesAll(){
    	this.dicesIn = new DicesIn();
        this.dicesOut = new DicesOut();
    }
    
    public DicesIn getDicesIn() {
		return this.dicesIn;
	}

	public DicesOut getDicesOut() {
		return this.dicesOut;
	}
	
	public void moveIn() {
		if (this.dicesOut.getDicesArray().size()!=0) {
			for (int i=this.dicesOut.getDicesArray().size()-1;i>=0;i--) {				
				this.dicesIn.addDice(dicesOut.getDicesArray().get(i));
	            this.dicesOut.removeDice(i);
			}	
		}
	}
	
    public void moveOut(int diceN){
    	for (int i=0;i<dicesIn.getDicesArray().size();i++) {
    		if(diceN==dicesIn.getDicesArray().get(i).getDice()) {
        		this.dicesOut.addDice(dicesIn.getDicesArray().get(i));
        		this.dicesIn.removeDice(diceN);
        		return;
    		}
    	}
    }
    
    public boolean checkTutto() {
        if (this.dicesOut.getDicesArray().size()==Constants.TOTALNUMDICES){
                System.out.println("TUTTO");
                return true;
        }
        return false;
    }    
    
    public void throwDices(){
        for (Dice d : dicesIn.getDicesArray()){
            d.throwDice();
        }
    }
}
