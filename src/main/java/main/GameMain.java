package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameMain extends Application {
  @Override
  public void start(Stage primaryStage) {
    GamePanel gamePanel = new GamePanel();

    StackPane root = new StackPane(gamePanel.getCanvas());
    Scene scene = new Scene(root, 800, 600);

    primaryStage.setTitle("placeholder text");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
