package uk.ac.york.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserDTO(String name) {}
