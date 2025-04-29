package board;

import com.google.gson.Gson;
import exceptions.BoardResourceNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import json_util.BoardDefinition;

/**
 * Represents a Snakes and Ladders game board. Loads the board structure from a JSON file.
 */
public class Board {

  private final Tile[] tiles;

  /**
   * Creates a Board by loading tile and action data from a JSON resource. Sets up all normal tiles,
   * ladders, and snakes.
   */
  public Board() {
    InputStream inputStream = getClass().getResourceAsStream("/json_boards/Board_1.json");
    if (inputStream == null) {
      throw new BoardResourceNotFoundException(
          "Could not find the board (Board_1.json) in resources");
    }

    // Parse JSON
    Gson gson = new Gson();
    BoardDefinition boardDef = gson.fromJson(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8),
        BoardDefinition.class
    );

    // Build the tiles
    int size = boardDef.getSize();
    tiles = new Tile[size];
    for (int i = 0; i < size; i++) {
      tiles[i] = new Tile(i + 1);
    }

    // Add LadderActions
    boardDef.getLadders().forEach(ladder -> {
      int fromIndex = ladder.getFrom() - 1;
      int toIndex = ladder.getTo() - 1;
      tiles[fromIndex].setLandAction(
          new LadderAction(ladder.getFrom(), ladder.getTo(), tiles[toIndex])
      );
    });

    // Add SnakeActions
    boardDef.getSnakes().forEach(snake -> {
      int fromIndex = snake.getFrom() - 1;
      int toIndex = snake.getTo() - 1;
      tiles[fromIndex].setLandAction(
          new SnakeAction(snake.getFrom(), snake.getTo(), tiles[toIndex])
      );
    });
  }

  /**
   * Returns the tile at the given index.
   *
   * @param index zero-based tile index
   * @return the Tile at the specified index
   * @throws IndexOutOfBoundsException if index is out of range
   */
  public Tile getTile(int index) {
    if (index < 0 || index >= tiles.length) {
      throw new IndexOutOfBoundsException("Tile index out of range: " + index);
    }
    return tiles[index];
  }

  /**
   * Returns the total number of tiles on the board.
   *
   * @return the number of tiles
   */
  public int getTileCount() {
    return tiles.length;
  }
}
