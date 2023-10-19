package uk.ac.york.eng2.books.cli.commands.book;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.BooksClient;
import uk.ac.york.eng2.books.domain.Book;

@CommandLine.Command(
    name = "get-books",
    description = "fetches a list of all books in the database",
    mixinStandardHelpOptions = true)
public class GetBooksCommand implements Runnable {
  @Inject private BooksClient client;

  @Override
  public void run() {
    Iterable<Book> result = client.list();
    result.forEach(it -> System.out.println(it.toString()));
  }
}
