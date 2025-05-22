package frontend.util;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;

public class BoardSetupHelper {

  private BoardSetupHelper() {
    throw new AssertionError("No instances of BoardSetupHelper");
  }

  private static final List<String> OPTIONS = List.of(
      "Board_picture1.json",
      "Board_picture2.json",
      "Board_picture3.json"
  );

  public static String askBoardJsonResource() {
    ChoiceDialog<String> boardChoiceDialog = new ChoiceDialog<>(OPTIONS.getFirst(), OPTIONS);
    boardChoiceDialog.setTitle("Select Board");
    boardChoiceDialog.setHeaderText("Choose which board to play");
    boardChoiceDialog.setContentText("Board file:");

    Optional<String> choiceOfBoard = boardChoiceDialog.showAndWait();
    if (choiceOfBoard.isEmpty()) {
      Platform.exit();
      return "/json_boards/" + OPTIONS.getFirst();
    }
    return "/json_boards/" + choiceOfBoard.get();
  }
}
