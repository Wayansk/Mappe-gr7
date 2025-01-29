package gameplay;

import java.util.Random;

public class Dice {
  private Random random;

  public Dice() {
    this.random = new Random();
  }

  /**
   * Rolls the dice and returns a value between 1 and 6.
   * @return A random integer between 1 and 6.
   */
  public int roll() {
    return random.nextInt(6) + 1;
  }
}
