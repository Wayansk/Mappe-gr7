package snakesandladders.engine;

import snakesandladders.board.Board;
import snakesandladders.frontend.observer.BoardGameObserver;
import snakesandladders.gameplay.Dice;
import snakesandladders.gameplay.Player;

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

  public BoardGame() {
    this("/json_boards/Board_1.json");
  }

  /**
   * Creates a new BoardGame instance. Initializes board, dice, and an empty player list.
   */
  public BoardGame(String boardJsonResource) {
    this.board = new Board(boardJsonResource);
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

  public String getBoardDefinitionPath() {
    return board.getResourcePath();
  }

  public void movePlayerTo(String playerName, int tileId) {
    turnManager.movePlayerTo(playerName, tileId);
  }

  public void setCurrentPlayerIndex(String playerName) {
    turnManager.setCurrentPlayerIndex(playerName);
  }

  public List<Player> getPlayer() {
    return turnManager.getPlayers();
  }

  public void reset() {
    turnManager.reset();
  }
}
