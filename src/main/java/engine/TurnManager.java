package engine;

import gameplay.Player;

import java.util.List;

public class TurnManager {
  private final List<Player> players;
  private int currentPlayerIndex;

  public TurnManager(List<Player> players) {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Player list cannot be empty");
    }
    this.players = players;
    this.currentPlayerIndex = 0;
  }

  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  /**
   * if the dice roll is not 6 -> next players turn
   * if it is 6, current player goes again
   *
   * @param diceRoll dice roll
   */
  public void nextTurn(int diceRoll) {
    if (diceRoll != 6) {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
  }
}
