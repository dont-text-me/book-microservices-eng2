package uk.ac.york.eng2.books.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Book (Long id, String title, Integer year){
  @Override
  public String toString() {
    return "Book{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year + '}';
  }
}
