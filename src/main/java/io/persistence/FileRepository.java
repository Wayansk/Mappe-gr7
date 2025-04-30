package io.persistence;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Repo interface for loading and saving collections of object type T
 *
 * @param <T> the type of objects managed by the repo
 */
public interface FileRepository<T> {

  Stream<T> loadAll();

  void saveAll(Collection<T> items);

  default void printAll () {
    loadAll().forEach(System.out::println);
  }
}
