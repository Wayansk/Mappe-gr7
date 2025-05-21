package io.persistence;

import board.Board;
import gameplay.Player;
import io.util.PathProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvPlayerRepositoryTest {

  private static final Path Test_DIRECTORY = Path.of("src/test/resource/testdata");
  private CsvPlayerRepository repository;
  private Path file;

  @BeforeEach
  void setUp() {
    Board board = new Board();
    PathProvider paths = new PathProvider(Test_DIRECTORY);
    file = paths.forCsv("test_players");
    repository = new CsvPlayerRepository(file, board);
  }

  @AfterEach
  void cleanUp() {
    boolean deleted = file.toFile().delete();
    assertTrue(deleted, "Temp file should be deleted");
  }

  @Test
  void testWriteAndLoadPlayers() {
    Player player1 = new Player("John Pork", new Board().getTile(0));
    Player player2 = new Player("Jane Doe", new Board().getTile(1));
    repository.saveAll(List.of(player1, player2));

    System.out.println("--- repo contains ---");
    repository.printAll();

    List<String> names = repository.loadAll().map(Player::getNameOfPiece).collect(Collectors.toList());
    assertEquals(List.of("John Pork", "Jane Doe"), names);
  }

  @Test
  void testEmptyFileReturnsEmpty() {
    assertTrue(repository.loadAll().toList().isEmpty());
  }
}
