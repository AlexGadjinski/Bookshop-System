package com.example.bookshop.services.impl;

import com.example.bookshop.data.entities.Category;
import com.example.bookshop.data.repositories.CategoryRepository;
import com.example.bookshop.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORIES_PATH = "src/main/resources/files/categories.txt";
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        Set<Category> categories = new HashSet<>();
        Files.readAllLines(Path.of(CATEGORIES_PATH)).stream()
                .filter(l -> !l.trim().isEmpty())
                .forEach(l -> categories.add(new Category(l)));
        categoryRepository.saveAll(categories);

        System.out.printf("Count of imported categories - %d\n", categoryRepository.count());
    }

    @Override
    public boolean areCategoriesImported() {
        return categoryRepository.count() != 0;
    }
}
