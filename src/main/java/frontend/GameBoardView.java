package frontend;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Builds a standard Snakes and Ladders board grid.
 */
public class GameBoardView {

  private static final int ROWS = 9;
  private static final int COLS = 10;
  private static final int TILE_SIZE = 60;

  private final GridPane boardGrid;
  private final Map<Integer, StackPane> tileMap;

  /**
   * Creates a new GameBoardView.
   */
  public GameBoardView() {
    this.tileMap = new HashMap<>();
    this.boardGrid = buildGrid();
  }

  /**
   * Returns the GridPane representing the game board.
   *
   * @return the board grid
   */
  public GridPane getBoardGrid() {
    return boardGrid;
  }

  /**
   * Returns the map of tile numbers to StackPanes.
   *
   * @return tile map
   */
  public Map<Integer, StackPane> getTileMap() {
    return tileMap;
  }

  /**
   * Builds the board grid and maps tile numbers.
   *
   * @return the board grid
   */
  private GridPane buildGrid() {
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);

    int tileNumber = 1;

    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        StackPane tile = new StackPane();
        tile.setPrefSize(TILE_SIZE, TILE_SIZE);

        Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
        rect.setFill(Color.BEIGE);
        rect.setStroke(Color.BLACK);

        tile.getChildren().add(rect);

        int effectiveRow = ROWS - 1 - row;
        int effectiveCol = (row % 2 == 0) ? col : COLS - 1 - col;

        grid.add(tile, effectiveCol, effectiveRow);
        tileMap.put(tileNumber, tile);
        tileNumber++;
      }
    }

    return grid;
  }
}
