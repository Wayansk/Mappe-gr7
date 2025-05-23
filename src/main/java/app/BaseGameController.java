package app;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;

import java.util.List;

public abstract class BaseGameController {
  protected final MenuBar menuBar = new MenuBar();

  /**
   * Constructs the controller and builds the common game menu.
   *
   * @param stage the primary stage on which game scenes are shown
   */
  protected BaseGameController(Stage stage) {
    buildCommonMenu(stage);
  }

  /**
   * Builds the standard "Game" menu, adds common menu items, and appends any extra items.
   *
   * @param stage the Stage used for restart or changing games
   */
  private void buildCommonMenu(Stage stage) {
    Menu gameMenu = new Menu("Game");
    gameMenu.getItems().addAll(
        new MenuItem("Save") {{ setOnAction(e -> onSave()); }},
        new MenuItem("Load") {{ setOnAction(e -> onLoad()); }},
        new MenuItem("Restart") {{ setOnAction(e -> onRestart(stage)); }},
        new SeparatorMenuItem(),
        new MenuItem("Change Game") {{ setOnAction(e -> onChangeGame(stage)); }}
    );
    menuBar.getMenus().add(gameMenu);
    getExtraMenuItems().forEach(gameMenu.getItems()::add);
  }

  protected abstract void onSave();
  protected abstract void onLoad();
  protected abstract void onRestart(Stage stage);
  protected void onChangeGame(Stage stage) {
    new app.LauncherApp().start(stage);
  }

  /**
   * Allows subclasses to supply additional menu items to the "Game" menu.
   *
   * @return a list of extra {@link MenuItem} instances to append
   */
  protected abstract List<MenuItem> getExtraMenuItems();
}
