package engine;

import gameplay.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

  private BoardGame boardGame;

  @BeforeEach
  void setup() {
    boardGame = new BoardGame();
  }

  @Test
  void addPlayer_shouldAddPlayerSuccessfully() {
    boardGame.addPlayer("Alice");
    List<Player> players = boardGame.getPlayers();

    assertEquals(1, players.size());
    assertEquals("Alice", players.getFirst().getNameOfPiece());
  }

  @Test
  void addPlayer_shouldSetStartingTileToFirstTile() {
    boardGame.addPlayer("Bob");
    Player bob = boardGame.getPlayers().getFirst();

    assertEquals(1, bob.getCurrentTile().getTileId()); // tile index 0 = ID 1
  }

  @Test
  void addPlayer_shouldThrowExceptionOnBlankName() {
    assertThrows(IllegalArgumentException.class, () -> boardGame.addPlayer(""));
  }

  @Test
  void addPlayer_shouldThrowExceptionOnNullName() {
    assertThrows(IllegalArgumentException.class, () -> boardGame.addPlayer(null));
  }

  @Test
  void getCurrentPlayer_shouldReturnFirstPlayerAfterAdd() {
    boardGame.addPlayer("Charlie");
    Player current = boardGame.getCurrentPlayer();

    assertEquals("Charlie", current.getNameOfPiece());
  }

  @Test
  void playNextTurn_shouldAdvanceGame_whenPlayersExist() {
    boardGame.addPlayer("Alice");
    boardGame.addPlayer("Bob");

    Optional<TurnResult> result = boardGame.playNextTurn();
    assertTrue(result.isPresent());
    assertEquals("Alice", result.get().player().getNameOfPiece()); // first turn = Alice
  }

  @Test
  void playNextTurn_shouldReturnEmpty_whenNoPlayers() {
    Optional<TurnResult> result = boardGame.playNextTurn();
    assertTrue(result.isEmpty());
  }

  @Test
  void isGameOver_shouldBeFalseInitially() {
    boardGame.addPlayer("Alice");
    assertFalse(boardGame.isGameOver());
  }

  @Test
  void getBoard_shouldReturnBoardInstance() {
    assertNotNull(boardGame.getBoard());
    assertEquals(90, boardGame.getBoard().getTileCount());
  }
}
