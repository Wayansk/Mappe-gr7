package board;

public class Board {
  private final int size;
  private final int[] board;

  public Board(int size) {
    this.size = size;
    board = new int[size + 1];
    initializeBoard();
    setupSpecialSquares();
  }

  private void initializeBoard() {
    for (int i = 1; i <= size; i++) {
      board[i] = i;
    }
  }

  private void setupSpecialSquares() {
    // TODO: figure out where the special squares should be
    // ladders: square -> destination (higher number)
    board[3] = 12;
    board[7] = 19;

    // snakes: same thing (lower number)
    board[15] = 1;
    board[24] = 5;
  }

  public int getDestination(int square) {
    if (square < 1 || square > size) {
      throw new IllegalArgumentException("Invalid square number: " + square);
    }
    return board[square];
  }

  public int movePlayer(int currentSquare, int diceRoll) {
    int nextSquare = currentSquare + diceRoll;
    if (nextSquare > size) {
      return currentSquare; // stay on the same square if you roll past the size of the board
    }
    return getDestination(nextSquare);
  }
}
