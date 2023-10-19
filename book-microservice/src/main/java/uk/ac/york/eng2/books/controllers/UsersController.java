package uk.ac.york.eng2.books.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.net.URI;
import uk.ac.york.eng2.books.domain.User;
import uk.ac.york.eng2.books.dto.UserDTO;
import uk.ac.york.eng2.books.repositories.UsersRepository;

@Controller("/users")
public class UsersController {
  @Inject private UsersRepository repo;

  @Get("/")
  public Iterable<User> list() {
    return repo.findAll();
  }

  @Get("/{id}")
  public User getUser(long id) {
    return repo.findById(id).orElse(null);
  }

  @Post("/")
  public HttpResponse<Void> add(@Body UserDTO userDetails) {
    User newUser = new User();
    newUser.setName(userDetails.name());

    repo.save(newUser);

    return HttpResponse.created(URI.create("/users/" + newUser.getId()));
  }

  @Put("/{id}")
  @Transactional
  public HttpResponse<Void> updateUser(long id, @Body UserDTO userDetails) {
    User oldUser = repo.findById(id).orElse(null);
    if (oldUser == null) {
      return HttpResponse.notFound();
    }
    oldUser.setName(userDetails.name());
    repo.save(oldUser);

    return HttpResponse.ok();
  }

  @Delete("/{id}")
  @Transactional
  public HttpResponse<Void> deleteUser(long id) {
    User oldUser = repo.findById(id).orElse(null);
    if (oldUser == null) {
      return HttpResponse.notFound();
    }
    repo.delete(oldUser);

    return HttpResponse.ok();
  }
}
