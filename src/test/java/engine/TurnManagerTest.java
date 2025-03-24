package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import board.Board;
import gameplay.Dice;
import gameplay.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"SpellCheckingInspection"})
class TurnManagerTest {

  // A simple Dice subclass that always returns a fixed roll value.
  static class FixedDice extends Dice {

    private final int fixedValue;

    public FixedDice(int fixedValue) {
      super(1); // Using one die, as in the game.
      this.fixedValue = fixedValue;
    }

    @Override
    public void rollDice() {
      // No action needed; the roll is fixed.
    }

    @Override
    public int getRollSum() {
      return fixedValue;
    }
  }

  // A Dice subclass that returns values from a sequence.
  static class SequenceDice extends Dice {

    private final int[] values;
    private int index;

    public SequenceDice(int[] values) {
      super(1);
      this.values = values;
      this.index = 0;
    }

    @Override
    public void rollDice() {
      // No action needed.
    }

    @Override
    public int getRollSum() {
      if (index < values.length) {
        return values[index++];
      } else {
        return values[values.length
            - 1]; // Continue returning the last value if sequence is exhausted.
      }
    }
  }

  @Test
  void testManageTurns_WinCondition_SinglePlayer() {
    Board board = new Board();
    List<Player> players = new ArrayList<>();
    Player player = new Player("Bobby", board.getTile(0));
    players.add(player);

    // Calculate steps needed from starting tile (assumed id 1) to reach tile 90.
    int stepsNeeded = 90 - player.getCurrentTile().getTileId();
    FixedDice dice = new FixedDice(stepsNeeded);

    TurnManager turnManager = new TurnManager(players, dice, board);
    turnManager.manageTurns();

    assertEquals(90, player.getCurrentTile().getTileId(), "Player should reach tile 90");
  }

  @Test
  void testOverboardMove() {
    Board board = new Board();
    List<Player> players = new ArrayList<>();
    Player player = new Player("Bobby", board.getTile(0));
    players.add(player);

    // Use a dice roll value that exceeds the necessary steps (e.g., 100).
    FixedDice dice = new FixedDice(100);
    TurnManager turnManager = new TurnManager(players, dice, board);
    turnManager.manageTurns();

    // Even with an oversized roll, the player's position should cap at tile 90.
    assertEquals(90, player.getCurrentTile().getTileId(), "Player should not move past tile 90");
  }

  @Test
  void testMultiplePlayers() {
    Board board = new Board();
    List<Player> players = new ArrayList<>();
    // Both players are named Bobby.
    Player player1 = new Player("Bobby", board.getTile(0));
    Player player2 = new Player("Bobby", board.getTile(0));
    players.add(player1);
    players.add(player2);

    // Simulate a sequence of dice rolls:
    // - First turn: player1 rolls 3 (tile 1 -> tile 4).
    // - Second turn: player2 rolls 4 (tile 1 -> tile 5).
    // - Third turn: player1 rolls 86 (tile 4 -> tile 90, winning the game).
    int[] diceSequence = {3, 4, 86};
    SequenceDice dice = new SequenceDice(diceSequence);
    TurnManager turnManager = new TurnManager(players, dice, board);
    turnManager.manageTurns();

    // Verify that player1 wins by reaching tile 90.
    assertEquals(90, player1.getCurrentTile().getTileId(), "Player 1 should reach tile 90 and win");
    // Verify that player2 remains on tile 5.
    assertEquals(5, player2.getCurrentTile().getTileId(), "Player 2 should be on tile 5");
  }

  @Test
  void testNoPlayers() {
    Board board = new Board();
    List<Player> players = new ArrayList<>();
    FixedDice dice = new FixedDice(10);
    TurnManager turnManager = new TurnManager(players, dice, board);

    // Since the players list is empty, trying to manage turns should throw an exception.
    assertThrows(IndexOutOfBoundsException.class, turnManager::manageTurns,
        "Managing turns with no players should throw an IndexOutOfBoundsException");
  }
}
