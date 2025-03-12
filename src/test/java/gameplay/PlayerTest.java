package gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp(){
        player = new Player( "Rikard", Player.PieceColor.ORANGE);
    }

    @Test
    void test_NameOfPiece(){
        assertEquals("Rikard", player.getNameOfPiece(), "Getter for the name should return the correct name");
    }

    @Test
    void test_ColorOfPiece(){
        assertEquals(Player.PieceColor.ORANGE, player.getColorOfPiece(), "Getter for the color of the piece should return the correct color");
    }


}
