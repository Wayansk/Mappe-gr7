package io.persistence;

import io.util.PathProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DummyData {
  public String name;
  public int number;

  public DummyData(String name, int number) {
    this.name = name;
    this.number = number;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    DummyData other = (DummyData) object;
    return number == other.number && name.equals(other.name);
  }

  @Override public int hashCode() {
    return name.hashCode() + 31 * number;
  }
}

class JsonFileRepositoryTest {

  private static final Path TEST_DIRECTORY = Path.of("src/test/resources/testdata");
  private JsonFileRepository<DummyData> repository;

  @BeforeEach
  void setUp() {
    PathProvider pathProvider = new PathProvider(TEST_DIRECTORY);
    repository = new JsonFileRepository<>(DummyData.class, pathProvider);
  }

  @AfterEach
  void cleanUp() {
    Path path = TEST_DIRECTORY.resolve("DummyData.json");

    boolean deleted = path.toFile().delete();
    assertTrue(deleted, "Temp path should be deleted");
  }

  @Test
  void testWriteAndRead() {
    DummyData dummyData1 = new DummyData("John Pork", 1);
    DummyData dummyData2 = new DummyData("Jane Doe", 2);
    repository.saveAll(List.of(dummyData1, dummyData2));

    System.out.println("--- repo contains ---");
    repository.printAll();

    Set<DummyData> result = repository.loadAll().collect(Collectors.toSet());
    assertEquals(Set.of(dummyData1, dummyData2), result);
  }

  @Test
  void testEmptyFileReturnsEmptySet() {
    Set<DummyData> result = repository.loadAll().collect(Collectors.toSet());
    assertTrue(result.isEmpty());
  }
}
