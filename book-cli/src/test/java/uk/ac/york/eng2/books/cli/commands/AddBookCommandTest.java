package uk.ac.york.eng2.books.cli.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.books.cli.commands.book.AddBookCommand;

public class AddBookCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @BeforeEach
  public void clearSysOut() {
    baos.reset();
  }

  @Test
  public void canCreateBook() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"Container Security", "2020"};
      PicocliRunner.run(AddBookCommand.class, ctx, args);

      // book-cli
      assertTrue(baos.toString().contains("Successfully created book with title"));
    }
  }
}
