package connectfour.persistence;

import io.persistence.JsonFileRepository;
import io.util.PathProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Connect4StateRepository {
  private final JsonFileRepository<Connect4State> repo;

  public Connect4StateRepository() {
    Path base = Paths.get(System.getProperty("user.home"), ".brettspill", "connect4");
    this.repo = new JsonFileRepository<>(Connect4State.class, new PathProvider(base));
  }

  public List<Connect4State> loadAll() {
    return repo.loadAll().toList();
  }

  public void save(Connect4State connect4State) {
    repo.saveAll(List.of(connect4State));
  }
}
