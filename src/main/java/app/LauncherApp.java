package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LauncherApp extends Application {
  @Override
  public void start(Stage primaryStage) {
    VBox root = new VBox(10);
    root.setPadding(new Insets(20));
    for (Game game : GameFactory.allGames()) {
      Button button = new Button(game.name());
      button.setPrefWidth(200);
      button.setOnAction(e -> game.start(primaryStage));
      root.getChildren().add(button);
    }
    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("Choose game");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
