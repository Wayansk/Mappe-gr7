package snakesandladders.board;

import snakesandladders.gameplay.Player;

/**
 * Represents a tile on a game board. Each tile has an ID, an optional action that can be triggered
 * when a player lands on it, and a reference to the next tile on the board.
 */
public class Tile {

  private final int tileId;
  private TileAction landAction;

  /**
   * Constructs a Tile with the given ID.
   *
   * @param tileId the unique identifier for this tile
   * @throws IllegalArgumentException if the tileId is less than 1
   */
  public Tile(int tileId) {
    this.tileId = tileId;
  }

  /**
   * Sets the specific action when landing on a tile.
   *
   * @param landAction action on landing
   */
  public void setLandAction(TileAction landAction) {
    try {
      this.landAction = landAction;
    } catch (IllegalArgumentException e) {
      System.out.println("Something unexpected happened. Try again.");
    }
  }

  /**
   * Executes the action associated with the tile when a player lands on it. If no action is set for
   * the tile, no action will be performed.
   *
   * @param player the player who lands on the tile
   */
  public void landPlayer(Player player) {
    if (landAction != null) {
      landAction.perform(player);
    }
  }

  /**
   * Gets the unique identifier of this tile.
   *
   * @return the ID of the tile
   */
  public int getTileId() {
    return tileId;
  }

  /**
   * Gets the action that is triggered when a player lands on this tile.
   *
   * @return the land action of this tile, or null if no action is set
   */
  public TileAction getLandAction() {
    return landAction;
  }
}
