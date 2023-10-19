package uk.ac.york.eng2.books.cli.commands.user;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.UsersClient;
import uk.ac.york.eng2.books.domain.User;

@CommandLine.Command(
    name = "get-users",
    description = "fetches a list of all users in the database",
    mixinStandardHelpOptions = true)
public class GetUsersCommand implements Runnable {
  @Inject private UsersClient client;

  @Override
  public void run() {
    Iterable<User> result = client.list();
    result.forEach(it -> System.out.println(it.toString()));
  }
}
