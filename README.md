# 📚 Library Management System - kata

## 🌟 Overview

Welcome to the *Library Management System*! This Java-based application helps you manage your library with ease. Whether you're an admin or a regular user, this system has everything you need for registering users, managing books, and handling transactions like borrowing and returning books. We've built this project using Test-Driven Development (TDD) to ensure robustness and reliability.

## 🚀 Features

- *User Management:*
  - Register new users
  - Secure login with credentials
- *Book Management:*
  - Add new books to the library
  - List and manage available books
  - Borrow and return books effortlessly
- *Test-Driven Development:*
  - Thorough test coverage with JUnit
  - Ensures all functionalities are working perfectly

## 🛠 Technical Overview

### Database Connection

The Library Management System uses a MySQL database to store information about books, users, and borrowed books. The database connection is managed through a helper class, as shown below:

java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


This class establishes a connection to the database using JDBC. It can be used throughout the application to execute SQL queries, ensuring a consistent and reliable connection to the database.

### Logic and Flow

- *User Registration and Login:*
  - Users register by providing a unique username and password.
  - Passwords are stored securely, and login checks against the stored credentials.
  - Role-based access ensures that admins have additional privileges, such as managing books.

- *Book Management:*
  - Admins can add new books by providing details like ISBN, title, author, and publication year.
  - The system checks if a book is already in the library by its ISBN, preventing duplicates.
  - Books can be marked as borrowed, which updates their availability status in the database.

- *Borrowing and Returning Books:*
  - Users can borrow books if they are available.
  - The system records the borrowing event, linking the user and the book in the borrowed_books table.
  - When returning a book, the system removes the record from the borrowed_books table and marks the book as available again.

### Project Structure


LibraryManagementSystem/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Book.java           # Represents a book entity
│   │   │   ├── Library.java        # Core library management logic
│   │   │   ├── LibrarySystem.java  # Main entry point for the application
│   │   │   ├── User.java           # Represents a user entity
│   │         └|── DatabaseHelper.java # Manages database connections
│   │      
│   └── test/
│       ├── java/
│            └── LibraryTest.java    # JUnit test cases for the project


## 📊 Database Schema

The Library Management System uses the following MySQL database schema:

### books Table

| Column             | Type        | Description                       |
|--------------------|-------------|-----------------------------------|
| isbn             | VARCHAR(20) | Primary key, unique identifier for each book |
| title            | VARCHAR(100)| Title of the book                 |
| author           | VARCHAR(100)| Author of the book                |
| publication_year | INT         | Year of publication               |
| available        | BOOLEAN     | Indicates if the book is available (default: TRUE) |

### users Table

| Column    | Type         | Description                          |
|-----------|--------------|--------------------------------------|
| username| VARCHAR(50)  | Primary key, unique identifier for each user |
| password| VARCHAR(50)  | User's password                      |
| role    | ENUM('admin', 'user') | User's role (admin or regular user) |

### borrowed_books Table

| Column    | Type        | Description                                      |
|-----------|-------------|--------------------------------------------------|
| username| VARCHAR(50) | Foreign key referencing users(username)        |
| isbn    | VARCHAR(20) | Foreign key referencing books(isbn)            |
| PRIMARY KEY (username, isbn) | Composite primary key to ensure each book can be borrowed by a user only once |

## 🚧 Getting Started

### Prerequisites

- *Java JDK 8+* – For compiling and running the project
- *Gradle* (optional) – For building and managing dependencies
- *JUnit* – For running tests
- *MySQL* – For database management

### Installation

1. *Clone the repository:*

   bash
   git clone https://github.com/priyanka270903/library_management_kata.git
   cd library_management_kata
   

2. *Set up the database:*

   - Create the database and tables using the provided SQL schema.

   sql
   CREATE DATABASE library_management;

   USE library_management;

   CREATE TABLE books (
       isbn VARCHAR(20) PRIMARY KEY,
       title VARCHAR(100),
       author VARCHAR(100),
       publication_year INT,
       available BOOLEAN DEFAULT TRUE
   );

   CREATE TABLE users (
       username VARCHAR(50) PRIMARY KEY,
       password VARCHAR(50),
       role ENUM('admin', 'user')
   );

   CREATE TABLE borrowed_books (
       username VARCHAR(50),
       isbn VARCHAR(20),
       FOREIGN KEY (username) REFERENCES users(username),
       FOREIGN KEY (isbn) REFERENCES books(isbn),
       PRIMARY KEY (username, isbn)
   );
   

3. *Build the project:*

   Using Gradle:

   bash
   gradle build
   

   Or, manually compile using Java:

   bash
   javac -d bin src/main/java/*.java
   

4. *Run the application:*

   bash
   java -cp bin LibrarySystem
   

5. *Run the tests:*

   Using Gradle:

   bash
   gradle test
   

   Or, using JUnit directly:

   bash
   java -cp "path-to-junit.jar:bin" org.junit.runner.JUnitCore LibraryTest
   

## 💡 Usage

- *Register a New User:*
  - Start the app and choose to register a new user.
  - Provide a username and password to create your account.

- *Login:*
  - Use your registered credentials to log in and start managing your library.

- *Manage Books:*
  - Add new books by entering details like ISBN, title, author, and year.
  - View available books, borrow them, and return them when done.

## 📬 Contact

Have questions or suggestions? Feel free to reach out:

- *Priyanka Dalwadi* - [dalwadipriyanka1727@gmail.com](mailto:dalwadipriyanka1727@gmail.com)
- *GitHub*: [https://github.com/priyanka270903](https://github.com/priyanka270903)

---
