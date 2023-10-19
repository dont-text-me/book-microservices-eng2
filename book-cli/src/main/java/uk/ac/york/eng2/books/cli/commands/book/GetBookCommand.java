package uk.ac.york.eng2.books.cli.commands.book;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;
import uk.ac.york.eng2.books.domain.Book;

@CommandLine.Command(
    name = "get-book",
    description = "fetches a book with the provided id",
    mixinStandardHelpOptions = true)
public class GetBookCommand implements Runnable {

  @Inject private BooksClient client;

  @CommandLine.Parameters(index = "0")
  private long id;

  @Override
  public void run() {
    Book result = client.getBook(id);
    if (result == null) {
      System.out.println("Couldn't find book with id " + id);
      return;
    }
    System.out.println(result);
  }
}
