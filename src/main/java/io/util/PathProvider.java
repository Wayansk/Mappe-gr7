package io.util;

import java.nio.file.Path;

/**
 * Resolves file-system paths for JSON and CSV files under a base directory
 */
public class PathProvider {
  private final Path baseDirectory;

  /**
   * @param baseDirectory the root folder under which all JSON/CSV will be
   */
  public PathProvider(Path baseDirectory) {
    this.baseDirectory = baseDirectory;
  }

  /**
   * @param clazz the data transfer (DTO) class
   * @return baseDirectory/ClassName.json
   */
  public Path forJson(Class<?> clazz) {
    return baseDirectory.resolve(clazz.getSimpleName() + ".json");
  }

  /**
   * @param fileName logical name
   * @return baseDirectory/fileName.csv
   */
  public Path forCsv(String fileName) {
    return baseDirectory.resolve(fileName + ".csv");
  }
}
