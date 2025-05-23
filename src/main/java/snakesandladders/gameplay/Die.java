package snakesandladders.gameplay;

import java.util.Random;

/**
 * A simple die that can be rolled to produce a random value between 1 and 6.
 */
public class Die {
    private final Random random;
    private int lastRolledNumber;

    /**
     * Constructs a new Die.
     */
    public Die() {
        this.random = new Random();
    }

    /**
     * Rolls the die to generate a random value between 1 and 6.
     */
    public void roll() {
        lastRolledNumber = random.nextInt(6) + 1;
    }

    /**
     * Returns the value from the last roll.
     *
     * @return the last rolled number.
     */
    public int getLasRolledNumber() {
        return lastRolledNumber;
    }
}
