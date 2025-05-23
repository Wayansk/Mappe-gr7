package snakesandladders.gameplay;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of dice.
 */
public class Dice {
  private final List<Die> dice;
  private int rollSum = 0;

  /**
   * Constructs a Dice object with the specified number of dice.
   *
   * @param numberOfDice the number of dice to create.
   */
  public Dice(int numberOfDice) {
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
  }

  /**
   * Rolls all dice and accumulates their values into a sum.
   */
  public void rollDice() {
    // Optionally, reset rollSum if multiple rolls are expected.
    rollSum = 0;
    for (Die die : dice) {
      die.roll();
      rollSum += die.getLasRolledNumber();
    }
  }

  /**
   * Returns the total sum of the last dice roll.
   *
   * @return the sum of the rolled values.
   */
  public int getRollSum() {
    return rollSum;
  }
}
