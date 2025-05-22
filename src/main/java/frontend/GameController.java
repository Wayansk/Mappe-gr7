package frontend;

import engine.BoardGame;
import frontend.util.BoardSetupHelper;
import frontend.util.PlayerSetupHelper;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Controls the GUI for the Snakes and Ladders game.
 */
public class GameController {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 650;

  private final BoardGame boardGame;
  private GameBoardView boardView;

  private final Button rollButton = new Button("Roll Dice");
  private final Label statusLabel = new Label("Welcome to Snakes & Ladders");

  /**
   * Creates a new game controller and starts the game setup.
   *
   * @param stage the JavaFX primary stage
   */
  public GameController(Stage stage) {
    String boardJsonResource = BoardSetupHelper.askBoardJsonResource();
    boardGame = new BoardGame(boardJsonResource);

    Optional<Integer> optionalPlayerCount = PlayerSetupHelper.askPlayerCount();
    if (optionalPlayerCount.isEmpty()) {
      Platform.exit();
      return;
    }
    int playerCount = optionalPlayerCount.get();

    List<String> playerNames = PlayerSetupHelper.collectPlayerNames(playerCount);
    playerNames.forEach(boardGame::addPlayer);

    String jsonFileName = boardJsonResource.substring(boardJsonResource.lastIndexOf('/') + 1);
    String imageResourcePath = "/images/" + jsonFileName.replace(".json", ".png");

    InputStream imageStream = getClass().getResourceAsStream(imageResourcePath);
    Region boardPane;
    if (imageStream != null) {
      Image boardImage = new Image(imageStream);

      int cols = boardGame.getBoard().getCols();
      int rows = boardGame.getBoard().getRows();

      double rawTileWidth = boardImage.getWidth() / cols;
      double rawTileHeight = boardImage.getHeight() / rows;
      double tileSize = Math.min(rawTileWidth, rawTileHeight);

      boardView = new GameBoardView(boardGame, tileSize, tileSize);

      ImageView imageView = new ImageView(boardImage);
      imageView.setPreserveRatio(false);
      imageView.setFitWidth(tileSize * cols);
      imageView.setFitHeight(tileSize * rows);

      boardPane = new StackPane(imageView, boardView.getBoardGrid());
      StackPane.setAlignment(imageView, Pos.TOP_LEFT);
    } else {
      boardView = new GameBoardView(boardGame);
      GridPane grid = boardView.getBoardGrid();
      double totalWidth = (double) boardView.getCols() * 60;
      double totalHeight = (double) boardView.getRows() * 60;
      grid.setPrefSize(totalWidth, totalHeight);
      grid.setMinSize(totalWidth, totalHeight);
      grid.setMaxSize(totalWidth, totalHeight);
      boardPane = new StackPane(grid);
    }

    boardGame.addObserver(boardView);
    rollButton.getStyleClass().add("roll-dice-button");
    rollButton.setOnAction(e -> playTurn());

    HBox controls = new HBox(10, rollButton, statusLabel);
    controls.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setTop(controls);
    root.setCenter(boardPane);
    root.setRight(boardView.buildStatusPane());

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets().add(Objects.requireNonNull(
        getClass().getResource("/css/style.css").toExternalForm())
    );

    stage.setScene(scene);
    stage.setTitle("Snakes & ladders");
    stage.show();

    boardView.update();
  }

  private void playTurn() {
    boardGame.playNextTurn().ifPresent(turnResult -> {
      statusLabel.setText(turnResult.player().getNameOfPiece() + " rolled a " + turnResult.rolledValue());
      if (turnResult.hasWon()) {
        statusLabel.setText(turnResult.player().getNameOfPiece() + " is the winner");
        rollButton.setDisable(true);
      }
    });
  }
}
