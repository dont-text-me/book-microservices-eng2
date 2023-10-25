package uk.ac.york.eng2.books.controllers;

import io.micronaut.context.annotation.Context;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.ac.york.eng2.books.domain.Book;
import uk.ac.york.eng2.books.dto.BookDTO;
import uk.ac.york.eng2.books.repositories.BooksRepository;
import uk.ac.york.eng2.books.repositories.UsersRepository;
import uk.ac.york.eng2.utils.DbCleanupExtension;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
//@ExtendWith(DbCleanupExtension.class)
public class BooksControllerTest {
    @Inject private BooksClient client;
    @Inject private BooksRepository repo;
    @Inject private UsersRepository userRepo;
    @BeforeEach
    public void cleanUp(){
        repo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void noBooks(){
        Iterable<Book> iterBooks = client.list();
        assertFalse(iterBooks.iterator().hasNext());
    }

    @Test
    public void getBook(){
        Book newBook = new Book();
        newBook.setTitle("Container Security");
        newBook.setYear(2020);

        repo.save(newBook);

        Book result = client.getBook(newBook.getId());

        assertEquals(result.getTitle(), "Container Security");
        assertEquals(result.getYear(), 2020);
    }

    @Test
    public void updateBook(){
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
}
