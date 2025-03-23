package com.example.bookshop.services.impl;

import com.example.bookshop.data.entities.Author;
import com.example.bookshop.data.repositories.AuthorRepository;
import com.example.bookshop.services.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String AUTHORS_PATH = "src/main/resources/files/authors.txt";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(AUTHORS_PATH)).stream()
                .filter(l -> !l.trim().isEmpty())
                .forEach(l -> {
                    String[] tokens = l.split(" ");
                    authorRepository.save(new Author(tokens[0], tokens[1]));
                });

        System.out.printf("Count of imported authors - %d\n", authorRepository.count());
     }

    @Override
    public boolean areAuthorsImported() {
        return authorRepository.count() != 0;
    }

    @Override
    public void printAllAuthorsWithBookBefore1990() {
        authorRepository.findAllByBooksReleaseDateBefore(LocalDate.of(1990, 1, 1))
                .forEach(a -> System.out.printf("%s %s\n", a.getFirstName(), a.getLastName()));

//        с Java код:
//        authorRepository.findAll().stream()
//                .filter(a -> a.getBooks().stream().anyMatch(b -> b.getReleaseDate().getYear() < 1990))
//                .forEach(a -> System.out.printf("%s %s\n", a.getFirstName(), a.getLastName()));
    }

    @Override
    public void printAllAuthorsWithBookDescending() {
        authorRepository.findAll().stream()
                .sorted((a1, a2) -> Integer.compare(a2.getBooks().size(), a1.getBooks().size()))
                .forEach(a -> System.out.printf("%s %s - %d\n", a.getFirstName(), a.getLastName(), a.getBooks().size()));
    }
}
