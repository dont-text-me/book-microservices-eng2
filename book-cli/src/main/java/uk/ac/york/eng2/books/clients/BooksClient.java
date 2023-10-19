package uk.ac.york.eng2.books.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.books.domain.Book;
import uk.ac.york.eng2.books.dto.BookDTO;

@Client("http://localhost:8080/books")
public interface BooksClient {
  @Get("/")
  public Iterable<Book> list();

  @Post("/")
  public HttpResponse<Void> add(@Body BookDTO bookDetails);

  @Get("/{id}")
  public Book getBook(long id);

  @Put("/{id}")
  public HttpResponse<Void> updateBook(long id, @Body BookDTO bookDetails);

  @Delete("/{id}")
  public HttpResponse<Void> deleteBook(long id);
}
