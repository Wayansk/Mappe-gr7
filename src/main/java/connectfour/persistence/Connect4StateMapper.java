package connectfour.persistence;

import connectfour.engine.Connect4Game;
import connectfour.model.Piece;
import connectfour.model.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Connect4StateMapper {
  private Connect4StateMapper() {}

  public static Connect4State toState(Connect4Game connect4Game) {
    Connect4State connect4State = new Connect4State();

    connect4State.playerNames = connect4Game.getPlayers().stream()
        .map(Player::getName)
        .collect(Collectors.toList());
    connect4State.currentPlayerIndex = connect4Game.getCurrentPlayerIndex();

    connect4State.grid = IntStream.range(0, connect4Game.getBoard().getRows())
        .mapToObj(row -> IntStream.range(0, connect4Game.getBoard().getCols())
            .mapToObj(col -> connect4Game.getBoard().getCell(row, col).getPiece())
            .collect(Collectors.toList()))
        .collect(Collectors.toList());

    return connect4State;
  }

  public static void fromState(Connect4Game connect4Game, Connect4State connect4State) {
    connect4Game.reset();

    List<String> names = connect4State.playerNames;
    for (int i = 0; i < names.size(); i++) {
      Piece piece = (i == 0 ? Piece.RED : Piece.YELLOW);
    }

    for (int r = 0; r < connect4State.grid.size(); r++) {
      List<Piece> row = connect4State.grid.get(r);
      for (int c = 0; c < row.size(); c++) {
        Piece piece = row.get(c);
        if (piece != null) {
          connect4Game.getBoard().getCell(r, c).setPiece(piece);
        }
      }
    }
    connect4Game.setCurrentPlayerIndex(connect4State.currentPlayerIndex);
  }
}
