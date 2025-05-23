package board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

import snakesandladders.board.Board;
import snakesandladders.board.Tile;
import snakesandladders.gameplay.Player;

class BoardTest {

  @Test
  void testBoardHas90Tiles() throws Exception {
    Board board = new Board();
    Field field = Board.class.getDeclaredField("tiles");
    field.setAccessible(true);
    Tile[] tiles = (Tile[]) field.get(board);
    assertEquals(90, tiles.length);
  }

  @Test
  void testTileIds() throws Exception {
    Board board = new Board();
    Field field = Board.class.getDeclaredField("tiles");
    field.setAccessible(true);
    Tile[] tiles = (Tile[]) field.get(board);
    for (int i = 0; i < tiles.length; i++) {
      assertEquals(i + 1, tiles[i].getTileId());
    }
  }

  @Test
  void testLadderAndSnakeActions() throws Exception {
    Board board = new Board();
    Field field = Board.class.getDeclaredField("tiles");
    field.setAccessible(true);
    Tile[] tiles = (Tile[]) field.get(board);

    // Ladder: tile 4 (index 3) should send the player to tile 14 (index 13)
    Tile tile4 = tiles[3];
    assertNotNull(tile4.getLandAction());
    Player player = new Player("TestPlayer", tile4);
    tile4.landPlayer(player);
    assertEquals(14, player.getCurrentTile().getTileId());

    // Snake: tile 81 (index 80) should send the player to tile 64 (index 63)
    Tile tile81 = tiles[80];
    assertNotNull(tile81.getLandAction());
    player.setCurrentTile(tile81);
    tile81.landPlayer(player);
    assertEquals(64, player.getCurrentTile().getTileId());
  }

  @Test
  void testTileWithoutAction() throws Exception {
    Board board = new Board();
    Field field = Board.class.getDeclaredField("tiles");
    field.setAccessible(true);
    Tile[] tiles = (Tile[]) field.get(board);
    // For example, tile 2 (index 1) should have no land action.
    assertNull(tiles[1].getLandAction());
  }
}
