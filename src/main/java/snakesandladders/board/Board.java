package snakesandladders.board;

import com.google.gson.Gson;
import exceptions.BoardIndexOutOfBoundsException;
import exceptions.BoardResourceNotFoundException;
import snakesandladders.json_util.BoardDefinition;
import snakesandladders.json_util.Jump;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Snakes and Ladders game board. Loads the board structure from a JSON file.
 */
public class Board {

  private final String resourcePath;
  private final List<Tile> tiles;
  private final int size;
  private final int rows;
  private final int cols;

  /**
   * Creates a Board by loading tile and action data from a JSON resource. Sets up all normal tiles,
   * ladders, and snakes.
   */
  public Board(String resourcePath) {
    this.resourcePath = resourcePath;
    BoardDefinition boardDefinition = loadDefinition(resourcePath);
    this.size = boardDefinition.getSize();
    this.rows = boardDefinition.getRows();
    this.cols = boardDefinition.getCols();

    // Build the tiles
    this.tiles = new ArrayList<>(size);
    for (int i = 1; i <= size; i++) {
      tiles.add(new Tile(i));
    }

    // Add LadderActions
    for (Jump jumpLadder : boardDefinition.getLadders()) {
      Tile jumpFromTile = tiles.get(jumpLadder.getFrom() - 1);
      Tile jumpToTile = tiles.get(jumpLadder.getTo() - 1);
      jumpFromTile.setLandAction(new LadderAction(jumpLadder.getFrom(), jumpLadder.getTo(), jumpToTile));
    }

    // Add SnakeActions
    for (Jump jumpSnake : boardDefinition.getSnakes()) {
      Tile jumpFromTile = tiles.get(jumpSnake.getFrom() - 1);
      Tile jumpToTile = tiles.get(jumpSnake.getTo() - 1);
      jumpFromTile.setLandAction(new SnakeAction(jumpSnake.getFrom(), jumpSnake.getTo(), jumpToTile));
    }
  }

  private BoardDefinition loadDefinition(String resourcePath) {
    InputStream inputStream = getClass().getResourceAsStream(resourcePath);
    if (inputStream == null) {
      throw new BoardResourceNotFoundException("Could not find JSON board: " + resourcePath);
    }
    try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
      return new Gson().fromJson(inputStreamReader, BoardDefinition.class);
    } catch (Exception e) {
      throw new BoardResourceNotFoundException("Error parsing JSON board: " + resourcePath, e);
    }
  }

  /**
   * Returns the tile at the given index.
   *
   * @param index zero-based tile index
   * @return the Tile at the specified index
   * @throws IndexOutOfBoundsException if index is out of range
   */
  public Tile getTile(int index) {
    if (index < 0 || index >= size) {
      throw new BoardIndexOutOfBoundsException(index, size);
    }
    return tiles.get(index);
  }

  /**
   * Returns the total number of tiles on the board.
   *
   * @return the number of tiles
   */
  public int getTileCount() {
    return size;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  public String getResourcePath() {
    return resourcePath;
  }
}
