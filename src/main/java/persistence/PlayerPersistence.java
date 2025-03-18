package persistence;

import gameplay.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading player information in a CSV file
 */
public class PlayerPersistence {

  private PlayerPersistence() {
    throw new UnsupportedOperationException("Utility class; not to be instantiated");
  }

  /**
   * Saves the list of Players to a CSV file
   *
   * @param players  the list of player pieces objects to save
   * @param filePath filePath to the csv file ("players.csv")
   */
  public static void savePlayers(List<Player> players, String filePath) {
    try (PrintWriter printWriter = new PrintWriter(new FileWriter(filePath))) {
      for (Player player : players) {
        printWriter.println(player.getNameOfPiece() + "," + player.getColorOfPiece().name());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads players from CSV file
   *
   * @param filePath path to csv file
   * @return A list of players objects (one for each participant)
   */
  public static List<Player> loadPlayers(String filePath) {
    List<Player> players = new ArrayList<>();
    File file = new File(filePath);

    if (file.exists()) {
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          // expected format: name,color
          String[] parts = line.split(",");
          if (parts.length == 2) {
            String name = parts[0].trim();
            String colorStr = parts[1].trim().toUpperCase();
            Player.PieceColor color = Player.PieceColor.valueOf(colorStr);
            players.add(new Player(name, color));
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return players;
  }
}
