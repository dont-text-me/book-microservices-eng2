package uk.ac.york.eng2.books.events;

import static uk.ac.york.eng2.books.events.Topics.TOPIC_BOOK_READ;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.books.domain.Book;

@KafkaListener(groupId = "books-debug")
public class BooksConsumer {
  @Topic(TOPIC_BOOK_READ)
  void notifyOfNewReader(@KafkaKey long id, Book book) {
    System.out.println(
        "User with id " + id + " has been added as reader to book " + book.toString());
  }
}
