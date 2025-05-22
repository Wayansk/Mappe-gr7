package connectfour.engine;

import connectfour.model.Player;

/**
 * Result of a single turn/drop
 * @param player
 * @param column
 * @param hasWon
 */
public record TurnResult(Player player, int column, boolean hasWon) { }
