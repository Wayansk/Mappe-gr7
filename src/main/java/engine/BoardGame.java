package engine;

import board.Board;
import gameplay.Dice;
import gameplay.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class creates the game with the players, and uses the turn-manager to have the game running.
 * It also creates the players on the correct square.
 */
public class BoardGame {

  private final Board board;
  private final List<Player> players;
  private final engine.TurnManager turnManager;

  /**
   * Constructs a BoardGame by initializing the board, dice, and an empty player list. It also
   * creates a TurnManager to handle turn-based gameplay.
   */
  public BoardGame() {
    board = new Board();
    Dice dice = new Dice(1); // Using one die for classic Snakes and Ladders.
    players = new ArrayList<>();
    turnManager = new engine.TurnManager(players, dice, board);
  }

  /**
   * Adds a new player to the game. Players start on the first tile
   *
   * @param name the name of the player
   */
  public void addPlayer(String name) {
    Player player = new Player(name, board.getTile(0));
    players.add(player);
  }

  /**
   * Starts the game by prompting the user for the number of players and their names, then
   * initiating the turn-based gameplay loop.
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter number of players:");
    int numPlayers = 0;
    while (numPlayers <= 0) {
      try {
        numPlayers = Integer.parseInt(scanner.nextLine());
        if (numPlayers <= 0) {
          System.out.println("Please enter a positive number.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
    for (int i = 1; i <= numPlayers; i++) {
      System.out.println("Enter name for player " + i + ":");
      String name = scanner.nextLine();
      addPlayer(name);
    }
    // TurnManager to handle the gameplay.
    turnManager.manageTurns();
  }
}
