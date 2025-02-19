package board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
  void testNormalMove() {
    Board board = new Board(30);

    int currentSquare = 1;
    int diceRoll = 1;
    int expected = 2;
    int result = board.movePlayer(currentSquare, diceRoll);

    assertEquals(expected, result, "piece is supposed to move from 1 to 2");
  }

  @Test
  void testLadderMove() {
    Board board = new Board(30);

    int currentSquare = 1;
    int diceRoll = 2;
    int expected = 12; // Ladder from 3 to 12
    int result = board.movePlayer(currentSquare, diceRoll);

    assertEquals(expected, result, "Piece should move from square 3 to 12 due to ladder");
  }

  @Test
  void testSnakeMove() {
    Board board = new Board(30);

    int currentSquare = 10;
    int diceRoll = 5;
    int expected = 1; // Snake from 15 to 1.
    int result = board.movePlayer(currentSquare, diceRoll);

    assertEquals(expected, result, "Piece should slide from 15 to 1 due to the snake");
  }

  @Test
  void testOvershootMove() {
    Board board = new Board(30);

    int currentSquare = 28;
    int diceRoll = 4;
    int expected = 28; // No move if overshooting the board.
    int result = board.movePlayer(currentSquare, diceRoll);

    assertEquals(expected, result, "Piece should remain on the same square if the move overshoots the board");
  }
}
