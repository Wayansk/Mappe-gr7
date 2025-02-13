package gameplay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiceRollTest {
  @Test
  public void testDiceRollWithinRange() {
    Dice dice = new Dice();
    for (int i = 0; i < 50; i++) { // Roll multiple times
      int roll = dice.roll();
      System.out.println("Roll #" + (i + 1) + ": " + roll);
      assertTrue(roll >= 1 && roll <= 6, "Dice roll out of range: " + roll);
    }
  }
}
