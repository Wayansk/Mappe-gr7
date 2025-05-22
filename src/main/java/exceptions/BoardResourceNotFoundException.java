package exceptions;

public class BoardResourceNotFoundException extends RuntimeException {
  public BoardResourceNotFoundException(String message) {
    super(message);
  }

  public BoardResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
