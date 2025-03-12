package board;

public class Board {
  private final int size;
  private final int[] gameBoard;

  public Board(int size) {
    this.size = size;
    gameBoard = new int[size + 1];
    initializeBoard();
    setupSpecialSquares();
  }

  private void initializeBoard() {
    for (int i = 1; i <= size; i++) {
      gameBoard[i] = i;
    }
  }

  private void setupSpecialSquares() {
    // TODO: figure out where the special squares should be
    // ladders: square -> destination (higher number)
    gameBoard[3] = 12;
    gameBoard[7] = 19;

    // snakes: same thing (lower number)
    gameBoard[15] = 1;
    gameBoard[24] = 5;
  }

  public int getDestination(int square) {
    if (square < 1 || square > size) {
      throw new IllegalArgumentException("Invalid square number: " + square);
    }
    return gameBoard[square];
  }

  public int movePlayer(int currentSquare, int diceRoll) {
    int nextSquare = currentSquare + diceRoll;
    if (nextSquare > size) {
      return currentSquare; // stay on the same square if you roll past the size of the board
    }
    return getDestination(nextSquare);
  }
}
