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

  @SuppressWarnings("unchecked")
  @Test
  void testBoardGameConstructor() {
    BoardGame game = new BoardGame();

    // Basic checks via reflection
    try {
      Field playersField = BoardGame.class.getDeclaredField("players");
      playersField.setAccessible(true);
      List<Player> players = (List<Player>) playersField.get(game);
      assertNotNull(players, "players list should not be null");
      assertTrue(players.isEmpty(), "players list should be empty initially");

      Field turnManagerField = BoardGame.class.getDeclaredField("turnManager");
      turnManagerField.setAccessible(true);
      Object turnManager = turnManagerField.get(game);
      assertNotNull(turnManager, "turnManager should be initialized");

      Field boardField = BoardGame.class.getDeclaredField("board");
      boardField.setAccessible(true);
      Object board = boardField.get(game);
      assertNotNull(board, "board should be initialized");
    } catch (NoSuchFieldException | IllegalAccessException e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  void testAddPlayer() {
    BoardGame game = new BoardGame();
    game.addPlayer("Bobby");

    try {
      Field playersField = BoardGame.class.getDeclaredField("players");
      playersField.setAccessible(true);
      List<Player> players = (List<Player>) playersField.get(game);
      assertEquals(1, players.size(), "Should be exactly one player");
      assertEquals("Bobby", players.getFirst().getNameOfPiece(), "Player's name should be Bobby");
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  void testAddMultiplePlayers() {
    BoardGame game = new BoardGame();
    game.addPlayer("Bobby");
    game.addPlayer("Bobby");

    try {
      Field playersField = BoardGame.class.getDeclaredField("players");
      playersField.setAccessible(true);
      List<Player> players = (List<Player>) playersField.get(game);
      assertEquals(2, players.size(), "Should be two players");
      for (Player p : players) {
        assertEquals("Bobby", p.getNameOfPiece(), "Each player's name should be Bobby");
      }
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  void testAddPlayerEmptyName() {
    BoardGame game = new BoardGame();
    game.addPlayer(""); // Add a player with empty name

    try {
      Field playersField = BoardGame.class.getDeclaredField("players");
      playersField.setAccessible(true);
      List<Player> players = (List<Player>) playersField.get(game);
      assertEquals(1, players.size(), "Should be one player");
      assertEquals("", players.getFirst().getNameOfPiece(), "Name should be empty string");
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  void testAddPlayerNullName() {
    BoardGame game = new BoardGame();
    game.addPlayer(null); // Add a player with null name

    try {
      Field playersField = BoardGame.class.getDeclaredField("players");
      playersField.setAccessible(true);
      List<Player> players = (List<Player>) playersField.get(game);
      assertEquals(1, players.size(), "Should be one player");
      assertNull(players.getFirst().getNameOfPiece(), "Name should be null");
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
    }
  }
}
