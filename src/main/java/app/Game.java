package app;

import javafx.stage.Stage;

/**
 * A pluggable game, can initialize its own Scene/Stage
 */
public interface Game {
  String name();

  /**
   * Initialize and show this game
   * @param primaryStage
   */
  void start(Stage primaryStage);
}
