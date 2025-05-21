package frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.*;
import engine.BoardGame;
import frontend.observer.BoardGameObserver;
import gameplay.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Builds a standard Snakes and Ladders board grid.
 */
public class GameBoardView implements BoardGameObserver {

  private static final int ROWS = 9;
  private static final int COLS = 10;
  private static final int TILE_SIZE = 60;
  private static final int TOKEN_RADIUS = 15;

  private final BoardGame boardGame;
  private final GridPane boardGrid;
  private final Map<Integer, StackPane> tileMap = new HashMap<>();
  private final Map<Player, Circle> playerIcons = new HashMap<>();

  /**
   * Creates a new GameBoardView.
   */
  public GameBoardView(BoardGame boardGame) {
    this.boardGame = boardGame;
    this.boardGrid = buildGrid();

    List<String> colors = List.of("red", "blue", "green", "orange");
    var players = boardGame.getPlayers();
    for (int i = 0; i < players.size(); i++) {
      Player player = players.get(i);
      Circle token = new Circle(TOKEN_RADIUS, Color.web(colors.get(i)));
      playerIcons.put(player, token);

      int tileId = player.getCurrentTile().getTileId();
      tileMap.get(tileId).getChildren().add(token);
    }
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

    Board board = boardGame.getBoard();
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++, tileNumber++) {
        StackPane cell = new StackPane();
        cell.setPrefSize(TILE_SIZE, TILE_SIZE);

        Tile tile = board.getTile(tileNumber - 1);
        Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
        rect.setStroke(Color.BLACK);

        TileAction tileAction = tile.getLandAction();
        if (tileAction instanceof LadderAction) {
          rect.setFill(Color.BROWN);
        } else if (tileAction instanceof SnakeAction) {
          rect.setFill(Color.GREEN);
        } else {
          rect.setFill(Color.LIGHTGRAY);
        }
        cell.getChildren().add(rect);

        Label labelTileNumber = new Label(String.valueOf(tileNumber));
        labelTileNumber.getStyleClass().add("tile-number");
        StackPane.setAlignment(labelTileNumber, Pos.TOP_LEFT);
        cell.getChildren().add(labelTileNumber);

        int displayRow = ROWS - 1 - row;
        int displayCol = (row % 2 == 0) ? col : COLS - 1 - col;
        grid.add(cell, displayCol, displayRow);
        tileMap.put(tileNumber, cell);
      }
    }
    return grid;
  }

  @Override
  public void update() {
    tileMap.values().forEach(stack ->
        stack.getChildren().removeIf(Circle.class::isInstance)
    );

    playerIcons.forEach((player, token) -> {
      int id = player.getCurrentTile().getTileId();
      tileMap.get(id).getChildren().add(token);
    });
  }

  public VBox buildStatusPane() {
    VBox statusPane = new VBox(5);
    statusPane.setPadding(new Insets(10));
    statusPane.setAlignment(Pos.TOP_LEFT);

    for (Player player : boardGame.getPlayers()) {
      Label label = new Label(player.getNameOfPiece() + ": " + player.getCurrentTile().getTileId());

      boardGame.addObserver(() ->
          label.setText(player.getNameOfPiece() + ": " + player.getCurrentTile().getTileId())
      );
      statusPane.getChildren().add(label);
    }
    return statusPane;
  }
}
