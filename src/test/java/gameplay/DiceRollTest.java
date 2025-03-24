package gameplay;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DiceRollTest {

  @Test
  void testRollDiceSumInRange() {
    int numberOfDice = 3;
    Dice dice = new Dice(numberOfDice);
    dice.rollDice();

    int sum = dice.getRollSum();
    int maxSum = numberOfDice * 6;  // maximum sum

    assertTrue(sum >= numberOfDice && sum <= maxSum,
            "Roll sum " + sum + " is not in the range [" + numberOfDice + ", " + maxSum + "]");
  }
}
