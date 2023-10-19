package uk.ac.york.eng2.books.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.books.domain.User;
import uk.ac.york.eng2.books.dto.UserDTO;

@Client("http://localhost:8080/users")
public interface UsersClient {
  @Get("/")
  public Iterable<User> list();

  @Get("/{id}")
  public User getUser(long id);

  @Post("/")
  public HttpResponse<Void> add(@Body UserDTO userDetails);

  @Put("/{id}")
  public HttpResponse<Void> updateUser(long id, @Body UserDTO userDetails);

  @Delete("/{id}")
  public HttpResponse<Void> deleteUser(long id);
}
