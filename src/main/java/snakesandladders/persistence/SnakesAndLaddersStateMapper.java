package snakesandladders.persistence;

import snakesandladders.engine.BoardGame;
import snakesandladders.gameplay.Player;

import java.util.stream.Collectors;

public final class SnakesAndLaddersStateMapper {
  private SnakesAndLaddersStateMapper() {}

  public static SnakesAndLaddersState toState(BoardGame boardGame) {
    SnakesAndLaddersState snakesAndLaddersState = new SnakesAndLaddersState();
    snakesAndLaddersState.boardJsonPath = boardGame.getBoardDefinitionPath();
    snakesAndLaddersState.playerPositions = boardGame.getPlayers().stream()
        .collect(Collectors.toMap(Player::getNameOfPiece, p -> p.getCurrentTile().getTileId()
        ));
    snakesAndLaddersState.currentPlayerName = boardGame.getCurrentPlayer().getNameOfPiece();
    return snakesAndLaddersState;
  }

  public static void fromState(BoardGame boardGame, SnakesAndLaddersState snakesAndLaddersState) {
    boardGame.reset();

    snakesAndLaddersState.playerPositions.forEach(boardGame::movePlayerTo);
    boardGame.setCurrentPlayerIndex(snakesAndLaddersState.currentPlayerName);
  }
}
