package engine;

import snakesandladders.board.Board;
import snakesandladders.engine.TurnManager;
import snakesandladders.engine.TurnResult;
import snakesandladders.gameplay.Dice;
import snakesandladders.gameplay.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

  private TurnManager turnManager;
  private Player player1;
  private Player player2;
  private Board board;

  @BeforeEach
  void setup() {
    board = new Board();
    List<Player> players = new ArrayList<>();
    player1 = new Player("Alice", board.getTile(0));
    player2 = new Player("Bob", board.getTile(0));
    players.add(player1);
    players.add(player2);

    turnManager = new TurnManager(players, new Dice(1), board);
  }

  @Test
  void playTurn_shouldReturnResult_whenGameIsRunning() {
    Optional<TurnResult> result = turnManager.playTurn();
    assertTrue(result.isPresent());
  }

  @Test
  void playTurn_shouldMarkGameOver_whenPlayerWins() {
    // Force player to win: move player close to end manually
    player1.setCurrentTile(board.getTile(board.getTileCount() - 2)); // second to last tile
    Optional<TurnResult> result = turnManager.playTurn();

    assertTrue(result.isPresent());
    assertTrue(result.get().hasWon());
    assertTrue(turnManager.isGameOver());
  }

  @Test
  void getCurrentPlayer_shouldReturnFirstPlayerInitially() {
    assertEquals(player1, turnManager.getCurrentPlayer());
  }

  @Test
  void getPlayers_shouldReturnAllPlayers() {
    List<Player> players = turnManager.getPlayers();
    assertEquals(2, players.size());
    assertTrue(players.contains(player1));
    assertTrue(players.contains(player2));
  }
}
