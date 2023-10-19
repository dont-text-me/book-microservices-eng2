package uk.ac.york.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record BookDTO(String title, Integer year) {}
