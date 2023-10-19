package uk.ac.york.eng2.books.repositories;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import uk.ac.york.eng2.books.domain.Book;

@Repository
public interface BooksRepository extends CrudRepository<Book, Long> {
  @Join(value = "readers", type = Join.Type.LEFT_FETCH)
  @Override
  Optional<Book> findById(@Nonnull Long id);
}
