package io.persistence;

import io.util.FileUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import board.Board;
import gameplay.Player;

/**
 * CSV-backed {@link FileRepository} for {@link Player} objects
 * Each line in the CSV is "name,pieceIdentifier"
 */
public class CsvPlayerRepository implements FileRepository<Player> {

  private final Path csvPath;
  private final Board board;

  /**
   * @param csvPath where to read/write players.csv
   * @param board used to map pieceIdentifier -> Tile
   */
  public CsvPlayerRepository(Path csvPath, Board board) {
    this.csvPath = csvPath;
    this.board = board;
  }

  @Override
  public Stream<Player> loadAll() {
    try {
      FileUtils.ensureExists(csvPath);
      List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
      return lines.stream()
          .map(line -> line.split(",", 2))
          .map(parts -> new Player(parts[0], board.getTile(0)));
    } catch (IOException e) {
      throw new DataAccessException("Cannot read CSV file: " + csvPath, e);
    }
  }

  @Override
  public void saveAll(Collection<Player> players) {
    try {
      FileUtils.ensureExists(csvPath);
      List<String> lines = players.stream().map(Player::getNameOfPiece).toList();
      Files.write(csvPath, lines, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new DataAccessException("Cannot write CSV file: " + csvPath, e);
    }
  }
}
