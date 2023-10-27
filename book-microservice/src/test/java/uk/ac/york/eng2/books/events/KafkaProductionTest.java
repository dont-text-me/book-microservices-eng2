package uk.ac.york.eng2.books.events;

import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.ac.york.eng2.books.domain.Book;
import uk.ac.york.eng2.books.domain.User;
import uk.ac.york.eng2.books.repositories.BooksRepository;
import uk.ac.york.eng2.books.repositories.UsersRepository;
import uk.ac.york.eng2.utils.BooksClient;
import uk.ac.york.eng2.utils.DbCleanupExtension;

@MicronautTest(transactional = false, environments = "no_streams")
@Property(name = "spec.name", value = "KafkaProductionTest")
@ExtendWith(DbCleanupExtension.class)
public class KafkaProductionTest {
  @Inject private BooksClient client;
  @Inject private BooksRepository repo;
  @Inject private UsersRepository userRepo;
  private static final Map<Long, Book> readBooks = new HashMap<>();

  @BeforeEach
  public void clearMessages() {
    readBooks.clear();
  }

  @Test
  public void addBookReader() {
    Book newBook = new Book();
    newBook.setTitle("Container Security");
    newBook.setYear(2020);
    repo.save(newBook);

    User newUser = new User();
    newUser.setName("Alice");
    userRepo.save(newUser);

    client.updateReaders(newBook.getId(), newUser.getId());

    Awaitility.await()
        .atMost(Duration.ofSeconds(30))
        .until(() -> readBooks.containsKey(newUser.getId()));
  }

  @Requires(property = "spec.name", value = "KafkaProductionTest")
  @KafkaListener(groupId = "kafka-production-test")
  static class TestConsumer {
    @Topic(TOPIC_BOOK_READ)
    void readBook(@KafkaKey Long id, Book book) {
      System.out.printf("Received event with key %s, value %s%n", id, book);
      readBooks.put(id, book);
    }
  }
}
