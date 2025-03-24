package game;

import board.Board;
import board.Tile;
import gameplay.Dice;
import gameplay.Player;
import java.util.List;

public class TurnManager {
  private final List<Player> players;
  private final Dice dice;
  private final Board board;
  private boolean gameOver;

  /**
   * Constructs a TurnManager with the provided list of players, dice, and board.
   *
   * @param players the list of players participating in the game
   * @param dice    the Dice object to be used for each turn
   * @param board   the game board
   */
  public TurnManager(List<Player> players, Dice dice, Board board) {
    this.players = players;
    this.dice = dice;
    this.board = board;
    this.gameOver = false;
  }

  /**
   * Manages the turn-based gameplay loop.
   * Each player takes a turn to roll the dice, move along the board,
   * and trigger any tile-specific actions. The loop ends when a player
   * reaches the final tile (tile 90).
   */
  public void manageTurns() {
    int currentPlayerIndex = 0;

    while (!gameOver) {
      Player currentPlayer = players.get(currentPlayerIndex);
      System.out.println("\n" + currentPlayer.getNameOfPiece() + "'s turn:");

      // Player rolls the dice.
      dice.rollDice();
      int moveSteps = dice.getRollSum();
      System.out.println(currentPlayer.getNameOfPiece() + " rolled: " + moveSteps);

      // Calculate the new tile number
      Tile currentTile = currentPlayer.getCurrentTile();
      int currentTileId = currentTile.getTileId();
      int newTileId = currentTileId + moveSteps;

      // sets 90 as max
      if (newTileId > 90) {
        newTileId = 90;
      }

      // Retrieve the new tile from the board.
      Tile newTile = board.getTile(newTileId - 1);
      currentPlayer.setCurrentTile(newTile);
      System.out.println(currentPlayer.getNameOfPiece() + " moved to tile " + newTile.getTileId());

      // Trigger any action associated with landing on the tile (e.g., ladder or snake).
      newTile.landPlayer(currentPlayer);

      // Check win condition.
      if (newTile.getTileId() == 90) {
        System.out.println("\n" + currentPlayer.getNameOfPiece() + " has won the game!");
        gameOver = true;
      } else {
        // Proceed to the next player.
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
      }
    }
  }
}
