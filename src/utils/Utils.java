/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import cards.Card;
import player.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import dice.Dice;


/**
 *
 * @author 34649
 */
public class Utils {
    
    public static void printCurrentScore(ArrayList<Player> listPlayer){
        for (int i=0;i<listPlayer.size();i++){
            System.out.println(listPlayer.get(i).getName()+": "+listPlayer.get(i).getFinalScore());
        }
    }
    public static Dice [] createNewListDice(int len){
        Dice [] lDice=new Dice[len];
        for (int i=0;i<len;i++ ){
            Dice d=new Dice();
            lDice[i]=d;
        }
        return lDice;
    }
    
    public static Card getRandomCard(ArrayList<Card> listCard){
        Random rand=new Random();
        boolean notSelected=true;
        Card c=null;
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
}
