/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.util.ArrayList;
import java.util.Scanner;
import cards.Bonus;
import cards.Card;
import cards.Cloverleaf;
import cards.Fireworks;
import cards.PlusMinus;
import cards.Straight;
import cards.Times2;
import dice.Dice;
import utils.Constants;
import utils.Utils;


/**
 *
 * @author 34649
 */
public class Player {
    private int finalScore;
    private int turnScore;
    private String name;
    ArrayList<Dice> finalListDice = new ArrayList<Dice>();
    ArrayList<Dice> diceSelected = new ArrayList<Dice>();
    
    private int [] numberDices=new int[6];
    
    
    public Player(String n){
        this.name=n;
        this.finalScore=0;
        this.turnScore=0;
    }

    public String getName() {
        return name;
    }

    public int getFinalScore() {
        return finalScore;
    }
    
    
    public boolean playTurn(Card c,Dice [] lDice){
        printDice(lDice);
        System.out.println(c.getName());
        if(checkNull(c,lDice)){
            if (c instanceof Fireworks){
                finalScore+=turnScore;
            }
            else{
                turnScore=0;
            }
            return false;
        }
        if (c instanceof Fireworks){
            addFinalListDice();
            if (checkTutto()){
                Dice [] lnewDice=Utils.createNewListDice(Constants.TOTALNUMDICES);
                finalListDice.clear();
                return playTurn(c,lnewDice);
            }
            else{
                Dice [] lnewDice=Utils.createNewListDice(Constants.TOTALNUMDICES-finalListDice.size());
                return playTurn(c,lnewDice);
            }
        }
        else if(c instanceof PlusMinus || c instanceof Cloverleaf){
            selectDices(c,lDice);
            if (checkTutto()){
                finalListDice.clear();
                if (c instanceof Cloverleaf){
                    ((Cloverleaf)c).setTimes(((Cloverleaf)c).getTimes()+1);
                    if (((Cloverleaf)c).getTimes()==2){
                        System.out.println("GAME OVER");
                        return true;
                    }
                    else{
                        Dice [] lnewDice=Utils.createNewListDice(Constants.TOTALNUMDICES);
                        return playTurn(c,lnewDice);
                    }
                }
                else if (c instanceof PlusMinus){
                    finalScore+=Constants.SCOREPLUSMINUS;
                    //FALTA RESTAR LOS 1000 PUNTOS
                    return true;
                }
                return true;
            }
            else{
                Dice [] lnewDice=Utils.createNewListDice(6-finalListDice.size());
                return playTurn(c,lnewDice);
            }
        }
        else{
            System.out.println("Roll the dice (R) or End the turn (E)");
            Scanner teclado = new Scanner(System.in);
            String coordenada;
            coordenada=teclado.nextLine();
            String letra = coordenada.substring(0,1);
            if (letra.equals("R")){ //CONTINUAR
                selectDices(c,lDice);
                if (checkTutto()){
                    if (c instanceof Straight){
                        finalScore+=Constants.SCORESTRAIGHT;
                        return true;
                    }
                    else if (c instanceof Bonus){
                        finalScore+=(turnScore+((Bonus)c).getWorth());
                        turnScore=0;
                        return true;
                    }
                    else if (c instanceof Times2){
                        finalScore+=(turnScore*2);
                        turnScore=0;
                        return true;
                    }
                }
                else{
                    Dice [] lnewDice=Utils.createNewListDice(Constants.TOTALNUMDICES-finalListDice.size());
                    return playTurn(c,lnewDice);
                }
            }
            else if (letra.equals("E")){ //BAJARSE
                this.finalScore += this.turnScore;
                this.turnScore=0;
                return false;
            }
            return false;
        }

    }
    
    
    private void printDice(Dice [] ld){
        for (int i=0;i<ld.length;i++){
            System.out.print(ld[i].getNumber()+" ");
        }
        System.out.println();
    }
    
    private boolean checkNull(Card c, Dice[] lthrowDice){
        if (c instanceof Straight){
            boolean nullthrow=true;
            for (int i=0;i<lthrowDice.length;i++){
                nullthrow=false;
                for (int j=0;j<finalListDice.size();j++){
                    if (lthrowDice[i].getNumber()==finalListDice.get(j).getNumber()){
                        nullthrow=true;
                    }
                }
                if (nullthrow=false){
                    return nullthrow;
                }
            }
            return true;
        }
        else{
            for (int i=0;i<lthrowDice.length;i++){
                if (lthrowDice[i].getNumber()==1) {
                    numberDices[0]++;
                }
                else if(lthrowDice[i].getNumber()==2){
                    numberDices[1]++;
                }
                else if(lthrowDice[i].getNumber()==3){
                    numberDices[2]++;
                }
                else if(lthrowDice[i].getNumber()==4){
                    numberDices[3]++;
                }
                else if(lthrowDice[i].getNumber()==5){
                    numberDices[4]++;
                }
                else if(lthrowDice[i].getNumber()==6){
                    numberDices[5]++;
                }
            }
            
            for (int i=0; i<numberDices.length;i++){
                if (numberDices[i]==3 || numberDices[0]!=0 || numberDices[4]!=0){
                    return false;
                }
            }
            return true;
        }
    }

    private void addFinalListDice() {
        for (int i=0;i<numberDices.length;i++){
            if (numberDices[i]==3){
                for (int j=0;j<numberDices[i];j++){
                    Dice d=new Dice(i+1);
                    finalListDice.add(d);
                }
                turnScore+=Constants.SCORETRIPLETS[i];
                numberDices[i]-=3;
            }
            else if (i != 0 && i != 4){
                numberDices[i]-=numberDices[i];
            }
        }
        if (numberDices[0]!=0){
            for (int k=0;k<numberDices[0];k++){
                Dice d=new Dice(1);
                finalListDice.add(d);
                turnScore+=Constants.SCORE1;
            }
            numberDices[0]-=numberDices[0];
        }
        if (numberDices[4]!=0){
            for (int k=0;k<numberDices[4];k++){
                Dice d=new Dice(5);
                finalListDice.add(d);
                turnScore+=Constants.SCORE5;
            }
            numberDices[4]-=numberDices[4];
        }
        
    }
    
    private void selectDices(Card c,Dice [] ld){
        System.out.println("Please, select the dices positions from this throw that you want to keep");
        Scanner tec = new Scanner(System.in); //QUITAR
        String numbersInput;			
        
        numbersInput = tec.nextLine();
        String[] numbers = numbersInput.split(" ");
        for(String n : numbers){
            ;
        }
        
        
      
    }

    private boolean checkTutto() {
        if (finalListDice.size()==6){
                System.out.println("TUTTO");
                return true;
        }
        return false;
    }
    
    
}
