package com.gmail.woosay333.onlinebookstore;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    private final BookService bookService;

    @Autowired
    public OnlineBookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = Book.builder()
                    .title("Effective Java")
                    .author("Joshua Bloch")
                    .price(BigDecimal.valueOf(34.93))
                    .isbn("9780321356680")
                    .description("Book about java programming language")
                    .coverImage("Effective Java Third edition")
                    .build();
            bookService.save(book);
            bookService.findAll().forEach(System.out::println);
        };
    }
}
