package engine;

import board.Board;
import board.Tile;
import gameplay.Dice;
import gameplay.Player;
import java.util.List;
import java.util.Optional;

/**
 * Handles the logic for turn-based gameplay. Manages whose turn it is, rolls dice, moves players,
 * and checks for victory.
 */
public class TurnManager {

  private final List<Player> players;
  private final Dice dice;
  private final Board board;
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
   * Plays a single turn and returns the result.
   *
   * @return Returns a TurnResult if the game is running, or empty if it's over.
   */
  public Optional<TurnResult> playTurn() {
    if (gameOver || players.isEmpty()) {
      return Optional.empty();
    }

    Player currentPlayer = players.get(currentPlayerIndex);
    dice.rollDice();
    int steps = dice.getRollSum();

    Tile currentTile = currentPlayer.getCurrentTile();
    int newTileId = Math.min(currentTile.getTileId() + steps, board.getTileCount());
    Tile newTile = board.getTile(newTileId - 1);

    currentPlayer.setCurrentTile(newTile);
    newTile.landPlayer(currentPlayer);

    boolean hasWon = newTile.getTileId() == board.getTileCount();
    if (hasWon) {
      gameOver = true;
    }

    TurnResult result = new TurnResult(currentPlayer, steps, newTile.getTileId(), hasWon);

    if (!hasWon) {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    return Optional.of(result);
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

  /**
   * Returns an unmodifiable list of players.
   *
   * @return the players in the game
   */
  public List<Player> getPlayers() {
    return List.copyOf(players);
  }
}
