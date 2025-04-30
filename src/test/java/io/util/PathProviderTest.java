package io.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathProviderTest {

  @Test
  void testJsonPathGeneration(@TempDir Path tempDir) {
    PathProvider pathProvider = new PathProvider(tempDir);
    Path result = pathProvider.forJson(String.class);
    assertEquals(tempDir.resolve("String.json"), result, "JSON path should match String.json");
  }

  @Test
  void testCsvPathGeneration(@TempDir Path tempDir) {
    PathProvider pathProvider = new PathProvider(tempDir);
    Path result = pathProvider.forCsv("players");
    assertEquals(tempDir.resolve("players.csv"), result, " CSV path should match players.csv");
  }
}
