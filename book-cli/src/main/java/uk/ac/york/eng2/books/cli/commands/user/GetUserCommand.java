package uk.ac.york.eng2.books.cli.commands.user;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.UsersClient;
import uk.ac.york.eng2.books.domain.User;

@CommandLine.Command(
    name = "get-user",
    description = "fetches a user with the provided id",
    mixinStandardHelpOptions = true)
public class GetUserCommand implements Runnable {
  @Inject private UsersClient client;

  @CommandLine.Parameters(index = "0")
  private long id;

  @Override
  public void run() {
    User result = client.getUser(id);
    if (result == null) {
      System.out.println("Couldn't find book with id " + id);
      return;
    }
    System.out.println(result);
  }
}
