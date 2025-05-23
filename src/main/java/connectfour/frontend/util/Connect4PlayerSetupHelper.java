package connectfour.frontend.util;

import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;

public final class Connect4PlayerSetupHelper {
  private Connect4PlayerSetupHelper() {
    throw new AssertionError("No instances of Connect4's PlayerSetupHelper");
  }

  public static String askPlayerName(String headerText, String defaultName) {
    TextInputDialog dialog = new TextInputDialog(defaultName);
    dialog.setTitle("Player setup");
    dialog.setHeaderText(headerText);
    dialog.setContentText("Enter name:");
    Optional<String> nameResult = dialog.showAndWait();
    return nameResult.map(String::trim).filter(s -> !s.isEmpty()).orElse(defaultName);
  }

  public static List<String> collectPlayerNames() {
    String redPLayer = askPlayerName("Player 1 (RED)", "Player 1");
    String yellowPlayer = askPlayerName("Player 2 (YELLOW)", "Player 2");
    return List.of(redPLayer, yellowPlayer);
  }
}
