package uk.ac.york.eng2.books.events;

import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ_BY_DAY;

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
import org.apache.kafka.streams.KafkaStreams;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;
import uk.ac.york.eng2.books.domain.Book;

@MicronautTest
@Property(name = "spec.name", value = "MicronautStreamsTest")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
// TODO fix during lab
public class MicronautStreamsTest {
  @Inject BooksProducer producer;
  @Inject KafkaStreams kStreams;

  @BeforeEach
  public void setUp() {
    events.clear();
    Awaitility.await().until(() -> kStreams.state().equals(KafkaStreams.State.RUNNING));
  }

  @AfterAll
  public void closeStreams() {
    kStreams.close();
  }

  private static final Map<WindowedIdentifier, Long> events = new HashMap<>();

  @Requires(property = "spec.name", value = "MicronautStreamsTest")
  @KafkaListener(groupId = "kafka-streams-test")
  static class StreamsListener {
    @Topic(TOPIC_BOOK_READ_BY_DAY)
    void dailySummary(@KafkaKey WindowedIdentifier id, Long count) {
      events.put(id, count);
    }
  }

  @Test
  public void updatesCount() {
    producer.readBook(1L, new Book());

    Awaitility.await().atMost(Duration.ofSeconds(30)).until(() -> !events.isEmpty());
  }
}
