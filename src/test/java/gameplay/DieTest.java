package gameplay;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DieTest {

    @Test
    void testRoll() {
        Die die = new Die();
        die.roll();
        int rolledValue = die.getLasRolledNumber();
        assertTrue(rolledValue >= 1 && rolledValue <= 6,
                "Rolled value should be between 1 and 6, but got " + rolledValue);
    }
}

