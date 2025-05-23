package snakesandladders.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import snakesandladders.engine.BoardGame;
import snakesandladders.frontend.observer.BoardGameObserver;
import snakesandladders.gameplay.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds a standard Snakes and Ladders board grid.
 */
public class SnakesAndLaddersBoardView implements BoardGameObserver {

  private static final int TOKEN_SIZE = 15;

  private final BoardGame boardGame;
  private final int numberOfRows;
  private final int numberOfColumns;
  private final double tileWidth;
  private final double tileHeight;
  private final double tokenRadius;

  private final GridPane boardGrid;
  private final Map<Integer, StackPane> tileMap = new HashMap<>();
  private final Map<Player, Circle> playerIcons = new HashMap<>();


  public SnakesAndLaddersBoardView(BoardGame boardGame) {
    this(boardGame, 60, 60);
  }

  /**
   * Creates a new GameBoardView.
   */
  public SnakesAndLaddersBoardView(BoardGame boardGame, double tileWidth, double tileHeight) {
    this.boardGame = boardGame;
    this.numberOfRows = boardGame.getBoard().getRows();
    this.numberOfColumns = boardGame.getBoard().getCols();
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
    this.tokenRadius = TOKEN_SIZE;

    this.boardGrid = buildGrid();
    initPlayerTokens();

    boardGame.addObserver(this);
    update();
  }

  /**
   * Builds the board grid and maps tile numbers.
   *
   * @return the board grid
   */
  private GridPane buildGrid() {
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);

    for (int colIndex = 0; colIndex < numberOfColumns; colIndex++) {
      ColumnConstraints columnConstraints = new ColumnConstraints(tileWidth, tileWidth, tileWidth);
      columnConstraints.setHgrow(Priority.NEVER);
      grid.getColumnConstraints().add(columnConstraints);
    }
    for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
      RowConstraints rowConstraints = new RowConstraints(tileHeight, tileHeight, tileHeight);
      rowConstraints.setVgrow(Priority.NEVER);
      grid.getRowConstraints().add(rowConstraints);
    }
    grid.setPrefSize(tileWidth * numberOfColumns, tileHeight * numberOfRows);

    int tileNumber = 1;
    for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
      for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
        StackPane cell = new StackPane();
        cell.setPrefSize(tileWidth, tileHeight);

        Rectangle background = new Rectangle(tileWidth, tileHeight);
        background.setStroke(Color.BLACK);
        background.setFill(Color.TRANSPARENT);
        cell.getChildren().add(background);

        Label tileNumberLabel = new Label(String.valueOf(tileNumber));
        tileNumberLabel.getStyleClass().add("tile-number");
        StackPane.setAlignment(tileNumberLabel, Pos.TOP_LEFT);
        cell.getChildren().add(tileNumberLabel);

        int displayRow = numberOfRows - 1 - rowIndex;
        int displayColumn = (rowIndex % 2 == 0) ? columnIndex : numberOfColumns - 1 - columnIndex;
        grid.add(cell, displayColumn, displayRow);

        tileMap.put(tileNumber, cell);
        tileNumber++;
      }
    }
    return grid;
  }

  private void initPlayerTokens() {
    List<String> colors = List.of("red", "blue", "green", "orange", "purple");
    List<Player> players = boardGame.getPlayers();

    for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
      Player player = players.get(playerIndex);
      Circle token = new Circle(tokenRadius, Color.web(colors.get(playerIndex % colors.size())));
      playerIcons.put(player, token);
    }
  }

  @Override
  public void update() {
    tileMap.values().forEach(cell ->
        cell.getChildren().removeIf(Circle.class::isInstance)
    );

    playerIcons.forEach((player, token) -> {
      int tileId = player.getCurrentTile().getTileId();
      StackPane cell = tileMap.get(tileId);
      if (cell != null) {
        cell.getChildren().add(token);
      } else {
        System.err.println("[GameBoardViw] Warning: no cell mapped for tileId=" + tileId);
      }
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

  /**
   * Returns the GridPane representing the game board.
   *
   * @return the board grid
   */
  public GridPane getBoardGrid() {
    return boardGrid;
  }


  public int getRows() {
    return numberOfRows;
  }

  public int getCols() {
    return numberOfColumns;
  }
}
