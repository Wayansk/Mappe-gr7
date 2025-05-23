package snakesandladders.frontend;

import app.BaseGameController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import snakesandladders.engine.BoardGame;
import snakesandladders.frontend.util.BoardSetupHelper;
import snakesandladders.frontend.util.PlayerSetupHelper;
import snakesandladders.persistence.SnakesAndLaddersState;
import snakesandladders.persistence.SnakesAndLaddersStateMapper;
import snakesandladders.persistence.SnakesAndLaddersStateRepository;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controls the GUI for the Snakes and Ladders game.
 */
public class SnakesAndLaddersController extends BaseGameController {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 700;

  private final BoardGame boardGame;
  private final Stage stage;
  private final String currentBoardJson;
  private SnakesAndLaddersBoardView boardView;

  private final Button rollButton = new Button("Roll Dice");
  private final Label statusLabel = new Label("Welcome to Snakes & Ladders");

  private final SnakesAndLaddersStateRepository repo = new SnakesAndLaddersStateRepository();

  /**
   * Constructs the controller using a board chosen by the user.
   * <p>
   * Prompts for a JSON resource via {@link BoardSetupHelper}, may exit
   * if no selection is made.
   *
   * @param stage the primary JavaFX stage
   */
  public SnakesAndLaddersController(Stage stage) {
    this(stage, BoardSetupHelper.askBoardJsonResource());
  }

  /**
   * Full constructor that sets up the game with a specific board JSON.
   * <p>
   * Initializes players, loads the board image if available,
   * configures controls, and displays the UI.
   *
   * @param stage the primary JavaFX stage
   * @param boardJsonResource classpath path to the board-definition JSON
   */
  public SnakesAndLaddersController(Stage stage, String boardJsonResource) {
    super(stage);
    this.stage = stage;
    this.currentBoardJson = boardJsonResource;
    this.boardGame = new BoardGame(boardJsonResource);

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

      boardView = new SnakesAndLaddersBoardView(boardGame, tileSize, tileSize);

      ImageView imageView = new ImageView(boardImage);
      imageView.setPreserveRatio(false);
      imageView.setFitWidth(tileSize * cols);
      imageView.setFitHeight(tileSize * rows);

      boardPane = new StackPane(imageView, boardView.getBoardGrid());
      StackPane.setAlignment(imageView, Pos.TOP_LEFT);
    } else {
      boardView = new SnakesAndLaddersBoardView(boardGame);
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
    root.setTop(new VBox(menuBar, controls));
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

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  private void saveGame() {
    SnakesAndLaddersState snakesAndLaddersState = SnakesAndLaddersStateMapper.toState(boardGame);
    repo.save(List.of(snakesAndLaddersState));
    showAlert("Snakes & Ladders game saved.");
  }

  private void loadGame() {
    List<SnakesAndLaddersState> saved = repo.loadAll();
    if (saved.isEmpty()) {
      showAlert("No saved Snakes and Ladders game found.");
      return;
    }
    SnakesAndLaddersState snakesAndLaddersState = saved.get(0);
    SnakesAndLaddersStateMapper.fromState(boardGame, snakesAndLaddersState);
    boardView.update();
    statusLabel.setText("Turn: " + boardGame.getCurrentPlayer().getNameOfPiece());
  }

  @Override
  protected List<MenuItem> getExtraMenuItems() {
    MenuItem changeBoard = new MenuItem("Change Board");
    changeBoard.setOnAction(e -> {
      String newJson = BoardSetupHelper.askBoardJsonResource();
      new SnakesAndLaddersController(stage, newJson);
    });
    return List.of(changeBoard);
  }

  /**
   * Restarts the current game with the same board configuration.
   *
   * @param stage the primary Stage to reinitialize
   */
  @Override
  protected void onRestart(Stage stage) {
    new SnakesAndLaddersController(stage, currentBoardJson);
  }

  @Override
  protected void onSave() {
    saveGame();
  }

  @Override
  protected void onLoad() {
    loadGame();
  }
}
