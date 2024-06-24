package com.gmail.woosay333.onlinebookstore.repository.book;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByIsbn(String isbn);

    @Query("FROM Book b JOIN FETCH b.categories WHERE b.id = :id")
    Optional<Book> findByIdWithCategories(Long id);

    @NonNull
    @EntityGraph(attributePaths = {"categories"})
    Page<Book> findAll(@NonNull Specification<Book> specification, @NonNull Pageable pageable);

    @Query("FROM Book b JOIN FETCH b.categories")
    List<Book> findAllBooks(Pageable pageable);

    @EntityGraph(attributePaths = {"categories"})
    List<Book> findAllByCategories_Id(Long categoryId, Pageable pageable);

    List<Book> findAllByIdOrIsbn(Long id, String isbn);
}
