package com.example.bookshop.controller;

import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleLineRunner implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public ConsoleLineRunner(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
//        bookService.printAllBooksAfter2000();
//        authorService.printAllAuthorsWithBookBefore1990();
//        authorService.printAllAuthorsWithBookDescending();
        bookService.printAllBooksFromGeorge();
    }

    private void seedData() throws IOException {
        if (!categoryService.areCategoriesImported()) {
            categoryService.seedCategories();
        }
        if (!authorService.areAuthorsImported()) {
            authorService.seedAuthors();
        }
        if (!bookService.areBooksImported()) {
            bookService.seedBooks();
        }
    }
}
