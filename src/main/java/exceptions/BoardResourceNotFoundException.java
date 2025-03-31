package exceptions;

public class BoardResourceNotFoundException extends RuntimeException {
  public BoardResourceNotFoundException(String message) {
    super(message);
  }
}
