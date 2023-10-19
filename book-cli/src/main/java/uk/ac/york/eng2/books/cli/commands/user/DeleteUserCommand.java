package uk.ac.york.eng2.books.cli.commands.user;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.UsersClient;

@CommandLine.Command(
    name = "delete-user",
    description = "deletes a user with the provided id",
    mixinStandardHelpOptions = true)
public class DeleteUserCommand implements Runnable {

  @Inject private UsersClient client;

  @CommandLine.Parameters(index = "0")
  private long id;

  @Override
  public void run() {
    HttpResponse<Void> result = client.deleteUser(id);
    if (result.code() == HttpStatus.NOT_FOUND.getCode()) {
      System.out.println("Could not find user with id " + id);
    } else {
      System.out.println("Successfully deleted user with id " + id);
    }
  }
}
