package uk.ac.york.eng2.books.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.Objects;
import javax.transaction.Transactional;
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

  @Get("/{id}")
  public Book getBook(long id) {
    return repo.findById(id).orElse(null);
  }

  @Put("/{id}")
  @Transactional
  public HttpResponse<Void> updateBook(long id, @Body BookDTO bookDetails) {
    Book oldBook = repo.findById(id).orElse(null);
    if (oldBook == null) {
      return HttpResponse.notFound();
    }
    oldBook.setTitle(Objects.requireNonNullElse(bookDetails.getTitle(), oldBook.getTitle()));
    oldBook.setYear(Objects.requireNonNullElse(bookDetails.getYear(), oldBook.getYear()));
    repo.save(oldBook);

    return HttpResponse.ok();
  }

  @Delete("/{id}")
  @Transactional
  public HttpResponse<Void> deleteBook(long id) {
    Book oldBook = repo.findById(id).orElse(null);
    if (oldBook == null) {
      return HttpResponse.notFound();
    }

    repo.delete(oldBook);

    return HttpResponse.ok();
  }
}
