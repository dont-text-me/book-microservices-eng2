package uk.ac.york.eng2.books.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ;
import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ_BY_DAY;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.books.domain.Book;

/**
 * Example of a Test Double approach for testing our Kafka Streams logic, without actually using a
 * Kafka cluster. We use a simulated Kafka Streams driver included with KS: the {@code
 * TopologyTestDriver}.
 */
@MicronautTest(environments = "no_streams") // TODO fix during lab
@Disabled
public class TestDoubleStreamsTest {

  @Inject private SerdeRegistry serdeRegistry;
  @Inject private BooksStreams streams;

  @Test
  public void topologyCheck() {
    final ConfiguredStreamBuilder builder = new ConfiguredStreamBuilder(new Properties());
    streams.readByDay(builder);

    try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build())) {
      TestInputTopic<Long, Book> inputTopic =
          testDriver.createInputTopic(
              TOPIC_BOOK_READ, new LongSerializer(), serdeRegistry.getSerializer(Book.class));

      final long bookId = 1L;
      final int eventCount = 2;
      for (int i = 0; i < eventCount; i++) {
        inputTopic.pipeInput(bookId, new Book());
      }

      TestOutputTopic<WindowedIdentifier, Long> outputTopic =
          testDriver.createOutputTopic(
              TOPIC_BOOK_READ_BY_DAY,
              serdeRegistry.getDeserializer(WindowedIdentifier.class),
              new LongDeserializer());

      List<KeyValue<WindowedIdentifier, Long>> keyValues = outputTopic.readKeyValuesToList();
      assertFalse(keyValues.isEmpty());

      KeyValue<WindowedIdentifier, Long> lastKeyValue = keyValues.get(keyValues.size() - 1);
      assertEquals(bookId, lastKeyValue.key.getId());
      assertEquals(eventCount, lastKeyValue.value);
    }
  }
}
