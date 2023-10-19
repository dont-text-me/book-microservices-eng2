package uk.ac.york.eng2.books.domain;

import io.micronaut.serde.annotation.Serdeable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Serdeable
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

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
}
