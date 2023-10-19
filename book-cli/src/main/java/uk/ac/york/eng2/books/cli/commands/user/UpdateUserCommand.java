package uk.ac.york.eng2.books.cli.commands.user;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.UsersClient;
import uk.ac.york.eng2.books.dto.UserDTO;

@CommandLine.Command(
    name = "update-user",
    description = "updates details of an existing user",
    mixinStandardHelpOptions = true)
public class UpdateUserCommand implements Runnable {
  @Inject private UsersClient client;

  @CommandLine.Parameters(index = "0")
  private long id;

  @CommandLine.Option(
      names = {"-n", "--name"},
      description = "Name of the user")
  private String name;

  @Override
  public void run() {
    UserDTO userDetails = new UserDTO(name);

    HttpResponse<Void> result = client.updateUser(id, userDetails);

    if (result.code() == HttpStatus.NOT_FOUND.getCode()) {
      System.out.println("Could not find user with id " + id);
    } else {
      System.out.println(
          "Updated user with id " + id + " with the following details: " + userDetails);
    }
  }
}
