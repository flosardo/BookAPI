package com.data.sample.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.data.sample.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
