package uk.ac.york.eng2.books.cli.commands.user;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.books.clients.UsersClient;
import uk.ac.york.eng2.books.dto.UserDTO;

@CommandLine.Command(
    name = "add-user",
    description = "adds a new user record to the database",
    mixinStandardHelpOptions = true)
public class AddUserCommand implements Runnable {
  @Inject private UsersClient client;

  @CommandLine.Parameters(index = "0")
  private String name;

  @Override
  public void run() {
    UserDTO newUser = new UserDTO(name);

    HttpResponse<Void> result = client.add(newUser);

    if (result.code() == HttpStatus.CREATED.getCode()) {
      System.out.println("Successfully created user with name " + name);
    }
  }
}
