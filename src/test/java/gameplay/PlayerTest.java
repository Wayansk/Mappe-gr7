package gameplay;

import board.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp(){
        Tile testtile = new Tile(3);
        player = new Player( "Rikard", testtile);
        player.setCurrentTile(testtile);
    }

    @Test
    void test_NameOfPiece(){
        assertEquals("Rikard", player.getNameOfPiece(), "Getter for the name should return the correct name");
    }

    @Test
    void test_CorrectTile(){
        assertEquals(3, player.getCurrentTile().getTileId(), "Test for current tile should return correct tile: 3");
    }
}
