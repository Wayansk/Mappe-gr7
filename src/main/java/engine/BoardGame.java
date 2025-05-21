package engine;

import board.Board;
import frontend.observer.BoardGameObserver;
import gameplay.Dice;
import gameplay.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Coordinates the setup and control of the board game. Holds references to the board, players, and
 * turn manager.
 */
public class BoardGame {

  private final Board board;
  private final List<Player> players;
  private final TurnManager turnManager;

  /**
   * Creates a new BoardGame instance. Initializes board, dice, and an empty player list.
   */
  public BoardGame() {
    this.board = new Board();
    this.players = new ArrayList<>();
    this.turnManager = new TurnManager(players, new Dice(1), board);
  }

  /**
   * Adds a new player to the game.
   *
   * @param name the player's name
   */
  public void addPlayer(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be blank");
    }
    players.add(new Player(name, board.getTile(0)));
  }

  /**
   * Plays the next turn in the game.
   *
   * @return an Optional TurnResult describing the outcome of the turn
   */
  public Optional<TurnResult> playNextTurn() {
    return turnManager.playTurn();
  }

  /**
   * Returns the current player.
   *
   * @return the player whose turn it is
   */
  public Player getCurrentPlayer() {
    return turnManager.getCurrentPlayer();
  }

  /**
   * Returns an unmodifiable list of all players.
   *
   * @return the list of players
   */
  public List<Player> getPlayers() {
    return turnManager.getPlayers();
  }

  /**
   * Checks if the game has ended.
   *
   * @return true if a player has won, false otherwise
   */
  public boolean isGameOver() {
    return turnManager.isGameOver();
  }

  /**
   * Gets the game board.
   *
   * @return the board instance
   */
  public Board getBoard() {
    return board;
  }

  public void addObserver(BoardGameObserver boardGameObserver) {
    turnManager.addObserver(boardGameObserver);
  }
}
