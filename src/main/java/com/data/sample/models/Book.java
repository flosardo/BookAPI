package com.data.sample.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Integer id;

  @Getter
  @Setter
  private String title;
  @Getter
  @Setter
  private String author;
  @Getter
  @Setter
  private Double price;
  @Getter
  @Setter
  private int pages;
  @Getter
  @Setter
  private LocalDate releaseDate;

  public Book(String title, String author, Double price, int pages, LocalDate releaseDate) {
    this.title = title;
    this.author = author;
    this.price = price;
    this.pages = pages;
    this.releaseDate = releaseDate;
  }

  // solo para los tests
  public Book(Integer id, String title, String author, Double price, int pages, LocalDate releaseDate) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.price = price;
    this.pages = pages;
    this.releaseDate = releaseDate;
  }

  public Book() {
  }

}
