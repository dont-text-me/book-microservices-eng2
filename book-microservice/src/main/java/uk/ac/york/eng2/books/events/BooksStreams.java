package uk.ac.york.eng2.books.events;

import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ;
import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ_BY_DAY;

import io.micronaut.configuration.kafka.serde.CompositeSerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import io.micronaut.serde.exceptions.SerdeException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import uk.ac.york.eng2.books.domain.Book;

@Factory
public class BooksStreams {
  @Inject private CompositeSerdeRegistry serdeRegistry;

  @Singleton
  KStream<WindowedIdentifier, Long> readByDay(ConfiguredStreamBuilder builder) {
    Properties props = builder.getConfiguration();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "books-metrics");
    props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);

    KStream<WindowedIdentifier, Long> stream = builder.stream(
                    TOPIC_BOOK_READ, Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Book.class)))
            .selectKey((k, v) -> v.getId())
            .groupByKey(Grouped.with(Serdes.Long(), serdeRegistry.getSerde(Book.class)))
            .windowedBy(
                    TimeWindows.ofSizeWithNoGrace(Duration.ofHours(24)).advanceBy(Duration.ofHours(24)))
            .count()
            .toStream()
            .selectKey((k, v) -> new WindowedIdentifier(k.key(), k.window().start(), k.window().end()));

    stream.to(TOPIC_BOOK_READ_BY_DAY, Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));

    return stream;
  }
}
