package app;

import java.util.List;

public class GameFactory {
  private GameFactory() { }

  public static List<Game> allGames() {
    return List.of(
        new Connect4Launcher(),
        new SnakesAndLaddersLauncher()
    );
  }
}
