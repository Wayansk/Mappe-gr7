package snakesandladders.persistence;

import java.util.Map;

public class SnakesAndLaddersState {
  public String boardJsonPath;
  public Map<String, Integer> playerPositions;
  public String currentPlayerName;

  public SnakesAndLaddersState() {}
}
