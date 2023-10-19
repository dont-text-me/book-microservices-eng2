package uk.ac.york.eng2.books.cli.commands.book;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;

@CommandLine.Command(
    name = "get-book-readers",
    description = "fetches a list of users that are readers for a book with a given id",
    mixinStandardHelpOptions = true)
public class GetBookReadersCommand implements Runnable {
  @Inject private BooksClient client;

  @CommandLine.Parameters(index = "0")
  private long id;

  @Override
  public void run() {
    client.getReaders(id).forEach(System.out::println);
  }
}
