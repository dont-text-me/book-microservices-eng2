package uk.ac.york.eng2.books.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import uk.ac.york.eng2.books.cli.commands.book.*;
import uk.ac.york.eng2.books.cli.commands.user.*;

@Command(
    name = "book-cli",
    description = "...",
    mixinStandardHelpOptions = true,
    subcommands = {
      GetBooksCommand.class,
      AddBookCommand.class,
      GetBookCommand.class,
      GetBookReadersCommand.class,
      AddBookReaderCommand.class,
      RemoveBookReaderCommand.class,
      UpdateBookCommand.class,
      DeleteBookCommand.class,
      AddUserCommand.class,
      DeleteUserCommand.class,
      GetUserCommand.class,
      GetUsersCommand.class,
      UpdateUserCommand.class
    })
public class BookCliCommand implements Runnable {

  @Option(
      names = {"-v", "--verbose"},
      description = "...")
  boolean verbose;

  public static void main(String[] args) throws Exception {
    PicocliRunner.run(BookCliCommand.class, args);
  }

  public void run() {
    // business logic here
    if (verbose) {
      System.out.println("Hi!");
    }
  }
}
