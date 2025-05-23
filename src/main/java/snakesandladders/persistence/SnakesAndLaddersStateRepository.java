package snakesandladders.persistence;

import io.persistence.JsonFileRepository;
import io.util.PathProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class SnakesAndLaddersStateRepository {
  private final JsonFileRepository<SnakesAndLaddersState> repo;

  public SnakesAndLaddersStateRepository() {
    Path base = Paths.get(System.getProperty("user.home"), ".brettspill", "snakesandladders");
    this.repo = new JsonFileRepository<>(SnakesAndLaddersState.class, new PathProvider(base));
  }

  public List<SnakesAndLaddersState> loadAll() {
    return repo.loadAll().toList();
  }

  public void save(List<SnakesAndLaddersState> states) {
    repo.saveAll(states);
  }
}
