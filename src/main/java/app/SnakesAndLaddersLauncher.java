package app;

import javafx.stage.Stage;
import snakesandladders.frontend.FrontendMain;

public class SnakesAndLaddersLauncher implements Game{
  @Override
  public String name() {
    return "Snakes and Ladders";
  }

  @Override
  public void start(Stage primaryStage) {
    new FrontendMain().start(primaryStage);
  }
}
