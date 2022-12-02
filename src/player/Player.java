
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
    
    ArrayList<Dice> dicesIn = new ArrayList<Dice>();
    ArrayList<Dice> dicesOut = new ArrayList<Dice>();

    private int [] numberDices=new int[6];
    
    
    public Player(String n){
        this.name=n;
        this.intermediateScore=0;
        this.finalScore=0;
        this.turnScore=0;
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
    
    
    public boolean playTurn(Card c,ArrayList<Dice> lDice) throws IOException{
        System.out.print("Card: "+c.getName());
        if (c instanceof Bonus){
            System.out.print(" "+((Bonus) c).getWorth());
        }
        System.out.println("");
        if (c instanceof Stop){
            return false;
        }
        this.dicesIn=lDice;
        //THROWDICES
        for (Dice d : this.dicesIn){
            d.throwDice();
        }
        System.out.println("");
        printDice("Dices out:",this.dicesOut);
        printDice("Dices in: ",this.dicesIn);
        Utils.printIndex(dicesIn.size());
        

        System.out.println("");
        System.out.println("Acumulated Score: "+intermediateScore);
        System.out.println("Turn Score: "+turnScore);

        if(checkNull(c)){
            if (c instanceof Fireworks){
                turnScore+=intermediateScore;
                finalScore+=turnScore;
            }
            intermediateScore=0;
            turnScore=0;
            dicesOut.clear();
            System.out.println("\nYou got a null throw. Sorry, you lost your turn.");
            return false;
        }
        if (c instanceof Fireworks){
            addAllDicesIn();
            if (checkTutto()){
                this.dicesIn=Utils.createNewListDice(Constants.TOTALNUMDICES);
                this.dicesOut.clear();
                return playTurn(c,dicesIn);
            }
            else{
                return playTurn(c,dicesIn);
            }
        }
        else if(c instanceof PlusMinus || c instanceof Cloverleaf || c instanceof Straight){
            selectDices(c,lDice);
            if (checkTutto()){
                
                if (c instanceof Cloverleaf){
                    ((Cloverleaf)c).setTimes(((Cloverleaf)c).getTimes()+1);
                    if (((Cloverleaf)c).getTimes()==2){
                        System.out.println("YOU HAVE SCORED A TUTTO TWICE IN A ROW");
                        return false;
                    }
                    else{
                        this.dicesIn=Utils.createNewListDice(Constants.TOTALNUMDICES);
                        dicesOut.clear();
                        return playTurn(c,dicesIn);
                    }
                }
                else if (c instanceof PlusMinus){
                    //FALLA LA RESTA DE PUNTOS
                    turnScore+=Constants.SCOREPLUSMINUS;
                    this.dicesOut.clear();
                    return askToContinue();
                }
                else if (c instanceof Straight){
                    turnScore+=Constants.SCORESTRAIGHT;
                    this.dicesOut.clear();
                    return askToContinue();
                }
                return true;
            }
            else{
                //Dice [] lnewDice=Utils.createNewListDice(6-dicesIn.size());
                return playTurn(c,dicesIn);
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
                    if (checkTutto()){
                        if (c instanceof Bonus){
                            turnScore+=(this.intermediateScore+((Bonus)c).getWorth());
                        }
                        else if (c instanceof Times2){
                            turnScore+=(this.intermediateScore*2);
                        }
                        this.intermediateScore=0;
                        this.dicesOut.clear();
                        boolean keep= askToContinue();
                        if (!keep){
                            this.finalScore+=this.turnScore;
                            this.turnScore=0;
                        }   
                        return keep;
                    }
                    else{
                        return playTurn(c,this.dicesIn);
                    }
                }
                else if (letter.equals("E")){ //END THE TURN
                    addAllDicesIn();
                    if (checkTutto()){
                        if (c instanceof Bonus){
                            turnScore+=(this.intermediateScore+((Bonus)c).getWorth());
                        }
                        else if (c instanceof Times2){
                            turnScore+=(this.intermediateScore*2);
                        }
                        this.intermediateScore=0;
                        this.dicesOut.clear();
                    }
                    else{
                        this.finalScore += this.intermediateScore;    
                        
                    }
                    this.finalScore += this.turnScore;
                    this.turnScore=0;
                    this.intermediateScore=0;
                    this.dicesOut.clear();
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
            System.out.print(d.getDie()+"  ");
            
        }
        System.out.println("");     
    }
    

    private boolean checkNull(Card c){
        for (int i=0;i<6;i++){
            numberDices[i]=0;
        }
        for (int i=0;i<dicesIn.size();i++){
                numberDices[dicesIn.get(i).getDie()-1]++;
        }
        if (c instanceof Straight){
            if (dicesOut.size()==0){
                return false;
            }
            boolean nullthrow=true;
            for (int i=0;i<this.dicesIn.size();i++){
                nullthrow=false;
                for (int j=0;j<dicesOut.size();j++){
                    if (dicesIn.get(i).getDie()==dicesOut.get(j).getDie()){
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

    private void addAllDicesIn() {
        for (int i=0;i<numberDices.length;i++){
            if (numberDices[i]>=3){  //CASE YOU THROW 3 OR MORE DICES WITH THE SAME NUMBER
                for (int j=0;j<3;j++){
                    Dice d=new Dice(i+1);
                    dicesOut.add(d);
                    removeDicesIn(d);
                }
                intermediateScore+=Constants.SCORETRIPLETS[i];
                numberDices[i]-=3;
            }
            if (numberDices[i]==3){  //CASE YOU THROW 6 DICES WITH THE SAME NUMBER
                for (int j=0;j<numberDices[i];j++){
                    Dice d=new Dice(i+1);
                    dicesOut.add(d);
                    removeDicesIn(d);
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
                Dice d=new Dice(1);
                dicesOut.add(d);
                removeDicesIn(d);
                intermediateScore+=Constants.SCORE1;
            }
            numberDices[0]-=numberDices[0];
        }
        if (numberDices[4]!=0){ //CASE DICE NUMBER IS 5
            for (int k=0;k<numberDices[4];k++){
                Dice d=new Dice(5);
                dicesOut.add(d);
                removeDicesIn(d);
                intermediateScore+=Constants.SCORE5;
            }
            numberDices[4]-=numberDices[4];
        }
        
    }
    
    private void selectDices(Card c,ArrayList<Dice> ld) throws IOException{
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
                //System.out.println(listOptions);
                        

                if (c instanceof Straight){
                    selected=checkDicesSelectedStraight(listOptions);
                }
                else{
                    selected=checkDicesSelected(listOptions);
                }
            }
            catch (Exception NumberFormatException){
                System.out.println("Please Write the dices positions separated by a space: 1 2 3 4");
            }

            
        }
        for (int values: listOptions){
            Dice d=new Dice(this.dicesIn.get(values-1).getDie());
            this.dicesOut.add(d);
            if (!(c instanceof Straight) && !(c instanceof PlusMinus) && !(c instanceof Cloverleaf)){
                if (numberDices[dicesIn.get(values-1).getDie()-1]>=3){
                    this.intermediateScore+=Constants.SCORETRIPLETS[dicesIn.get(values-1).getDie()-1];
                    numberDices[dicesIn.get(values-1).getDie()-1]-=3;
                }
                else if (dicesIn.get(values-1).getDie()==1 && numberDices[dicesIn.get(values-1).getDie()-1]!=0){
                    this.intermediateScore+=Constants.SCORE1;
                    numberDices[dicesIn.get(values-1).getDie()-1]--;
                }
                else if (dicesIn.get(values-1).getDie()==5 && numberDices[dicesIn.get(values-1).getDie()-1]!=0){
                    this.intermediateScore+=Constants.SCORE5;
                    numberDices[dicesIn.get(values-1).getDie()-1]--;
                }
            }
        }
        for (int i=0;i<listOptions.size();i++){
            dicesIn.remove(0);
        }
    }

    private boolean checkTutto() {
        if (dicesOut.size()==Constants.TOTALNUMDICES){
                System.out.println("TUTTO");
                return true;
        }
        return false;
    }

    private void removeDicesIn(Dice d) {
        for (int i=0;i<dicesIn.size();i++){
            if (dicesIn.get(i).getDie()==d.getDie()){
                dicesIn.remove(i);
                return;
            }
            
        }
    }

    private boolean checkDicesSelected(ArrayList<Integer> listOptions) {
        int [] provisionalList=new int[6];
        for (int value : listOptions){
            if (numberDices[dicesIn.get(value-1).getDie()-1]<3 && dicesIn.get(value-1).getDie() !=1 && dicesIn.get(value-1).getDie() !=5){
                System.out.println("The Dice "+dicesIn.get(value-1).getDie()+" is not well selected.");
                return false;
            }
            else{
                provisionalList[dicesIn.get(value-1).getDie()-1]++;
            }
        }
        for (int i=0;i<6;i++){
            if(provisionalList[i]!=3 && provisionalList[i]!=0 && i+1!=1 && i+1!=5){
                System.out.println("You have not selected the 3 dices with the number "+(i+1));
                return false;
            }
        }
        
        return true;
    }

    private boolean checkDicesSelectedStraight(ArrayList<Integer> listOptions) {
        for (int value: listOptions){
            for (int value2: listOptions){
                if (dicesIn.get(value-1).getDie()==dicesIn.get(value2-1).getDie() && value!=value2){
                    System.out.println("You cannot select the Dice "+dicesIn.get(value-1).getDie()+" more than once.");
                    return false;
                }
            }
            for (Dice dice: dicesOut){
                if (dice.getDie()==dicesIn.get(value-1).getDie()){
                    System.out.println("The Dice "+dicesIn.get(value-1).getDie()+" is already selected.");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean askToContinue() {
        boolean hola=true;
        while (hola){
            System.out.println("Please Select one of the options:");
            System.out.println("Continue (New Card): Y");
            System.out.println("End your turn:       N");
            Scanner teclado = new Scanner(System.in);
            String letter=teclado.nextLine();
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
