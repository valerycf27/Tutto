package dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DiceTest {
    Dice dice = new Dice();

    @Test
    void throwDice() {
        // By creating a new dice, the die is set to 0 (i.e. undefined) by default.
        int dieOne = dice.getDice();
        dice.throwDice();
        // The die should change to a number between 1 and 6.
        int dieTwo = dice.getDice();
        assertNotEquals(dieOne, dieTwo);
    }

    @Test
    void getDie() {
        Dice dice = new Dice(3);
        assertEquals(3, dice.getDice());
    }
}
