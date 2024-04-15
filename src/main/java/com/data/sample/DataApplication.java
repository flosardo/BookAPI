package com.data.sample;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.data.sample.db.BookRepository;
import com.data.sample.models.Book;

@SpringBootApplication
public class DataApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DataApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);
		Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 19.99, 1178, LocalDate.of(1954, 7, 29));
		Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 14.99, 310, LocalDate.of(1937, 9, 21));
		repository.save(book);
		repository.save(book2);

	}

}
