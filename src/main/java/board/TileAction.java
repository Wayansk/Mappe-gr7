package board;

import gameplay.Player;

/**
 * Represents an action that can be performed when a player lands on a specific tile.
 */
public interface TileAction {

  /**
   * Performs the action associated with a tile when a player lands on it.
   *
   * @param player The player on whom the action is performed.
   */
  void perform(Player player);
}