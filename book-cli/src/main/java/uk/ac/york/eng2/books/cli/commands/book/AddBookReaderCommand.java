package uk.ac.york.eng2.books.cli.commands.book;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;

@CommandLine.Command(
    name = "add-book-reader",
    description = "adds a new reader to a book",
    mixinStandardHelpOptions = true)
public class AddBookReaderCommand implements Runnable {
  @Inject private BooksClient client;

  @CommandLine.Option(names = {"-b", "--book-id"})
  private long bookId;

  @CommandLine.Option(names = {"-u", "--user-id"})
  private long userId;

  @Override
  public void run() {
    HttpResponse<Void> result = client.updateReaders(bookId, userId);
    if (result.code() == HttpStatus.NOT_FOUND.getCode()) {
      System.out.println("Couldnt find book or user with hte provided id");
    } else {
      System.out.printf("Added user with id %s to book with id %s%n", userId, bookId);
    }
  }
}
