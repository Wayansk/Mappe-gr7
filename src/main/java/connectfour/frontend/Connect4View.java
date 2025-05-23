package connectfour.frontend;

import connectfour.engine.BoardGameObserver;
import connectfour.engine.Connect4Game;
import connectfour.model.Board;
import connectfour.model.Piece;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Connect4View implements BoardGameObserver {
  private static final int TILE_SIZE = 80;
  private final Connect4Game connectGame;
  private final GridPane gridPane = new GridPane();

  public Connect4View(Connect4Game connectGame) {
    this.connectGame = connectGame;
    buildGrid();
    connectGame.addObserver(this);
  }

  private void buildGrid() {
    Board board = connectGame.getBoard();
    for (int row = 0; row < board.getRows(); row++) {
      for (int col = 0; col < board.getCols(); col++) {
        StackPane cellPane = new StackPane();
        Rectangle background = new Rectangle(TILE_SIZE, TILE_SIZE);
        background.setFill(Color.LAVENDER);
        background.setStroke(Color.BLACK);
        cellPane.getChildren().add(background);
        gridPane.add(cellPane, col, row);
      }
    }
    update();
  }

  public GridPane getGridPane() {
    return gridPane;
  }

  @Override
  public void update() {
    Board board = connectGame.getBoard();
    for (int row = 0; row < board.getRows(); row++) {
      for (int col = 0; col < board.getCols(); col++) {
        StackPane cellPane = (StackPane) getNode(row, col);
        cellPane.getChildren().removeIf(Circle.class::isInstance);
        Piece piece = board.getCell(row, col).getPiece();
        if (piece != null) {
          Circle disc = new Circle(TILE_SIZE * 0.4);
          disc.setFill(piece == Piece.RED ? Color.RED : Color.YELLOW);
          cellPane.getChildren().add(disc);
        }
      }
    }
  }

  private Node getNode(int row, int col) {
    for (var node : gridPane.getChildren()) {
      if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
        return node;
      }
    }
    return null;
  }
}
