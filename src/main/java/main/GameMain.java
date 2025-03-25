package main;

import engine.BoardGame;

/**
 * Class that starts the application.
 */
public class GameMain {

  /**
   * Launches the BoardGame application. This method creates an instance of {@code BoardGame} and
   * calls its {@code start()} method to begin the game.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    BoardGame boardGame = new BoardGame();
    boardGame.start();
  }

}
