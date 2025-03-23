package com.example.bookshop.services;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;

    boolean areAuthorsImported();

    void printAllAuthorsWithBookBefore1990();

    void printAllAuthorsWithBookDescending();
}
