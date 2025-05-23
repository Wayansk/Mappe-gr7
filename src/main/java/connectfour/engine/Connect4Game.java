package connectfour.engine;

import connectfour.model.Board;
import connectfour.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Connect4Game {
  private final Board board;
  private final List<Player> players = new ArrayList<>();
  private final List<BoardGameObserver> observers = new ArrayList<>();
  private int currentPlayerIndex = 0;
  private Player winner = null;

  public Connect4Game() {
    this.board = new Board();
  }

  public void addPlayer(Player player) {
    if (players.size() >= 2) {
      throw new IllegalStateException("Max two players");
    }
    players.add(player);
  }

  public Board getBoard() {
    return board;
  }

  public List<Player> getPlayers() {
    return List.copyOf(players);
  }

  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  public void addObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  private void notifyObservers() {
    observers.forEach(BoardGameObserver::update);
  }

  /**
   * Perform a turn at given column
   * @param column
   * @return TurnResult
   */
  public TurnResult playTurn(int column) {
    if (winner != null || board.isFull()) {
      throw new IllegalStateException("Game is over");
    }
    Player player = getCurrentPlayer();
    int row = board.dropDisc(column, player.getPiece());
    if (row < 0) {
      throw new IllegalArgumentException("Column is full");
    }
    boolean win = board.checkWinner() == player.getPiece();
    if (win) {
      winner = player;
    } else {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
      notifyObservers();
    }
    return new TurnResult(player, column, win);
  }

  public boolean isFinished() {
    return winner != null || board.isFull();
  }

  public Player getWinner() {
    return winner;
  }

  public void reset() {
    board.clear();
    currentPlayerIndex = 0;
    winner = null;
    notifyObservers();
  }

  public int getCurrentPlayerIndex() {
    return currentPlayerIndex;
  }

  public void setCurrentPlayerIndex(int index) {
    if (index < 0 || index >= players.size()) {
      throw new IllegalArgumentException("Invalid player index: " + index);
    }
    this.currentPlayerIndex = index;
    notifyObservers();
  }
}
