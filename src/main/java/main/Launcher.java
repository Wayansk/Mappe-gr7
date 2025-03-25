package main;

/**
 * Launcher class that starts the game application. This class delegates the startup process by
 * invoking the {@code main} method of the {@code GameMain} class.
 * </p>
 */
public class Launcher {

  /**
   * Entry point of the application. This method forwards the command-line arguments to
   * {@code GameMain.main}.
   * </p>
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    GameMain.main(args);
  }
}
