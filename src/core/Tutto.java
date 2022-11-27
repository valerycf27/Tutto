
package core;

import cards.Bonus;
import cards.Card;
import cards.Cloverleaf;
import cards.Fireworks;
import cards.PlusMinus;
import cards.Stop;
import cards.Straight;
import cards.Times2;
import java.util.ArrayList;
import java.util.Scanner;
import dice.Dice;
import player.Player;
import utils.Utils;
import java.util.Collections;
import utils.Constants;


public class Tutto {    
    
    public static void main(String[] args) {
        System.out.println("WELCOME TO TUTTO");
        
        //NUMBER OF PLAYERS
        boolean validnPlayers=false;
        int nPlayer=0;
        while (!validnPlayers){
            try{
                System.out.println("Select the number of players:");
                Scanner in = new Scanner(System.in);
                nPlayer = in.nextInt();
                if (nPlayer<2 || nPlayer>4){
                    System.out.println("The number of players must be between 2 and 4");
                }
                else{
                    validnPlayers=true;
                }
            }catch(Exception InputMismatchException) {
                System.out.println("You must only enter an integer");
            }
        }
        
        //ARRAY WITH THE PLAYERS ORDERED
        ArrayList<Player> listPlayer = new ArrayList<Player>();
        ArrayList<String> names=new ArrayList<String>();
        for (int i=0;i<nPlayer;i++){
            boolean validname=false;
            String name;
            while (!validname){
                System.out.println("Write the name of the player "+(i+1)+":");
                Scanner teclado = new Scanner(System.in);
                name=teclado.nextLine();
                String letra = name.substring(0,1);
                if (Character.isLetter(letra.charAt(0)) == false){
                    System.out.println("The name of the player must start with a letter");
                }
                else{
                    validname=true;
                    names.add(name);
                }
            }
        }
        Collections.sort(names);
        System.out.println(names);
        for(String temp: names){
            Player p1=new Player(temp);
            listPlayer.add(p1);
        }
        
        //DECK OF CARDS
        ArrayList<Card> deckCards = new ArrayList<Card>();
        for (int i=0;i<Constants.NUMCLOVERLEAFCARDS;i++){
            Card c=new Cloverleaf();
            deckCards.add(c);
        }
        for (int i=0;i<Constants.NUMFIREWORKSCARDS;i++){
            Card c=new Fireworks();
            deckCards.add(c);
        }
        for (int i=0;i<Constants.NUMSTOPCARDS;i++){
            Card c=new Stop();
            deckCards.add(c);
        }
        for (int i=0;i<Constants.NUMSTRAIGHTCARDS;i++){
            Card c=new Straight();
            deckCards.add(c);
        }
        for (int i=0;i<Constants.NUMPLUSMINUSCARDS;i++){
            Card c=new PlusMinus();
            deckCards.add(c);
        }
        for (int i=0;i<Constants.NUMTIMES2CARDS;i++){
            Card c=new Times2();
            deckCards.add(c);
        }
        for (int i=0;i<Constants.NUMBONUSCARDS;i++){
            Card c;
            if(i<5){
                c=new Bonus(Constants.WORTH200);
            }
            if(i<10){
                c=new Bonus(Constants.WORTH300);
            }
            if(i<15){
                c=new Bonus(Constants.WORTH400);
            }
            if(i<20){
                c=new Bonus(Constants.WORTH500);
                
            }
            else{
                c=new Bonus(Constants.WORTH600);
            }
            
            deckCards.add(c);           
        }
        
        //PLAY TUTTO GAME
        boolean victoryPlayer=false;
        while(!victoryPlayer){
            for (int i=0;i<listPlayer.size();i++){
                boolean errorStart=true;
                String letter="";
                
                while (errorStart){
                    System.out.println("Roll the dice (R) or Display the current scores (D)");
                    Scanner teclado = new Scanner(System.in);
                    letter=teclado.nextLine();
                    if (letter.length() != 1){
                        System.out.println("Error reading the input");
                    }
                    else{
                        if (!letter.equals("R") && !letter.equals("D")){
                            System.out.println("You have to write an R or a D");    
                        }
                        else if (letter.equals("R")){
                            Dice [] ldices=Utils.createNewListDice(Constants.TOTALNUMDICES);
                            Card c=Utils.getRandomCard(deckCards);
                            if ( !(c instanceof Stop)){
                                while (listPlayer.get(i).playTurn(c, ldices)){
                                    if (c instanceof PlusMinus){
                                        //restFirstPlayer();
                                    }
                                    else{

                                    }
                                }
                            }

                            errorStart=false;
                        }
                        else{
                            Utils.printCurrentScore(listPlayer);
                        }
                    }
                }
                

            }
        }
    }
    
}
