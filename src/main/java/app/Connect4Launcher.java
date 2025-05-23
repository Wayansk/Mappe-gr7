package app;

import connectfour.frontend.FrontendMain;
import javafx.stage.Stage;

public class Connect4Launcher implements Game {
  @Override
  public String name() {
    return "Connect 4";
  }

  @Override
  public void start(Stage primaryStage) {
    new FrontendMain().start(primaryStage);
  }
}
