package snakesandladders.engine;

import snakesandladders.gameplay.Player;

/**
 * A data record representing the result of a single player's turn.
 *
 * @param player      the player who took the turn
 * @param rolledValue the number rolled on the dice
 * @param newTileId   the ID of the tile the player landed on
 * @param hasWon      true if this turn resulted in a win
 */
public record TurnResult(Player player, int rolledValue, int newTileId, boolean hasWon) {

}
