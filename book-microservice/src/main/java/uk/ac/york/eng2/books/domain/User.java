package uk.ac.york.eng2.books.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Serdeable
public class User {

  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "readers")
  private Set<Book> readBooks;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
