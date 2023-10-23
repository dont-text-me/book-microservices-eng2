package uk.ac.york.eng2.books.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record User (Long id, String name) {
  @Override
  public String toString() {
    return "User{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
