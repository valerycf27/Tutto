
package utils;

import cards.Bonus;
import cards.Card;
import cards.Cloverleaf;
import cards.Fireworks;
import cards.PlusMinus;
import cards.Stop;
import cards.Straight;
import cards.Times2;
import player.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import dice.Dice;
import java.util.Scanner;


public class Utils {
    public static void printHead(){
        System.out.println("--------------------");
        System.out.println("  WELCOME TO TUTTO  ");
        System.out.println("--------------------");
    }
    
    public static int numberPlayers(){
        boolean validnPlayers=false;
        int nPlayer=0;
        while (!validnPlayers){
            try{
                System.out.println("Please, select the number of players:");
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
        return nPlayer;
    }
    
    public static ArrayList<Player> sortPlayers(int nPlayer){
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
        for(String temp: names){
            Player p1=new Player(temp);
            listPlayer.add(p1);
        }
        return listPlayer;
    }
    
    public static ArrayList<Card> createDeck(){
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
            Card c=new Bonus(Constants.WORTHBONUS[i%5]);            
            deckCards.add(c);           
        }
        return deckCards;
    }
    
    public static void printCurrentScore(ArrayList<Player> listPlayer){
        for (int i=0;i<listPlayer.size();i++){
            System.out.println(listPlayer.get(i).getName()+": "+listPlayer.get(i).getFinalScore());
        }
    }
    
    public static Card getRandomCard(ArrayList<Card> listCard){
        Random rand=new Random();
        boolean notSelected=true;
        Card c=null;
        int counter=0;
        while(counter<listCard.size()-1 && listCard.get(counter).isUsed()==true){
            counter++;
        }
        if (counter==56){
            for (int i=0;i<listCard.size();i++){
                listCard.get(i).setUsed(false);
            }
        }
        while (notSelected){
            int position=rand.nextInt(56);
            if (listCard.get(position).isUsed()==false){
                listCard.get(position).setUsed(true);
                c= listCard.get(position);
                notSelected=false;
            }  
        }
        return c;
    }
    
    public static void printIndex(int len){
        System.out.print("Index:      ");
        for (int i=0;i<len;i++){
            System.out.print((i+1)+"  ");
        }
        System.out.println("");        
    }

    public static void restFirstPlayer(ArrayList<Player> listPlayer,Player p) {
        int position=-1;
        int score=0;
        for (int i=0;i<listPlayer.size();i++){
            if (listPlayer.get(i).getFinalScore()>score){
                position=i;
                score=listPlayer.get(i).getFinalScore();
            }

        }
        if (position != -1) {
        	if (! listPlayer.get(position).equals(p)){
                for (Player player: listPlayer){
                    if (!player.equals(p) && player.getFinalScore()==listPlayer.get(position).getFinalScore() && !player.equals(listPlayer.get(position))){
                        player.setFinalScore(listPlayer.get(position).getFinalScore()-Constants.DEDUCTIONPLUSMINUS);
                    }
                }
                listPlayer.get(position).setFinalScore(listPlayer.get(position).getFinalScore()-Constants.DEDUCTIONPLUSMINUS);
        	}
        	
        }


        
        
    }
}
