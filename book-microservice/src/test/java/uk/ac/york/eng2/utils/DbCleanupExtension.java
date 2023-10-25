package uk.ac.york.eng2.utils;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.test.context.TestExecutionListener;
import jakarta.inject.Inject;
import java.util.Optional;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import uk.ac.york.eng2.books.repositories.BooksRepository;
import uk.ac.york.eng2.books.repositories.UsersRepository;
public class DbCleanupExtension implements BeforeEachCallback {
    @Inject ApplicationContext applicationContext;

  @Override
  public void beforeEach(ExtensionContext context) {
//      System.out.println("Clearing database...");
//    applicationContext.getAllBeanDefinitions().stream()
//        .filter(
//            (beanDefinition -> {
//              Optional<AnnotationValue<Repository>> ann =
//                  beanDefinition.findAnnotation(Repository.class);
//              return ann.isPresent();
//            })) // find all the @Repository's in the application context
//        .toList()
//        .forEach(it -> ((CrudRepository<?, ?>) applicationContext.getBean(it)).deleteAll()); // delete everything from them

  }
}
