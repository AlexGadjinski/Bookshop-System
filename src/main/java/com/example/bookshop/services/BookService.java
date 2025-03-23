package com.example.bookshop.services;

import java.io.IOException;

public interface BookService {
    void seedBooks() throws IOException;

    boolean areBooksImported();

    void printAllBooksAfter2000();

    void printAllBooksFromGeorge();
}
