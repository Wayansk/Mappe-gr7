package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import gameplay.Player;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Test;

class BoardGameTest {

  // Helper method to retrieve a private field value via reflection.
  @SuppressWarnings("unchecked")
  private <T> T getPrivateField(Object instance, String fieldName) throws Exception {
    Field field = instance.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    return (T) field.get(instance);
  }

  @Test
  void testBoardGameConstructor() {
    BoardGame game = new BoardGame();
    try {
      List<Player> players = getPrivateField(game, "players");
      assertNotNull(players, "players list should not be null");
      assertTrue(players.isEmpty(), "players list should be empty initially");

      Object turnManager = getPrivateField(game, "turnManager");
      assertNotNull(turnManager, "turnManager should be initialized");

      Object board = getPrivateField(game, "board");
      assertNotNull(board, "board should be initialized");
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @Test
  void testAddPlayer() {
    BoardGame game = new BoardGame();
    game.addPlayer("Bobby");
    try {
      List<Player> players = getPrivateField(game, "players");
      assertEquals(1, players.size(), "Should be exactly one player");
      assertEquals("Bobby", players.getFirst().getNameOfPiece(), "Player's name should be Bobby");
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @Test
  void testAddMultiplePlayers() {
    BoardGame game = new BoardGame();
    game.addPlayer("Bobby");
    game.addPlayer("Bobby");
    try {
      List<Player> players = getPrivateField(game, "players");
      assertEquals(2, players.size(), "Should be two players");
      for (Player p : players) {
        assertEquals("Bobby", p.getNameOfPiece(), "Each player's name should be Bobby");
      }
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @Test
  void testAddPlayerEmptyName() {
    BoardGame game = new BoardGame();
    game.addPlayer("");
    try {
      List<Player> players = getPrivateField(game, "players");
      assertEquals(1, players.size(), "Should be one player");
      assertEquals("", players.getFirst().getNameOfPiece(), "Name should be an empty string");
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @Test
  void testAddPlayerNullName() {
    BoardGame game = new BoardGame();
    game.addPlayer(null);
    try {
      List<Player> players = getPrivateField(game, "players");
      assertEquals(1, players.size(), "Should be one player");
      assertNull(players.getFirst().getNameOfPiece(), "Name should be null");
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }
}
