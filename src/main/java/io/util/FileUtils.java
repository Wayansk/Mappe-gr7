package io.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * File system utilities
 * Ensures that files and directories exist before reading/writing
 */
public final class FileUtils {
  private FileUtils() {} /* no instances */

  /**
   * Ensure that the given file Path exists
   * Creates parent directories if needed and file if missing
   *
   * @param path the file path to create
   * @throws IOException if the directories/files cannot be created
   */
  public static void ensureExists(Path path) throws IOException {
    Path parent = path.getParent();
    if (parent != null && Files.notExists(parent)) {
      Files.createDirectories(parent);
    }
    if (Files.notExists(path)) {
      Files.createFile(path);
    }
  }
}
