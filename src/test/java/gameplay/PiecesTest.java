package gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PiecesTest {

    private Pieces pieces;

    @BeforeEach
    void setUp(){
        pieces = new Pieces( "Rikard", Pieces.PieceColor.ORANGE);
    }

    @Test
    void test_NameOfPiece(){
        assertEquals("Rikard", pieces.getNameOfPiece(), "Getter for the name should return the correct name");
    }

    @Test
    void test_ColorOfPiece(){
        assertEquals(Pieces.PieceColor.ORANGE, pieces.getColorOfPiece(), "Getter for the color of the piece should return the correct color");
    }


}
