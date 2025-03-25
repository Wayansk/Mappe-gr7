package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import board.Board;

import gameplay.Dice;
import gameplay.Player;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TurnManagerTest {

  // Dice subclass that always returns a fixed value.
  static class FixedDice extends Dice {
    private final int fixedValue;

    public FixedDice(int fixedValue) {
      super(1); // Only need one die, but this is not used because rollDice() does nothing.
      this.fixedValue = fixedValue;
    }

    @Override
    public void rollDice() {
      // No action needed; the roll value is fixed.
    }

    @Override
    public int getRollSum() {
      return fixedValue;
    }
  }

  @Test
  void testManageTurnsSinglePlayerWin() throws Exception {
    // Simulate user pressing "Enter" once.
    String simulatedInput = "\n";

    // Preserve the original System.in to restore later.
    InputStream originalIn = System.in;

    try (ByteArrayInputStream testIn =
        new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))) {

      System.setIn(testIn);

      // Create a board and a single player starting at tile 0.
      Board board = new Board();
      Player player = new Player("SinglePlayer", board.getTile(0));
      List<Player> players = new ArrayList<>();
      players.add(player);

      // Use a FixedDice with a high roll to ensure the player reaches tile 90 immediately.
      Dice fixedDice = new FixedDice(100);

      // Create TurnManager with a single player and fixed dice.
      TurnManager turnManager = new TurnManager(players, fixedDice, board);

      // Run the method under test. It will read from the simulated input.
      turnManager.manageTurns();

      // After one turn, the player should be on tile 90.
      assertEquals(90, player.getCurrentTile().getTileId(),
          "Player should have reached tile 90 and won the game.");
    } finally {
      System.setIn(originalIn);
    }
  }

  @Test
  void testManageTurnsMultiplePlayers() throws Exception {
    // Simulate user pressing "Enter" once.
    String simulatedInput = "\n";

    InputStream originalIn = System.in;
    try (ByteArrayInputStream testIn =
        new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))) {

      System.setIn(testIn);

      // Create a board and two players starting at tile 0.
      Board board = new Board();
      Player player1 = new Player("Player1", board.getTile(0));
      Player player2 = new Player("Player2", board.getTile(0));
      List<Player> players = new ArrayList<>();
      players.add(player1);
      players.add(player2);

      // Use a FixedDice that ensures player1 immediately reaches tile 90.
      Dice fixedDice = new FixedDice(100);

      TurnManager turnManager = new TurnManager(players, fixedDice, board);

      turnManager.manageTurns();

      // Player1 should have won; Player2 should not have moved.
      assertEquals(90, player1.getCurrentTile().getTileId(),
          "Player1 should have reached tile 90 and won the game.");
      assertEquals(1, player2.getCurrentTile().getTileId(),
          "Player2 should remain on the starting tile (tile ID 1).");
    } finally {
      System.setIn(originalIn);
    }
  }
}
