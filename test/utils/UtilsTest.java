package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import cards.Card;
import junit.framework.Assert;
import static org.junit.Assert.*;
import player.Player;

class UtilsTest {

	@Test
	void testNumberPlayers() {
		int numPlayers = Utils.numberPlayers();
		Assert.assertTrue(numPlayers >= 2 && numPlayers <= 4);
	}

	@Test
	void testSortPlayers2() {
		int numPlayers = 2;
		
		ArrayList<Player> listPlayerInput = Utils.sortPlayers(numPlayers);
		int asciiValuePlayer0 = listPlayerInput.get(0).getName().substring(0,1).charAt(0);
		int asciiValuePlayer1 = listPlayerInput.get(1).getName().substring(0,1).charAt(0);
		Assert.assertTrue(asciiValuePlayer0 <= asciiValuePlayer1);
	}
	
	@Test
	void testSortPlayers3() {
		int numPlayers = 3;
		
		ArrayList<Player> listPlayerInput = Utils.sortPlayers(numPlayers);
		int asciiValuePlayer0 = listPlayerInput.get(0).getName().substring(0,1).charAt(0);
		int asciiValuePlayer1 = listPlayerInput.get(1).getName().substring(0,1).charAt(0);
		int asciiValuePlayer2 = listPlayerInput.get(2).getName().substring(0,1).charAt(0);
		Assert.assertTrue(asciiValuePlayer0 <= asciiValuePlayer1 && asciiValuePlayer1 <= asciiValuePlayer2);
	}
	
	@Test
	void testSortPlayers4() {
		int numPlayers = 4;
		
		ArrayList<Player> listPlayerInput = Utils.sortPlayers(numPlayers);
		int asciiValuePlayer0 = listPlayerInput.get(0).getName().substring(0,1).charAt(0);
		int asciiValuePlayer1 = listPlayerInput.get(1).getName().substring(0,1).charAt(0);
		int asciiValuePlayer2 = listPlayerInput.get(2).getName().substring(0,1).charAt(0);
		int asciiValuePlayer3 = listPlayerInput.get(3).getName().substring(0,1).charAt(0);
		Assert.assertTrue(asciiValuePlayer0 <= asciiValuePlayer1 && asciiValuePlayer1 <= asciiValuePlayer2 && asciiValuePlayer2 <= asciiValuePlayer3);
	}
	
	@Test
	void testCreateDeck() {
		ArrayList<Card> deckCards = Utils.createDeck();
		Assert.assertEquals(deckCards.size(), Constants.NUMCARDS);
	}

	@Test
	void testGetRandomCard() {
		ArrayList<Card> deckCards = Utils.createDeck();
		for(int i = 0 ; i< deckCards.size()- 1; i++) {
			deckCards.get(i).setUsed(true);
		}
		Card c = Utils.getRandomCard(deckCards);
		Assert.assertEquals(c, deckCards.get(Constants.NUMCARDS-1));
	}

	@Test
	void testRestFirstPlayer() {
		
		ArrayList<Player> listPlayer = Utils.sortPlayers(3);
		listPlayer.get(0).setFinalScore(4000);
		listPlayer.get(1).setFinalScore(3000);
		listPlayer.get(2).setFinalScore(2000);
		
		Utils.restFirstPlayer(listPlayer, listPlayer.get(0));
		Assert.assertEquals(listPlayer.get(0).getFinalScore(), 4000);
	}
	
	@Test
	void testRestFirstPlayer2() {
		
		ArrayList<Player> listPlayer = Utils.sortPlayers(3);
		listPlayer.get(0).setFinalScore(4000);
		listPlayer.get(1).setFinalScore(3000);
		listPlayer.get(2).setFinalScore(2000);
		
		Utils.restFirstPlayer(listPlayer, listPlayer.get(1));
		Assert.assertEquals(listPlayer.get(0).getFinalScore(), 3000);
	}

	@Test
	void testRestFirstPlayer3() {
		
		ArrayList<Player> listPlayer = Utils.sortPlayers(3);
		listPlayer.get(0).setFinalScore(4000);
		listPlayer.get(1).setFinalScore(4000);
		listPlayer.get(2).setFinalScore(2000);
		
		Utils.restFirstPlayer(listPlayer, listPlayer.get(2));
		Assert.assertTrue(listPlayer.get(0).getFinalScore()==3000 && listPlayer.get(1).getFinalScore()==3000);
	}
}
