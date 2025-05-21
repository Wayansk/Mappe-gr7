package frontend;

import engine.BoardGame;
import frontend.util.PlayerSetupHelper;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

/**
 * Controls the GUI for the Snakes and Ladders game.
 */
public class GameController {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 650;

  private final BoardGame boardGame;
  private final GameBoardView boardView;

  private final Button rollButton = new Button("Roll Dice");
  private final Label statusLabel = new Label("Welcome to Snakes & Ladders");

  /**
   * Creates a new game controller and starts the game setup.
   *
   * @param stage the JavaFX primary stage
   */
  public GameController(Stage stage) {
    boardGame = new BoardGame();

    int playerCount = PlayerSetupHelper.askPlayerCount();
    List<String> playerNames = PlayerSetupHelper.collectPlayerNames(playerCount);
    for (String playerName : playerNames) {
      boardGame.addPlayer(playerName);
    }

    boardView = new GameBoardView(boardGame);
    boardGame.addObserver(boardView);

    rollButton.setOnAction(e -> playTurn());

    HBox controls = new HBox(10, rollButton, statusLabel);
    controls.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setTop(controls);
    root.setCenter(boardView.getBoardGrid());
    root.setRight(boardView.buildStatusPane());

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets().add(requireNonNull(
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
