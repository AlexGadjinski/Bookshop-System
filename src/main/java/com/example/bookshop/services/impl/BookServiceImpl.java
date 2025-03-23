package com.example.bookshop.services.impl;

import com.example.bookshop.data.entities.Author;
import com.example.bookshop.data.entities.Book;
import com.example.bookshop.data.entities.Category;
import com.example.bookshop.data.entities.enums.AgeRestriction;
import com.example.bookshop.data.entities.enums.EditionType;
import com.example.bookshop.data.repositories.AuthorRepository;
import com.example.bookshop.data.repositories.BookRepository;
import com.example.bookshop.data.repositories.CategoryRepository;
import com.example.bookshop.services.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOKS_PATH = "src/main/resources/files/books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of(BOOKS_PATH)).stream()
                .filter(l -> !l.isBlank())
                .forEach(l -> {
                    String[] tokens = l.split(" ");
                    Book book = new Book(
                            AgeRestriction.values()[Integer.parseInt(tokens[4])],
                            Integer.parseInt(tokens[2]),
                            EditionType.values()[Integer.parseInt(tokens[0])],
                            new BigDecimal(tokens[3]),
                            LocalDate.of(
                                    Integer.parseInt(tokens[1].split("/")[2]),
                                    Integer.parseInt(tokens[1].split("/")[1]),
                                    Integer.parseInt(tokens[1].split("/")[0])
                            ),
//                            LocalDate.parse(tokens[1], DateTimeFormatter.ofPattern("d/M/yyyy")),
                            Arrays.stream(tokens).skip(5).collect(Collectors.joining(" "))
                    );
                    book.setAuthor(getRandomAuthor());
                    book.setCategories(getRandomCategories());

                    bookRepository.save(book);
                });
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        // с имплементирането на equals() в Category указваме на Set-а да не добавя елементи, които вече съществуват;
        // иначе Set-а би ни ги запазил, и впоследствие базата ни ще гръмне

        long categoriesCount = new Random().nextLong(1, 5);
        for (int i = 0; i < categoriesCount; i++) {
            long categoryId = new Random().nextLong(1, categoryRepository.count() + 1);
            categories.add(categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new));
        }
        return categories;
    }

    private Author getRandomAuthor() {
        Optional<Author> optionalAuthor = authorRepository.findById(
                new Random().nextLong(1, authorRepository.count() + 1));
//        return optionalAuthor.orElse(null);
        return optionalAuthor.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean areBooksImported() {
        return bookRepository.count() != 0;
    }

    @Override
    public void printAllBooksAfter2000() {
        bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 12, 31))
                .forEach(b -> System.out.printf("%s\n", b.getTitle()));
    }

    @Override
    public void printAllBooksFromGeorge() {
        bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("George", "Powell")
                .forEach(b -> System.out.printf("%s %s %d\n", b.getTitle(), b.getReleaseDate(), b.getCopies()));
    }
}
