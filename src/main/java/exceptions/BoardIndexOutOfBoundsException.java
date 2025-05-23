package exceptions;

public class BoardIndexOutOfBoundsException extends RuntimeException {
  public BoardIndexOutOfBoundsException(int index, int size) {
    super("Tile index out of bounds: " + index + " (valid range: 0-" + (size - 1) + ")");
  }
}
