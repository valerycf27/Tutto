package dice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DicesOutTest {

    DicesOut dicesOut = new DicesOut();

    @Test
    void getDiceArray() {
        int size = dicesOut.getDicesArray().size();
        // check if the array contains six elements:
        assertEquals(0, size);
    }

    @Test
    void addDice() {
        int size_a = dicesOut.getDicesArray().size();
        Dice dice = new Dice(1);
        dicesOut.addDice(dice);
        int size_b = dicesOut.getDicesArray().size();
        assertNotEquals(size_a, size_b);
    }

}