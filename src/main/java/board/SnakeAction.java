package board;

import gameplay.Player;

/**
 * Moves the player using the snake.
 */
public class SnakeAction implements TileAction {

  private final int fromTileId;
  private final int toTileId;
  private final Tile destinationTile;

  /**
   * Creates a ladder action from a start tile to a destination tile.
   *
   * @param fromTileId      the ID of the starting tile
   * @param toTileId        the ID of the destination tile
   * @param destinationTile the {@link Tile} object representing the destination
   */
  public SnakeAction(int fromTileId, int toTileId, Tile destinationTile) {
    this.fromTileId = fromTileId;
    this.toTileId = toTileId;
    this.destinationTile = destinationTile;
  }

  /**
   * Moves the specified player to the destination tile.
   *
   * @param player the player who landed on this ladder
   */
  @Override
  public void perform(Player player) {
    System.out.println(player.getNameOfPiece()
        + " Found a snake " + fromTileId + " to " + toTileId);
    player.setCurrentTile(destinationTile);
  }
}
