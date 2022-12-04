
package player;

import java.util.ArrayList;
import java.util.Scanner;
import cards.Bonus;
import cards.Card;
import cards.Cloverleaf;
import cards.Fireworks;
import cards.PlusMinus;
import cards.Stop;
import cards.Straight;
import cards.Times2;
import dice.Dice;
import dice.DicesAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import utils.Constants;
import utils.Utils;

public class Player {
    
    private String name;    
    private int finalScore;
    private int intermediateScore;
    private int turnScore;
    private boolean plusMinus;
    private int [] numberDices=new int[6];
    
    
    public Player(String n){
        this.name=n;
        this.intermediateScore=0;
        this.finalScore=0;
        this.turnScore=0;
        this.plusMinus=false;
    }

    public String getName() {
        return name;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }
    
    public boolean isPlusMinus() {
		return plusMinus;
	}

	public void setPlusMinus(boolean plusMinus) {
		this.plusMinus = plusMinus;
	}

	public boolean playTurn(Card c, DicesAll lDice) throws IOException{
		System.out.println("--------------------------");
        System.out.print("\nCard: "+c.getName());
        if (c instanceof Bonus){
            System.out.print(" "+((Bonus) c).getWorth());
        }
        System.out.println("");
        if (c instanceof Stop){
            return false;
        }
        
        lDice.throwDices();
        System.out.println("");
        printDice("Dices out:",lDice.getDicesOut().getDicesArray());
        printDice("Dices in: ",lDice.getDicesIn().getDicesArray());
        Utils.printIndex(lDice.getDicesIn().getDicesArray().size());
        

        System.out.println("");
        System.out.println("Acumulated Score: "+intermediateScore);
        System.out.println("Turn Score: "+turnScore+"\n");

        if(checkNull(c,lDice)){
            if (c instanceof Fireworks){
                turnScore+=intermediateScore;
                finalScore+=turnScore;
            }
            intermediateScore=0;
            turnScore=0;
            lDice.moveIn();
            System.out.println("\nYou got a null throw. Sorry, you lost your turn.");
            return false;
        }
        if (c instanceof Fireworks){
            addAllDicesIn(lDice);
            if (lDice.checkTutto()){
                lDice.moveIn();
                return playTurn(c,lDice);
            }
            else{
                return playTurn(c,lDice);
            }
        }
        else if(c instanceof PlusMinus || c instanceof Cloverleaf || c instanceof Straight){
            selectDices(c,lDice);
            if (lDice.checkTutto()){
                
                if (c instanceof Cloverleaf){
                    ((Cloverleaf)c).setTimes(((Cloverleaf)c).getTimes()+1);
                    if (((Cloverleaf)c).getTimes()==2){
                        System.out.println("YOU HAVE SCORED A TUTTO TWICE IN A ROW");
                        return false;
                    }
                    else{
                    	lDice.moveIn();
                        return playTurn(c,lDice);
                    }
                }
                else if (c instanceof PlusMinus){
                    turnScore+=Constants.SCOREPLUSMINUS;
                    lDice.moveIn(); //REVISAR
                    this.plusMinus=true;
                    return askToContinue();
                }
                else if (c instanceof Straight){
                    turnScore+=Constants.SCORESTRAIGHT;
                    lDice.moveIn(); //REVISE
                    return askToContinue();
                }
                return true;
            }
            else{
                return playTurn(c,lDice);
            }
        }
        else{
            boolean correctLetter=false;
            while (!correctLetter){
                System.out.println("Please Select one of the options:");
                System.out.println("Continue (Roll the dice):  R");
                System.out.println("End the turn:              E");
                Scanner teclado = new Scanner(System.in);
                String coordenada;
                coordenada=teclado.nextLine();
                String letter = coordenada.substring(0,1);
                if (letter.length() != 1){
                    System.out.println("Error reading the input");
                }
                else if (letter.equals("R")){ //CONTINUE
                    selectDices(c,lDice);
                    if (lDice.checkTutto()){
                        if (c instanceof Bonus){
                            turnScore+=(this.intermediateScore+((Bonus)c).getWorth());
                        }
                        else if (c instanceof Times2){
                            turnScore+=(this.intermediateScore*2);
                        }
                        this.intermediateScore=0;
                        lDice.moveIn();
                        boolean keep= askToContinue();
                        if (!keep){
                            this.finalScore+=this.turnScore;
                            this.turnScore=0;
                        }   
                        return keep;
                    }
                    else{
                        return playTurn(c,lDice);
                    }
                }
                else if (letter.equals("E")){ //END THE TURN
                    addAllDicesIn(lDice);
                    if (lDice.checkTutto()){
                        if (c instanceof Bonus){
                            turnScore+=(this.intermediateScore+((Bonus)c).getWorth());
                        }
                        else if (c instanceof Times2){
                            turnScore+=(this.intermediateScore*2);
                        }
                        this.intermediateScore=0;
                        lDice.moveIn();
                    }
                    else{
                        this.finalScore += this.intermediateScore;    
                        
                    }
                    this.finalScore += this.turnScore;
                    this.turnScore=0;
                    this.intermediateScore=0;
                    lDice.moveIn();
                    return false;
                }
                else{
                    System.out.println("You must write only a R or a E.");
                }
            }

        }
        return false;
    }
    
    
    private void printDice(String name,ArrayList<Dice> ld){        
        System.out.print(name+"  ");
        for (Dice d : ld){
            System.out.print(d.getDice()+"  ");
            
        }
        System.out.println("");     
    }
    

    private boolean checkNull(Card c, DicesAll lDice){
        for (int i=0;i<6;i++){
            numberDices[i]=0;
        }
        for (int i=0;i<lDice.getDicesIn().getDicesArray().size();i++){
                numberDices[lDice.getDicesIn().getDicesArray().get(i).getDice()-1]++;
        }
        if (c instanceof Straight){
            if (lDice.getDicesIn().getDicesArray().size()==0){
                return false;
            }
            boolean nullthrow=true;
            for (int i=0;i<lDice.getDicesIn().getDicesArray().size();i++){
                nullthrow=false;
                for (int j=0;j<lDice.getDicesOut().getDicesArray().size();j++){
                    if (lDice.getDicesIn().getDicesArray().get(i).getDice()==lDice.getDicesOut().getDicesArray().get(j).getDice()){
                        nullthrow=true;
                    }
                }
                if (nullthrow==false){
                    return nullthrow;
                }
            }
            return nullthrow;
        }
        else{
            for (int i=0; i<numberDices.length;i++){
                if (numberDices[i]>=3 || numberDices[0]!=0 || numberDices[4]!=0){
                    return false;
                }
            }
            return true;
        }
    }

    private void addAllDicesIn(DicesAll lDice) {
        for (int i=0;i<numberDices.length;i++){
            if (numberDices[i]>=3){  //CASE YOU THROW 3 OR MORE DICES WITH THE SAME NUMBER
                for (int j=0;j<3;j++){
                    lDice.moveOut(i+1);
                }
                intermediateScore+=Constants.SCORETRIPLETS[i];
                numberDices[i]-=3;
            }
            if (numberDices[i]==3){  //CASE YOU THROW 6 DICES WITH THE SAME NUMBER
                for (int j=0;j<numberDices[i];j++){
                	lDice.moveOut(i+1);
                }
                intermediateScore+=Constants.SCORETRIPLETS[i];
                numberDices[i]-=3;
            }
            else if (i != 0 && i != 4){ //CASE YOU THROW 1-2 or 4-5 DICES WITH THE SAME NUMBER
                numberDices[i]-=numberDices[i];
            }
        }
        if (numberDices[0]!=0){ //CASE DICE NUMBER IS 1
            for (int k=0;k<numberDices[0];k++){
            	lDice.moveOut(1);
                intermediateScore+=Constants.SCORE1;
            }
            numberDices[0]-=numberDices[0];
        }
        if (numberDices[4]!=0){ //CASE DICE NUMBER IS 5
            for (int k=0;k<numberDices[4];k++){
            	lDice.moveOut(5);
                intermediateScore+=Constants.SCORE5;
            }
            numberDices[4]-=numberDices[4];
        }
        
    }
    
    private void selectDices(Card c,DicesAll lDice) throws IOException{
        boolean selected=false;
        ArrayList<Integer> listOptions = new ArrayList<Integer>();
        while (!selected){
            try{
                listOptions.clear();
                System.out.println("Please, select the INDEX of the dices you want to keep:");
                Scanner tec = new Scanner(System.in);
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String  lines = br.readLine();    
                String[] strs = lines.trim().split("\\s+");

                for (int i = 0; i < strs.length; i++) {
                    listOptions.add(Integer.parseInt(strs[i]));
                }                        

                if (c instanceof Straight){
                    selected=checkDicesSelectedStraight(listOptions,lDice);
                }
                else{
                    selected=checkDicesSelected(listOptions,lDice);
                }
            }
            catch (Exception NumberFormatException){
                System.out.println("Please Write the dices positions separated by a space: 1 2 3 4");
            }    
        }
    	ArrayList<Dice> listDicesSelected = new ArrayList<Dice>();
        for (int values: listOptions){
        	listDicesSelected.add(lDice.getDicesIn().getDicesArray().get(values-1));
        }
        for (Dice d: listDicesSelected) {
        	lDice.moveOut(d.getDice());
            if (!(c instanceof Straight) && !(c instanceof PlusMinus) && !(c instanceof Cloverleaf)){
                if (numberDices[d.getDice()-1]>=3){
                    this.intermediateScore+=Constants.SCORETRIPLETS[d.getDice()-1];
                    numberDices[d.getDice()-1]-=3;
                }
                else if (d.getDice()==1 && numberDices[d.getDice()-1]!=0){
                    this.intermediateScore+=Constants.SCORE1;
                    numberDices[d.getDice()-1]--;
                }
                else if (d.getDice()==5 && numberDices[d.getDice()-1]!=0){
                    this.intermediateScore+=Constants.SCORE5;
                    numberDices[d.getDice()-1]--;
                }
            }
        }
    }



    private boolean checkDicesSelected(ArrayList<Integer> listOptions,DicesAll lDice) {
        int [] provisionalList=new int[Constants.TOTALNUMDICES];
        for (int value : listOptions){
            if (numberDices[lDice.getDicesIn().getDicesArray().get(value-1).getDice()-1]<3 && lDice.getDicesIn().getDicesArray().get(value-1).getDice() !=1 && lDice.getDicesIn().getDicesArray().get(value-1).getDice() !=5){
                System.out.println("The Dice "+lDice.getDicesIn().getDicesArray().get(value-1).getDice()+" is not well selected.");
                return false;
            }
            else{
                provisionalList[lDice.getDicesIn().getDicesArray().get(value-1).getDice()-1]++;
            }
        }
        for (int i=0;i<provisionalList.length;i++){
            if(provisionalList[i]!=3 && provisionalList[i]!=0 && i+1!=1 && i+1!=5){
                System.out.println("You have not selected the 3 dices with the number "+(i+1));
                return false;
            }
        }
        
        return true;
    }

    private boolean checkDicesSelectedStraight(ArrayList<Integer> listOptions,DicesAll lDice) {
        for (int value: listOptions){
            for (int value2: listOptions){
                if (lDice.getDicesIn().getDicesArray().get(value-1).getDice()==lDice.getDicesIn().getDicesArray().get(value2-1).getDice() && value!=value2){
                    System.out.println("You cannot select the Dice "+lDice.getDicesIn().getDicesArray().get(value-1).getDice()+" more than once.");
                    return false;
                }
            }
            for (Dice dice: lDice.getDicesOut().getDicesArray()){
                if (dice.getDice()==lDice.getDicesIn().getDicesArray().get(value-1).getDice()){
                    System.out.println("The Dice "+dice.getDice()+" is already selected.");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean askToContinue() {
        boolean stayLoop=true;
        while (stayLoop){
            System.out.println("Please Select one of the options:");
            System.out.println("Continue (New Card): Y");
            System.out.println("End your turn:       N");
            Scanner keyboard = new Scanner(System.in);
            String letter=keyboard.nextLine();
            if (letter.length() != 1){
                System.out.println("Error reading the input");
            }
            else{
                if (!letter.equals("Y") && !letter.equals("N")){
                    System.out.println("You have to write a Y or a N");    
                }
                else if (letter.equals("Y")){
                    return true;
                }
                else if (letter.equals("N")){
                    this.finalScore+=this.turnScore;
                    this.turnScore=0;
                    return false;
                }
            }
        }
        return false; 
    }
    
}