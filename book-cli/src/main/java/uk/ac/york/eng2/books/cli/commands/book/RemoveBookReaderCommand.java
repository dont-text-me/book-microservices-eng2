package uk.ac.york.eng2.books.cli.commands.book;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;

@CommandLine.Command(
    name = "remove-book-reader",
    description = "removes a reader from a book",
    mixinStandardHelpOptions = true)
public class RemoveBookReaderCommand implements Runnable {

  @Inject private BooksClient client;

  @CommandLine.Option(names = {"-b", "--book-id"})
  private long bookId;

  @CommandLine.Option(names = {"-u", "--user-id"})
  private long userId;

  @Override
  public void run() {
    HttpResponse<Void> result = client.deleteReaders(bookId, userId);
    if (result.code() == HttpStatus.NOT_FOUND.getCode()) {
      System.out.println("Couldnt find book or user with hte provided id");
    } else {
      System.out.printf("Removed user with id %s from book with id %s%n", userId, bookId);
    }
  }
}
