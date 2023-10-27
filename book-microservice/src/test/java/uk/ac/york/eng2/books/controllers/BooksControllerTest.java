package uk.ac.york.eng2.books.controllers;

import static org.junit.jupiter.api.Assertions.*;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.ac.york.eng2.books.domain.Book;
import uk.ac.york.eng2.books.domain.User;
import uk.ac.york.eng2.books.dto.BookDTO;
import uk.ac.york.eng2.books.events.BooksProducer;
import uk.ac.york.eng2.books.repositories.BooksRepository;
import uk.ac.york.eng2.books.repositories.UsersRepository;
import uk.ac.york.eng2.utils.BooksClient;
import uk.ac.york.eng2.utils.DbCleanupExtension;

@MicronautTest(transactional = false, environments = "no_streams")
@ExtendWith(DbCleanupExtension.class)
public class BooksControllerTest {
  @Inject private BooksClient client;
  @Inject private BooksRepository repo;
  @Inject private UsersRepository userRepo;
  private final Map<Long, Book> readBooks = new HashMap<>();

  @BeforeEach
  public void clearMessages() {
    readBooks.clear();
  }

  @MockBean(BooksProducer.class)
  BooksProducer testProducer() {
    return readBooks::put;
  }

  @Test
  public void noBooks() {
    Iterable<Book> iterBooks = client.list();
    assertFalse(iterBooks.iterator().hasNext());
  }

  @Test
  public void getBook() {
    Book newBook = new Book();
    newBook.setTitle("Container Security");
    newBook.setYear(2020);

    repo.save(newBook);

    Book result = client.getBook(newBook.getId());

    assertEquals(result.getTitle(), "Container Security");
    assertEquals(result.getYear(), 2020);
  }

  @Test
  public void updateBook() {
    Book newBook = new Book();
    newBook.setTitle("Container Security");
    newBook.setYear(2020);

    repo.save(newBook);

    BookDTO details = new BookDTO("New title", null);
    HttpResponse<Void> result = client.updateBook(newBook.getId(), details);

    assertEquals(result.getStatus(), HttpStatus.OK);

    Book bookResult = repo.findById(newBook.getId()).get();

    assertEquals(bookResult.getTitle(), "New title");
  }

  @Test
  public void addBookReader() {
    Book newBook = new Book();
    newBook.setTitle("Container Security");
    newBook.setYear(2020);
    repo.save(newBook);

    User newUser = new User();
    newUser.setName("Alice");
    userRepo.save(newUser);

    client.updateReaders(newBook.getId(), newUser.getId());

    assertTrue(readBooks.containsKey(newUser.getId()));
  }
}
