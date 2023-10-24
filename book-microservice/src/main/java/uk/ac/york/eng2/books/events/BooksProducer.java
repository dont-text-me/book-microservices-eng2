package uk.ac.york.eng2.books.events;

import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.books.domain.Book;

@KafkaClient
public interface BooksProducer {
  @Topic(TOPIC_BOOK_READ)
  void readBook(@KafkaKey Long id, Book b);
}
