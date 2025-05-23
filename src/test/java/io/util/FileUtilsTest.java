package io.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

  private Path filePath;

  @Test
  void testEnsureExistsCreatesFileAndParent(@TempDir Path tempDir) throws IOException {
    filePath = tempDir.resolve("nested/dir/testfile.txt");

    FileUtils.ensureExists(filePath);

    assertTrue(Files.exists(filePath.getParent()), "Parent directory should exist");
    assertTrue(Files.exists(filePath), "File should be created");
  }


  @Test
  void testEnsureExistsWhenAlreadyExists(@TempDir Path tempDir) throws IOException {
    filePath = tempDir.resolve("nested/dir/testfile.txt");

    Files.createDirectories(filePath.getParent());
    Files.createFile(filePath);

    assertDoesNotThrow(() -> FileUtils.ensureExists(filePath));
    assertTrue(Files.exists(filePath));
  }
}
