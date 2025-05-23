package connectfour.persistence;

import connectfour.model.Piece;

import java.util.List;

public class Connect4State {
  public List<List<Piece>> grid;
  public List<String> playerNames;
  public int currentPlayerIndex;

  public Connect4State() {}
}
