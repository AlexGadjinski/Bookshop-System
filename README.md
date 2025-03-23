# Bookshop System

The Bookshop System is a simple database-driven application designed to manage books, authors, and categories for a bookshop. This system allows for organizing and managing books by storing essential details like title, author, categories, edition type, price, release date, and more. It also provides functionalities to query and filter books based on various criteria, such as publication year, author, and more.

## Features:
- **Entities**: `Book`, `Author`, and `Category`, each with necessary attributes like book title, author name, book price, and more.
- **CRUD operations**: Basic CRUD functionality for books, authors, and categories.
- **Seed Data**: Populates the database with sample data from external files (`books.txt`, `authors.txt`, `categories.txt`).
- **Custom Queries**: Provides SQL queries to filter books and authors based on conditions like release year, author name, and book count.

## Setup:
1. Clone the repository and navigate to the project directory.
2. Import the project into your IDE (e.g., IntelliJ IDEA).
4. Execute the `seedData()` method in `ConsoleRunner` to populate the database with sample data.
5. Run the project to perform queries on the bookshop database.

## Queries:
- Get all books after the year 2000.
- Get all authors with at least one book released before 1990.
- Get authors ordered by the number of books they have published.
- Get books from author George Powell, ordered by release date and book title.
