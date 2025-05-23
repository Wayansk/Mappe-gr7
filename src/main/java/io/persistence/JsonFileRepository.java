package io.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.DataAccessException;
import io.json.CustomGson;
import io.util.FileUtils;
import io.util.PathProvider;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * JSON-backed implementation of {@link FileRepository<T>}
 * Uses Gson (via {@link CustomGson}) to serialize/deserialize
 *
 * @param <T> the element type
 */
public class JsonFileRepository<T> implements FileRepository<T> {

  private final Path filePath;
  private final Gson gson;
  private final Type collectionType;

  /**
   * @param clazz the class of the elements T
   * @param pathProvider resolves the JSON file path
   */
  public JsonFileRepository(Class<T> clazz, PathProvider pathProvider) {
    this.filePath = pathProvider.forJson(clazz);
    this.gson = CustomGson.getInstance();
    this.collectionType = TypeToken.getParameterized(Set.class, clazz).getType();
    ensureFileExists();
  }

  @Override
  public Stream<T> loadAll() {
    try {
      if (Files.size(filePath) == 0) {
        return Stream.empty();
      }
      try (Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
        Set<T> set = gson.fromJson(reader, collectionType);
        return set.stream();
      }
    } catch (IOException e) {
      throw new DataAccessException("Failed to load JSON file: " + filePath, e);
    }
  }

  @Override
  public void saveAll(Collection<T> items) {
    try {
      FileUtils.ensureExists(filePath);
      Set<T> toWrite = new LinkedHashSet<>(items);
      String json = gson.toJson(toWrite, collectionType);
      Files.writeString(filePath, json, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new DataAccessException("Failed to write JSON file: " + filePath, e);
    }
  }

  private void ensureFileExists() {
    try {
      FileUtils.ensureExists(filePath);
    } catch (IOException e) {
      throw new DataAccessException("Failed to initialize JSON file: " + filePath, e);
    }
  }
}
