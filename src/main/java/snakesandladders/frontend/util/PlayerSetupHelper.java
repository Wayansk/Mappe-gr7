package snakesandladders.frontend.util;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerSetupHelper {

  private PlayerSetupHelper() {}

  public static Optional<Integer> askPlayerCount() {
    ChoiceDialog<Integer> playerCountDialog = new ChoiceDialog<>(2, 2, 3, 4, 5);
    playerCountDialog.setTitle("Player Count");
    playerCountDialog.setHeaderText("How many players? (2-5)");
    Optional<Integer> choice = playerCountDialog.showAndWait();
    return choice.filter(n -> n >= 2 && n <= 5);
  }

  public static List<String> collectPlayerNames(int count) {
    List<String> playerNames = new ArrayList<>();
    for (int i = 1; i <= count; i++) {
      TextInputDialog inputPlayerNamesDialog = new TextInputDialog("Player " + i);
      inputPlayerNamesDialog.setTitle("Player Name");
      inputPlayerNamesDialog.setHeaderText("Enter name for player " + i);
      Optional<String> playerNameResult = inputPlayerNamesDialog.showAndWait();
      playerNames.add(playerNameResult.filter(n -> !n.isBlank()).orElse("Player " + i));
    }
    return playerNames;
  }
}
