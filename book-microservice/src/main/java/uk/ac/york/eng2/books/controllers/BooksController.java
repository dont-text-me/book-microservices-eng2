package uk.ac.york.eng2.books.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.net.URI;
import java.util.Objects;
import uk.ac.york.eng2.books.domain.Book;
import uk.ac.york.eng2.books.domain.User;
import uk.ac.york.eng2.books.dto.BookDTO;
import uk.ac.york.eng2.books.events.BooksProducer;
import uk.ac.york.eng2.books.repositories.BooksRepository;
import uk.ac.york.eng2.books.repositories.UsersRepository;

@Controller("/books")
public class BooksController {
  @Inject private BooksRepository repo;
  @Inject private UsersRepository userRepo;
  @Inject private BooksProducer booksProducer;

  @Get("/")
  public Iterable<Book> list() {
    return repo.findAll();
  }

  @Post("/")
  public HttpResponse<Void> add(@Body BookDTO bookDetails) {
    Book newBook = new Book();
    newBook.setYear(bookDetails.year());
    newBook.setTitle(bookDetails.title());
    repo.save(newBook);

    return HttpResponse.created(URI.create("/books/" + newBook.getId()));
  }

  @Get("/{id}")
  public Book getBook(long id) {
    return repo.findById(id).orElse(null);
  }

  @Get("/{id}/readers")
  public Iterable<User> getReaders(long id) {
    Book result = repo.findById(id).orElse(null);
    if (result == null) {
      return null;
    }
    return result.getReaders();
  }

  @Put("/{id}/readers")
  @Transactional
  public HttpResponse<Void> updateReaders(long id, @Body long readerId) {
    User userResult = userRepo.findById(readerId).orElse(null);
    Book bookResult = repo.findById(id).orElse(null);
    if (userResult == null || bookResult == null) {
      return HttpResponse.notFound();
    }
    if (bookResult.addReader(userResult)) {
      booksProducer.readBook(readerId, bookResult);
    }
    repo.update(bookResult);
    return HttpResponse.ok();
  }

  @Delete("/{id}/readers")
  @Transactional
  public HttpResponse<Void> deleteReaders(long id, @Body long readerId) {
    User userResult = userRepo.findById(readerId).orElse(null);
    Book bookResult = repo.findById(id).orElse(null);
    if (userResult == null || bookResult == null) {
      return HttpResponse.notFound();
    }

    if (!bookResult.getReaders().contains(userResult)) {
      return HttpResponse.ok(); // Not a user already, return OK
    }

    bookResult.removeReader(userResult);
    repo.update(bookResult);
    return HttpResponse.ok();
  }

  @Put("/{id}")
  @Transactional
  public HttpResponse<Void> updateBook(long id, @Body BookDTO bookDetails) {
    Book oldBook = repo.findById(id).orElse(null);
    if (oldBook == null) {
      return HttpResponse.notFound();
    }
    oldBook.setTitle(Objects.requireNonNullElse(bookDetails.title(), oldBook.getTitle()));
    oldBook.setYear(Objects.requireNonNullElse(bookDetails.year(), oldBook.getYear()));
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
