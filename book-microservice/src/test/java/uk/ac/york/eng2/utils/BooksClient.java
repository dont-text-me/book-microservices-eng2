package uk.ac.york.eng2.utils;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.books.domain.Book;
import uk.ac.york.eng2.books.domain.User;
import uk.ac.york.eng2.books.dto.BookDTO;

@Client("${books.url:`http://localhost:8080/books`}")
public interface BooksClient {
  @Get("/")
  Iterable<Book> list();

  @Post("/")
  HttpResponse<Void> add(@Body BookDTO bookDetails);

  @Get("/{id}")
  Book getBook(long id);

  @Put("/{id}")
  HttpResponse<Void> updateBook(long id, @Body BookDTO bookDetails);

  @Delete("/{id}")
  HttpResponse<Void> deleteBook(long id);

  @Get("/{id}/readers")
  Iterable<User> getReaders(long id);

  @Put("/{id}/readers")
  HttpResponse<Void> updateReaders(long id, @Body long readerId);

  @Delete("/{id}/readers")
  HttpResponse<Void> deleteReaders(long id, @Body long readerId);
}
