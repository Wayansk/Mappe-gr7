package persistence;

import gameplay.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles the registering of players (name/color) and
 * delegating file I/O to PlayerPersistence
 */
public class PlayerManager {

  private PlayerManager() {
    throw new UnsupportedOperationException("Utility class; not to be instantiated");
  }

  /**
   * Loads player profiles from CSV via PlayerPersistence
   *
   * @param filePath path to the CSV file ("players.csv")
   * @return list of players/pieces objects loaded from the file
   */
  public static List<Player> loadPlayers(String filePath) {
    return PlayerPersistence.loadPlayers(filePath);
  }

  /**
   * Prompts the user to register new players via console, then saves it
   *
   * @param filePath path to the csv file
   * @return List of newly registered Players
   */
  public static List<Player> registerPlayers(String filePath) {
    List<Player> players = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter number of players: ");
    int numPlayers = scanner.nextInt();
    scanner.nextLine();

    for (int i = 0; i < numPlayers; i++) {
      System.out.println("Enter name for player " + (i + 1) + ": ");
      String name = scanner.nextLine();

      System.out.println("Choose a piece color for " + name + " from the following colors:");
      for (Player.PieceColor color : Player.PieceColor.values()) {
        System.out.println(color.name() + " ");
      }
      System.out.println();
      String colorStr = scanner.nextLine().toUpperCase();
      Player.PieceColor chosenColor = Player.PieceColor.valueOf(colorStr);

      players.add(new Player(name, chosenColor));
    }

    // saves the new players for future sessions
    PlayerPersistence.savePlayers(players, filePath);
    return players;
  }
}
