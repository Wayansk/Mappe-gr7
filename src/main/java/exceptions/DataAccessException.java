package exceptions;

/**
 * Runtime exception for failures during data access operations
 */
public class DataAccessException extends RuntimeException {

  /**
   * Shows what went wrong
   * Wraps a lower level exception with a domain-specific message
   *
   * @param message readable details of what went wrong
   * @param cause The underlying exception
   */
  public DataAccessException(String message, Throwable cause) {
    super(message, cause);
  }
}
