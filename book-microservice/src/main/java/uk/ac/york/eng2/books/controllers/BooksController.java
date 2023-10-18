package uk.ac.york.eng2.books.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import java.net.URI;
import uk.ac.york.eng2.books.domain.Book;
import uk.ac.york.eng2.books.dto.BookDTO;
import uk.ac.york.eng2.books.repositories.BooksRepository;

@Controller("/books")
public class BooksController {
  @Inject private BooksRepository repo;

  @Get("/")
  public Iterable<Book> list() {
    return repo.findAll();
  }

  @Post("/")
  public HttpResponse<Void> add(@Body BookDTO bookDetails) {
    Book newBook = new Book();
    newBook.setYear(bookDetails.getYear());
    newBook.setTitle(bookDetails.getTitle());
    repo.save(newBook);

    return HttpResponse.created(URI.create("/books/" + newBook.getId()));
  }
}
