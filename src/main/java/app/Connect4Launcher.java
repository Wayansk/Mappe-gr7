package app;

import connectfour.frontend.FrontendMain;
import javafx.stage.Stage;


public class Connect4Launcher implements Game {
  @Override
  public String name() {
    return "Connect 4";
  }

  /**
   * Starts the Connect 4 game by delegating to the Connect Four frontend.
   *
   * @param primaryStage the primary JavaFX stage on which the game scene will be shown
   */
  @Override
  public void start(Stage primaryStage) {
    new FrontendMain().start(primaryStage);
  }
}
