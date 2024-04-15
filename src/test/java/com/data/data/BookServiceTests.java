package com.data.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.data.sample.DataApplication;
import com.data.sample.db.BookRepository;
import com.data.sample.models.Book;
import com.data.sample.services.BookService;

@DataJpaTest
@ContextConfiguration(classes = { DataApplication.class })
public class BookServiceTests {

  @Mock
  private BookRepository repository;

  private BookService bookService;

  @BeforeEach
  void setUp() {
    bookService = new BookService(this.repository);
  }

  @AfterEach
  void tearDown() {
    this.repository.deleteAll();
  }

  @Test
  void shouldGiveAllBooks() {
    Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 19.99, 1178, LocalDate.of(1954, 7, 29));
    Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 14.99, 310, LocalDate.of(1937, 9, 21));
    when(this.repository.findAll()).thenReturn(List.of(book1, book2));

    List<Book> result = bookService.getAllBooks();

    assertEquals(2, result.size());
    assertEquals("The Lord of the Rings", result.get(0).getTitle());
    assertEquals("The Hobbit", result.get(1).getTitle());
    verify(this.repository, times(1)).findAll();
  }

  @Test
  void shouldGiveOneBook() {
    Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 19.99, 1178, LocalDate.of(1954, 7, 29));
    when(this.repository.findById(1)).thenReturn(Optional.of(book1));

    Book result = this.bookService.getBookById(1);

    assertEquals("The Lord of the Rings", result.getTitle());
    verify(this.repository, times(1)).findById(1);

  }

  @Test
  void shouldCreateBook() {
    Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 19.99, 1178, LocalDate.of(1954, 7, 29));
    when(this.repository.save(book)).thenReturn(book);

    Book bookCreated = this.bookService.addNewBook(book);
    assertNotNull(bookCreated);

    assertEquals("The Lord of the Rings", bookCreated.getTitle());
    assertEquals(19.99, bookCreated.getPrice());
    verify(this.repository, times(1)).save((book));
  }

  @Test
  void shouldModifyBook() {
    Book book = new Book(1, "The Lord of the Rings", "J.R.R. Tolkien", 19.99, 1178, LocalDate.of(1954, 7, 29));

    when(repository.existsById(1)).thenReturn(true);
    when(repository.save(book)).thenReturn(book);

    Book bookModified = this.bookService.modifyBook(book);

    assertNotNull(bookModified);
    assertEquals(1, bookModified.getId());
    verify(this.repository, times(1)).save((book));
  }

  @Test
  void deleteOneBook() {
    when(repository.existsById(1)).thenReturn(true);

    this.bookService.deleteBookById(1);

    verify(repository, times(1)).deleteById(1);

  }

  @Test
  void deleteAllBooks() {
    when(repository.count()).thenReturn(1L);

    this.bookService.deleteAllBooks();

    verify(repository, times(1)).deleteAll();
    assertEquals(0, this.bookService.getAllBooks().size());
  }
}
