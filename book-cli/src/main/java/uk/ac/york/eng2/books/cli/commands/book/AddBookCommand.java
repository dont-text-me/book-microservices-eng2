package uk.ac.york.eng2.books.cli.commands.book;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;
import uk.ac.york.eng2.books.dto.BookDTO;

@CommandLine.Command(
    name = "add-book",
    description = "adds a new book record to the database",
    mixinStandardHelpOptions = true)
public class AddBookCommand implements Runnable {
  @Inject private BooksClient client;

  @CommandLine.Parameters(index = "0")
  private String title;

  @CommandLine.Parameters(index = "1")
  private Integer year;

  @Override
  public void run() {
    BookDTO newBook = new BookDTO(title, year);

    HttpResponse<Void> result = client.add(newBook);
    if (result.code() == HttpStatus.CREATED.getCode()) {
      System.out.println("Successfully created book with title " + title);
    }
  }
}
