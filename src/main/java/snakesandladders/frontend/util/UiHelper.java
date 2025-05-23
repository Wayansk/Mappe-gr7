package snakesandladders.frontend.util;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Utility class for showing dialogs and alerts in the GUI.
 */
public final class UiHelper {

  // Private constructor to avoid instantiation
  private UiHelper() {
    throw new UnsupportedOperationException("UiHelper is a utility class and should not be "
        + "instantiated");
  }

  /**
   * Shows an informational message to the user.
   *
   * @param message the message to show
   */
  public static void showInfo(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Info");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Shows an error message to the user.
   *
   * @param message the error message
   */
  public static void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("An error occurred");
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Prompts the user for a text input.
   *
   * @param title       the window title
   * @param prompt      the header text
   * @param defaultText default value in the input field
   * @return optional containing the user's input if present
   */
  public static Optional<String> promptText(String title, String prompt, String defaultText) {
    TextInputDialog dialog = new TextInputDialog(defaultText);
    dialog.setTitle(title);
    dialog.setHeaderText(prompt);
    dialog.setContentText(null);
    return dialog.showAndWait();
  }
}
