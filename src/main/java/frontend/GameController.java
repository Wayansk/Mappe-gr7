package frontend;

import engine.BoardGame;
import frontend.util.UiHelper;
import gameplay.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Controls the GUI for the Snakes and Ladders game.
 */
public class GameController {

  private static final int TILE_SIZE = 60;

  private final BoardGame boardGame;
  private final Map<Player, Circle> playerIcons = new HashMap<>();
  private final GameBoardView boardView;

  private Label statusLabel;
  private Button rollButton;

  /**
   * Creates a new game controller and starts the game setup.
   *
   * @param stage the JavaFX primary stage
   */
  public GameController(Stage stage) {
    this.boardGame = new BoardGame();
    this.boardView = new GameBoardView();
    init(stage);
  }

  /**
   * Initializes the game window and controls.
   *
   * @param stage the JavaFX stage
   */
  private void init(Stage stage) {
    List<String> playerNames = collectPlayerNames();
    for (String name : playerNames) {
      boardGame.addPlayer(name);
    }

    setupPlayerIcons();

    statusLabel = new Label("Click Roll to begin!");
    rollButton = new Button("Roll Dice");
    rollButton.setOnAction(e -> playTurn());

    VBox controls = new VBox(10, statusLabel, rollButton);
    controls.setAlignment(Pos.CENTER);
    controls.setPrefHeight(100);

    BorderPane root = new BorderPane();
    root.setCenter(boardView.getBoardGrid());
    root.setBottom(controls);

    int width = TILE_SIZE * 10;
    int height = TILE_SIZE * 9 + 100;

    stage.setScene(new Scene(root, width, height));
    stage.setTitle("Snakes and Ladders");
    stage.show();
  }

  /**
   * Collects all player names from the user.
   *
   * @return list of player names
   */
  private List<String> collectPlayerNames() {
    int playerCount = askPlayerCount();
    List<String> names = new ArrayList<>();

    for (int i = 1; i <= playerCount; i++) {
      String name = askPlayerName(i);
      names.add(name);
    }

    return names;
  }

  private int askPlayerCount() {
    while (true) {
      Optional<String> input = UiHelper.promptText("Setup", "Enter number of players (1–5):", "2");

      if (input.isEmpty()) {
        continue;
      }

      try {
        int count = Integer.parseInt(input.get());
        if (count >= 1 && count <= 5) {
          return count;
        }
        UiHelper.showInfo("Please enter a number between 1 and 5.");
      } catch (NumberFormatException e) {
        UiHelper.showInfo("Invalid input. Please enter digits only.");
      }
    }
  }

  private String askPlayerName(int index) {
    while (true) {
      Optional<String> input = UiHelper.promptText("Enter name", "Name for Player " + index + ":",
          "Player " + index);
      if (input.isPresent() && !input.get().trim().isBlank()) {
        return input.get().trim();
      } else {
        UiHelper.showInfo("Please enter a valid name.");
      }
    }
  }

  /**
   * Sets up each player's token on the board.
   */
  private void setupPlayerIcons() {
    List<Color> colors = List.of(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PURPLE);
    int colorIndex = 0;

    for (Player player : boardGame.getPlayers()) {
      Color color = colors.get(colorIndex % colors.size());
      Circle icon = new Circle(10, color);

      playerIcons.put(player, icon);
      placeToken(player);

      colorIndex++;
    }
  }

  /**
   * Plays a turn and updates the board.
   */
  private void playTurn() {
    boardGame.playNextTurn().ifPresent(result -> {
      Player player = result.player();
      placeToken(player);

      statusLabel.setText(player.getNameOfPiece() + " rolled " + result.rolledValue());

      if (result.hasWon()) {
        statusLabel.setText(player.getNameOfPiece() + " wins!");
        rollButton.setDisable(true);
      }
    });
  }

  /**
   * Moves the player's token to the correct tile.
   *
   * @param player the player to move
   */
  private void placeToken(Player player) {
    Circle token = playerIcons.get(player);
    boardView.getTileMap().values().forEach(tile -> tile.getChildren().remove(token));

    int tileId = player.getCurrentTile().getTileId();
    StackPane target = boardView.getTileMap().get(tileId);
    if (target != null) {
      target.getChildren().add(token);
    }
  }
}
