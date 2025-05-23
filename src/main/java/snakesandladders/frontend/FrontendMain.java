package snakesandladders.frontend;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches the JavaFX frontend for Snakes and Ladders.
 * Starts the GUI by creating a GameController.
 */
public class FrontendMain extends Application {

  /**
   * JavaFX entry point. Sets up the primary stage and game controller.
   *
   * @param primaryStage the main window
   */
  @Override
  public void start(Stage primaryStage) {
    new SnakesAndLaddersController(primaryStage);
  }

  /**
   * Launches the JavaFX application.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    launch(args);
  }
}
