package snakesandladders.engine;

import snakesandladders.board.Board;
import snakesandladders.board.Tile;
import snakesandladders.board.TileAction;
import snakesandladders.frontend.observer.BoardGameObserver;
import snakesandladders.gameplay.Dice;
import snakesandladders.gameplay.Player;

import java.util.*;

/**
 * Handles the logic for turn-based gameplay. Manages whose turn it is, rolls dice, moves players,
 * and checks for victory.
 */
public class TurnManager {

  private final Board board;
  private final Dice dice;
  private final List<Player> players;
  private final List<BoardGameObserver> observers = new ArrayList<>();

  private int currentPlayerIndex = 0;
  private boolean gameOver = false;

  /**
   * Creates a new TurnManager.
   *
   * @param players the list of players
   * @param dice    the dice object to use
   * @param board   the game board
   */
  public TurnManager(List<Player> players, Dice dice, Board board) {
    this.players = players;
    this.dice = dice;
    this.board = board;
  }

  /**
   * Returns an unmodifiable list of players.
   *
   * @return the players in the game
   */
  public List<Player> getPlayers() {
    return List.copyOf(players);
  }

  /**
   * Returns the current player.
   *
   * @return the player whose turn it is
   */
  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  /**
   * Returns whether the game is over.
   *
   * @return true if someone has won, false otherwise
   */
  public boolean isGameOver() {
    return gameOver;
  }

  public void addObserver(BoardGameObserver boardGameObserver) {
    observers.add(boardGameObserver);
  }

  private void notifyObservers() {
    for (BoardGameObserver observer : observers) {
      observer.update();
    }
  }

  /**
   * Plays a single turn and returns the result.
   *
   * @return Returns a TurnResult if the game is running, or empty if it's over.
   */
  public Optional<TurnResult> playTurn() {
    if (gameOver || players.isEmpty()) {
      return Optional.empty();
    }

    dice.rollDice();
    int rolled = dice.getRollSum();

    Player currentPlayer = players.get(currentPlayerIndex);
    int fromId = currentPlayer.getCurrentTile().getTileId();
    int toId = Math.min(fromId + rolled, board.getTileCount());
    Tile newTile = board.getTile(toId - 1);

    currentPlayer.setCurrentTile(newTile);

    TileAction tileAction = newTile.getLandAction();
    if (tileAction != null) {
      tileAction.perform(currentPlayer);
    }

    boolean hasWon = currentPlayer.getCurrentTile().getTileId() == board.getTileCount();
    if (hasWon) {
      gameOver = true;
    } else {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    notifyObservers();
    return Optional.of(new TurnResult(
        currentPlayer,
        rolled,
        currentPlayer.getCurrentTile().getTileId(),
        hasWon
    ));
  }
}
