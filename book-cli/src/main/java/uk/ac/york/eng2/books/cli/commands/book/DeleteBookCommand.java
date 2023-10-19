package uk.ac.york.eng2.books.cli.commands.book;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;

@CommandLine.Command(
    name = "delete-book",
    description = "deletes a book with the provided id",
    mixinStandardHelpOptions = true)
public class DeleteBookCommand implements Runnable {
  @Inject private BooksClient client;

  @CommandLine.Parameters(index = "0")
  private long id;

  @Override
  public void run() {
    HttpResponse<Void> result = client.deleteBook(id);
    if (result.code() == HttpStatus.NOT_FOUND.getCode()) {
      System.out.println("Could not find book with id " + id);
    } else {
      System.out.println("Successfully deleted book with id " + id);
    }
  }
}
