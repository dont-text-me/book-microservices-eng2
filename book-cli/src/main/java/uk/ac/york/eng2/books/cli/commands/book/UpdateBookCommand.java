package uk.ac.york.eng2.books.cli.commands.book;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;
import uk.ac.york.eng2.books.dto.BookDTO;

@CommandLine.Command(
    name = "update-book",
    description = "updates details of an existing book",
    mixinStandardHelpOptions = true)
public class UpdateBookCommand implements Runnable {
  @Inject private BooksClient client;

  @CommandLine.Parameters(index = "0")
  private long id;

  @CommandLine.Option(
      names = {"-t", "--title"},
      description = "Title of the book")
  private String title;

  @CommandLine.Option(
      names = {"-y", "--year"},
      description = "Year of the book")
  private Integer year;

  @Override
  public void run() {
    BookDTO bookDetails = new BookDTO();
    bookDetails.setYear(year);
    bookDetails.setTitle(title);

    HttpResponse<Void> result = client.updateBook(id, bookDetails);

    if (result.code() == HttpStatus.NOT_FOUND.getCode()) {
      System.out.println("Could not find book with id " + id);
    } else {
      System.out.println(
          "Updated book with id " + id + " with the following details: " + bookDetails);
    }
  }
}
