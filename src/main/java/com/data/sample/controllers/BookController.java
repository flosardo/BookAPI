package com.data.sample.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.data.sample.models.Book;
import com.data.sample.services.BookService;

@RestController
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping("api/books") // buscar todos los libros
  public List<Book> getBooks() {
    return this.bookService.getAllBooks();
  }

  @GetMapping("api/books/{id}") // buscar UN libro por ID
  public ResponseEntity<Book> getBook(@PathVariable Integer id) {
    try {
      Book book = bookService.getBookById(id);
      return ResponseEntity.ok(book);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("api/books") // crear un libro
  public ResponseEntity<?> createBook(@RequestBody Book book) {
    try {
      Book bookCreated = this.bookService.addNewBook(book);
      return ResponseEntity.ok(bookCreated);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("api/books") // modificar un libro
  public ResponseEntity<?> updateBook(@RequestBody Book book) {
    try {
      Book bookModified = this.bookService.modifyBook(book);
      return ResponseEntity.ok(bookModified);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("api/books") // borrar todos los libros
  public ResponseEntity<?> deleteBook() {
    try {
      this.bookService.deleteAllBooks();
      return ResponseEntity.noContent().build();

    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("api/books/{id}") // borrar UN libro por ID
  public ResponseEntity<?> deleteBook(@PathVariable int id) {
    try {
      this.bookService.deleteBookById(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
