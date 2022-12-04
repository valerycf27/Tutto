package dice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DicesInTest {
    DicesIn dicesIn = new DicesIn();

    @Test
    void getDicesArray() {
        int size = dicesIn.getDicesArray().size();
        // check if the array contains six elements:
        assertEquals(6, size);
        // check if an entity in the array is of class "Dice"
        assertEquals(Dice.class, dicesIn.getDicesArray().get(0).getClass());
    }


}