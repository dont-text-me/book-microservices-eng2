package uk.ac.york.eng2.books.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Serdeable
public class Book {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Integer year;

  @JsonIgnore @ManyToMany private Set<User> readers;

  public Long getId() {
    return id;
  }

  public Set<User> getReaders() {
    return readers;
  }

  public void setReaders(Set<User> readers) {
    this.readers = readers;
  }

  public void addReader(User reader) {
    this.readers.add(reader);
  }

  public void removeReader(User reader) {
    this.readers.remove(reader);
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return "Book{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", year=" + year +
            '}';
  }
}
