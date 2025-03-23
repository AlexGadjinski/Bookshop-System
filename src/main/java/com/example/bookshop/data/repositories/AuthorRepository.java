package com.example.bookshop.data.repositories;

import com.example.bookshop.data.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findAllByBooksReleaseDateBefore(LocalDate localDate);
}
