package board;

/**
 * A simple game board with 90 tiles for Snakes and Ladders.
 */
public class Board {

  private static final int SIZE = 90;
  private final Tile[] tiles;

  /**
   * Creates the board, initializes 90 tiles, and sets up ladder and snake actions.
   */
  public Board() {
    tiles = new Tile[SIZE];
    initializeBoard();

    // Ladders
    tiles[3].setLandAction(new LadderAction(4, 14, tiles[13]));
    tiles[9].setLandAction(new LadderAction(10, 27, tiles[26]));
    tiles[28].setLandAction(new LadderAction(29, 57, tiles[56]));

    // Snakes
    tiles[80].setLandAction(new SnakeAction(81, 64, tiles[63]));
    tiles[50].setLandAction(new SnakeAction(51, 32, tiles[31]));
    tiles[15].setLandAction(new SnakeAction(16, 2, tiles[3]));
  }

  /**
   * Initializes the board with tiles numbered 1 to 90.
   */
  private void initializeBoard() {
    for (int i = 0; i < SIZE; i++) {
      tiles[i] = new Tile(i + 1);
    }
  }

  /**
   * Returns the tile of the index in tiles.
   *
   * @param index number to the tile in tiles
   * @return the index of the tile
   */
  public Tile getTile(int index) {
    return tiles[index];
  }
}
