
package core;

import cards.Card;
import cards.Cloverleaf;
import cards.Stop;
import java.util.ArrayList;
import java.util.Scanner;
import dice.DicesAll;

import java.io.IOException;
import player.Player;
import utils.Utils;
import utils.Constants;


public class Tutto {    
    
    public static void main(String[] args) throws IOException {
        Utils.printHead();
        
        //NUMBER OF PLAYERS
        int nPlayer= Utils.numberPlayers();
        
        //ARRAY WITH THE PLAYERS ORDERED
        ArrayList<Player> listPlayer= Utils.sortPlayers(nPlayer);
        
        //DECK OF CARDS
        ArrayList<Card> deckCards = Utils.createDeck();

        
        //SET OF DICES
        DicesAll dices=new DicesAll();
        
        //PLAY TUTTO GAME
        boolean victoryPlayer=false;
        while(!victoryPlayer){
            for (int i=0;i<listPlayer.size();i++){
                System.out.println("\nIt's your turn Player "+(i+1)+": "+listPlayer.get(i).getName());
                boolean errorStart=true;
                String letter="";
                
                while (errorStart){
                    System.out.println("\nPlease Select one of the options:");
                    System.out.println("Roll the dice:              R");
                    System.out.println("Display the current scores: D");
                    Scanner teclado = new Scanner(System.in);
                    letter=teclado.nextLine();
                    if (letter.length() != 1){
                        System.out.println("Error reading the input");
                    }
                    else{
                        if (!letter.equals("R") && !letter.equals("D")){
                            System.out.println("You must write only a R or a D.");    
                        }
                        else if (letter.equals("R")){
                            Card c=Utils.getRandomCard(deckCards);
                            while (listPlayer.get(i).playTurn(c, dices)){  
                                c=Utils.getRandomCard(deckCards);
                            }
                            if (c instanceof Stop){
                                System.out.println("You got a Stop Card. Sorry, you lost your turn.");
                            }
                            else if (c instanceof Cloverleaf){
                                if (((Cloverleaf)c).getTimes()==2){
                                    victoryPlayer=true;
                                }
                            }
                            else if(listPlayer.get(i).isPlusMinus()) {
                            	Utils.restFirstPlayer(listPlayer, listPlayer.get(i));
                            	listPlayer.get(i).setPlusMinus(false);
                            }
                            errorStart=false;
                        }
                        else{
                            Utils.printCurrentScore(listPlayer);
                        }
                    }
                }
                if (listPlayer.get(i).getFinalScore()>=Constants.ENDGAMEPOINTS){
                    victoryPlayer=true;
                }
                if (victoryPlayer){
                    System.out.println("\nCONGRATULATIONS, YOU ARE THE WINNER "+listPlayer.get(i).getName());
                    System.out.println("This are the final scores:");
                    Utils.printCurrentScore(listPlayer);
                    System.out.println("GAME OVER");
                    break;
                }
            }
        }
    }
}