package connectfour.model;

import java.util.Arrays;

/**
 * Connect 4 board: 6x7 by default
 * Supports disc dropping and checking for win
 */
public class Board {
  public static final int DEFAULT_ROWS = 6;
  public static final int DEFAULT_COLS = 7;
  private static final int WIN_COUNT = 4;
  private static final int[][] DIRECTIONS = {
      {0,1}, // horizontal
      {1,0}, // vertical
      {1,1}, // diagonal down
      {-1,1} // diagonal up
  };

  private final int rows;
  private final int cols;
  private final Cell[][] grid;

  public Board() {
    this(DEFAULT_ROWS, DEFAULT_COLS);
  }

  public Board(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    grid = new Cell[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new Cell(row, col);
      }
    }
  }

  /**
   * Drop disc into column
   * @param column the column to drop disc into
   * @param piece the player piece
   * @return returns the row it lands on, or -1 if column full
   */
  public int dropDisc(int column, Piece piece) {
    for (int row = rows - 1; row >= 0; row--) {
      if (grid[row][column].isEmpty()) {
        grid[row][column].setPiece(piece);
        return row;
      }
    }
    return -1; // full
  }

  public Cell getCell(int row, int col) {
    return grid[row][col];
  }

  public boolean isFull() {
    return Arrays.stream(grid[0]).allMatch(cell -> !cell.isEmpty());
  }

  /**
   * Check all directions for four in a row
   * @return the winning Piece, or null if none
   */
  public Piece checkWinner() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Piece piece = grid[row][col].getPiece();
        if (piece == null) {
          continue;
        }
        if (hasWinningLineFrom(row, col, piece)) {
          return piece;
        }
      }
    }
    return null;
  }

  private boolean hasWinningLineFrom(int startRow, int startCol, Piece piece) {
    for (int[] direction : DIRECTIONS) {
      if (countInDirection(startRow, startCol, direction[0], direction[1], piece) >= WIN_COUNT) {
        return true;
      }
    }
    return false;
  }

  private int countInDirection(int row, int col, int deltaRow, int deltaCol, Piece piece) {
    int count = 1;
    int r = row + deltaRow;
    int c = col + deltaCol;

    while (isInBounds(r, c) && grid[r][c].getPiece() == piece) {
      count++;
      r += deltaRow;
      c += deltaCol;
    }
    return count;
  }

  private boolean isInBounds(int r, int c) {
    return r >= 0 && r < rows && c >= 0 && c < cols;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }
}
