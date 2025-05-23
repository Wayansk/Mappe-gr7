package connectfour.frontend;

import app.BaseGameController;
import connectfour.engine.Connect4Game;
import connectfour.engine.TurnResult;
import connectfour.frontend.util.Connect4PlayerSetupHelper;
import connectfour.model.Piece;
import connectfour.model.Player;
import connectfour.persistence.Connect4State;
import connectfour.persistence.Connect4StateMapper;
import connectfour.persistence.Connect4StateRepository;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Connect4Controller extends BaseGameController {
  private static final int TILE_SIZE = 80;

  private final Connect4Game connect4Game = new Connect4Game();
  private final Connect4View connect4View;
  private final GridPane controls = new GridPane();
  private final Label statusLabel = new Label();
  private final Connect4StateRepository repo = new Connect4StateRepository();

  /**
   * Constructs the controller, initializes players, view, controls, and stage.
   *
   * @param stage the primary JavaFX stage for the game
   */
  public Connect4Controller(Stage stage) {
    super(stage);

    List<String> playerNames = Connect4PlayerSetupHelper.collectPlayerNames();
    connect4Game.addPlayer(new Player(playerNames.get(0), Piece.RED));
    connect4Game.addPlayer(new Player(playerNames.get(1), Piece.YELLOW));

    connect4View = new Connect4View(connect4Game);
    statusLabel.setText("Turn: " + connect4Game.getCurrentPlayer().getName());

    controls.setPadding(new Insets(5));
    for (int col = 0; col < connect4Game.getBoard().getCols(); col++) {
      ColumnConstraints columnConstraints = new ColumnConstraints(TILE_SIZE);
      controls.getColumnConstraints().add(columnConstraints);

      Button button = new Button(String.valueOf(col + 1));
      button.setMaxWidth(Double.MAX_VALUE);
      final int column = col;
      button.setOnAction(e -> handleDrop(column));
      controls.add(button, col, 0);
    }

    BorderPane root = new BorderPane();
    root.setTop(menuBar);
    root.setTop(new VBox(menuBar, controls));
    root.setCenter(connect4View.getGridPane());
    root.setBottom(statusLabel);
    BorderPane.setMargin(statusLabel, new Insets(5));

    Scene scene = new Scene(root,
        (double) connect4Game.getBoard().getCols() * TILE_SIZE + 20,
        (double) connect4Game.getBoard().getRows() * TILE_SIZE + 100);

    stage.setScene(scene);
    stage.setTitle("Connect 4");
    stage.show();
  }

  /**
   * Handles a drop button click: plays a turn, updates view, and checks for win/draw.
   *
   * @param col zero-based column index for the move
   */
  private void handleDrop(int col) {
    TurnResult turnResult = connect4Game.playTurn(col);
    connect4View.update();

    if (turnResult.hasWon()) {
      statusLabel.setText("Winner: " + turnResult.player().getName());
      disableControls();
      showAlert(turnResult.player().getName() + " wins");
    } else if (connect4Game.isFinished()) {
      statusLabel.setText("Draw...");
      disableControls();
      showAlert("Board is full - draw");
    } else {
      statusLabel.setText("Turn: " + connect4Game.getCurrentPlayer().getName());
    }
  }
  private void disableControls() {
    controls.getChildren().forEach(n -> n.setDisable(true));
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  @Override
  protected void onSave() {
    Connect4State connect4State = Connect4StateMapper.toState(connect4Game);
    repo.save(connect4State);
    showAlert("Game saved");
  }

  @Override
  protected void onLoad() {
    List<Connect4State> saved = repo.loadAll();
    if (saved.isEmpty()) {
      showAlert("No saved game found");
    } else {
      Connect4StateMapper.fromState(connect4Game, saved.get(0));
      connect4View.update();
      statusLabel.setText("Turn: " + connect4Game.getCurrentPlayer().getName());
    }
  }

  @Override
  protected void onRestart(Stage stage) {
    connect4Game.reset();
    connect4View.update();
    statusLabel.setText("Turn: " + connect4Game.getCurrentPlayer().getName());
  }

  /**
   * Provides extra menu items for the "Game" menu; none for Connect 4.
   *
   * @return an empty list
   */
  @Override
  protected List<MenuItem> getExtraMenuItems() {
    return List.of();
  }
}
