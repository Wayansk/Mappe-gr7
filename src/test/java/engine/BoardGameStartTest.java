package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gameplay.Dice;
import gameplay.Player;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

class BoardGameStartTest {

  // A Dice subclass that always returns a fixed roll value.
  static class FixedDice extends Dice {

    private final int fixedValue;

    public FixedDice(int fixedValue) {
      super(1);
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

  @SuppressWarnings("unchecked")
  @Test
  void testStartWithTwoPlayers() throws Exception {
    // Simulate user input:
    //   1) number of players: "2"
    //   2) name for player1:  "Bobby"
    //   3) name for player2:  "Bobby"
    String simulatedInput = "2\nBobby\nBobby\n";

    // Preserve original System.in so it can be restored afterward.
    InputStream originalIn = System.in;

    // Use try-with-resources for the ByteArrayInputStream to avoid Sonar warnings.
    try (ByteArrayInputStream testIn = new ByteArrayInputStream(
        simulatedInput.getBytes(StandardCharsets.UTF_8))) {
      System.setIn(testIn);

      // Create the BoardGame
      BoardGame game = new BoardGame();

      // Reflect to replace the dice in TurnManager with a fixed one (roll=89).
      Field turnManagerField = BoardGame.class.getDeclaredField("turnManager");
      turnManagerField.setAccessible(true);
      TurnManager turnManager = (TurnManager) turnManagerField.get(game);

      Field diceField = TurnManager.class.getDeclaredField("dice");
      diceField.setAccessible(true);
      diceField.set(turnManager, new FixedDice(89));

      // Run the game, which will:
      // - read "2" as number of players
      // - read "Bobby" twice as player names
      // - instantly roll 89 so the first player wins
      game.start();

      // Verify two players named "Bobby" were created.
      Field playersField = BoardGame.class.getDeclaredField("players");
      playersField.setAccessible(true);
      List<Player> players = (List<Player>) playersField.get(game);

      assertEquals(2, players.size(), "There should be exactly 2 players");
      for (Player p : players) {
        assertEquals("Bobby", p.getNameOfPiece(), "Each player's name should be Bobby");
      }

    } finally {
      // Always restore the original System.in
      System.setIn(originalIn);
    }
  }
}
