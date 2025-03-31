package board;

import com.google.gson.Gson;
import exceptions.BoardResourceNotFoundException;
import json_util.BoardDefinition;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * A simple game board with 90 tiles for Snakes and Ladders.
 */
public class Board {

  private final Tile[] tiles;

  /**
   * Creates the board, initializes 90 tiles, and sets up ladder and snake actions.
   */
  public Board() {
    InputStream inputStream = getClass().getResourceAsStream("/json_boards/Board_1.json");
    if (inputStream == null) {
      throw new BoardResourceNotFoundException("Could not find the board (Board_1.json) in resources");
    }

    // Parse JSON
    Gson gson = new Gson();
    BoardDefinition boardDef = gson.fromJson(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8),
        BoardDefinition.class
    );

    // build the tiles
    int size = boardDef.getSize();
    tiles = new Tile[size];
    for (int i = 0; i < size; i++) {
      tiles[i] = new Tile(i + 1);
    }

    // add LadderAction
    boardDef.getLadders().forEach(ladder -> {
      int fromIndex = ladder.getFrom() - 1;
      int toIndex = ladder.getTo() - 1;
      tiles[fromIndex].setLandAction(
          new LadderAction(ladder.getFrom(), ladder.getTo(), tiles[toIndex])
      );
    });

    // add SnakeAction
    boardDef.getSnakes().forEach(snake -> {
      int fromIndex = snake.getFrom() - 1;
      int toIndex = snake.getTo() - 1;
      tiles[fromIndex].setLandAction(
          new SnakeAction(snake.getFrom(), snake.getTo(), tiles[toIndex])
      );
    });
  }

  public Tile getTile(int index) {
    if (index < 0 || index >= tiles.length) {
      throw new IndexOutOfBoundsException("Tile index out of range: " + index);
    }
    return tiles[index];
  }
}
