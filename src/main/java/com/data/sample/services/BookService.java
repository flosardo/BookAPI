package com.data.sample.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.sample.db.BookRepository;
import com.data.sample.models.Book;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BookService {

  @Autowired
  private BookRepository repository;

  public List<Book> getAllBooks() {
    return this.repository.findAll();
  }

  public Book getBookById(Integer id) {
    Book book = this.repository.findById(id).orElse(null);
    if (book == null) {
      throw new NoSuchElementException("El libro con ID " + id + " no fue encontrado");
    }
    return book;
  }

  public Book addNewBook(Book book) {
    if (book.getId() != null) {
      throw new IllegalArgumentException("El ID del libro debe ser nulo");
    }
    if (book.getPrice() < 0) {
      throw new IllegalArgumentException("El precio debe ser positivo");
    }
    return this.repository.save(book);
  }

  public Book modifyBook(Book bookModified) {
    if (bookModified.getId() == null) {
      throw new IllegalArgumentException("El ID del libro no debe ser nulo");
    }
    if (!this.repository.existsById(bookModified.getId())) {
      throw new NoSuchElementException("El libro con ID " + bookModified.getId() + " no fue encontrado");
    }
    return this.repository.save(bookModified);
  }

  public void deleteAllBooks() {
    if (this.repository.count() == 0) {
      throw new NoSuchElementException("No hay libros para eliminar");
    }
    this.repository.deleteAll();
  }

  public void deleteBookById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("El ID no puede ser nulo");
    }
    if (!this.repository.existsById(id)) {
      throw new IllegalArgumentException("El libro con ID " + id + " no puede ser eliminado ya que no existe");
    }
    this.repository.deleteById(id);
  }
}
