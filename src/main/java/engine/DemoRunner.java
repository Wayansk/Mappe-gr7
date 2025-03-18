package engine;

/**
 * A simple file to test run the demo
 */
public class  DemoRunner {
  public static void main(String[] args) {
    BoardGame game = new BoardGame();

    game.setupPlayers();

    game.setupBoard(50);

    game.startGame();
  }
}
