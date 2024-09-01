# 📚 Library Management System

## 🌟 Overview

Welcome to the *Library Management System*! This Java-based application helps manage your library with ease, offering features like user management, book management, and transaction handling. Developed using Test-Driven Development (TDD) for reliability.

## 🚀 Features

- *User Management:* Register and log in users with role-based access.
- *Book Management:* Add, view, borrow, and return books.
- *TDD:* Comprehensive JUnit test coverage ensures functionality.

## 🛠 Technical Details

### Database Connection

We use a MySQL database to store all data. Here's the connection setup:

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


### Logic Flow

- *User Registration/Login:* 
  - Secure user registration and login with role-based access (admin/user).
- *Book Management:* 
  - Admins can add books; users can borrow and return them.
  - Database updates book availability and transaction records.

### Project Structure


LibraryManagementSystem/
├── src/
│   ├── main/java/
│   │   ├── Book.java
│   │   ├── Library.java
│   │   ├── LibrarySystem.java
│   │   ├── User.java
│   │   └── DatabaseHelper.java
│   └── test/java/
│       └── LibraryTest.java
└── build.gradle


### Database Schema

- **books Table:**
  - Stores book details like ISBN, title, and availability.
- **users Table:**
  - Manages user credentials and roles.
- **borrowed_books Table:**
  - Tracks book loans linked to users.

## 🚧 Setup

### Prerequisites

- *Java JDK 8+*
- *MySQL*
- *JUnit* (for testing)

### Installation

1. *Clone the Repository:*

   bash
   git clone https://github.com/priyanka270903/library_management_kata.git
   cd library_management_kata
   

2. *Set Up Database:*

   Create and populate the MySQL database:

   sql
   CREATE DATABASE library_management;
   -- SQL schema for tables is provided above.
   

3. *Build & Run:*

   - Using Gradle: gradle build
   - Manually: javac -d bin src/main/java/*.java && java -cp bin LibrarySystem

4. *Run Tests:*

   bash
   gradle test
   

## 🤝 Contributing

1. Fork the repo.
2. Create a new branch.
3. Make your changes.
4. Push and open a pull request.

## 📬 Contact

*Priyanka J Dalwadi* - [dalwadipriyanka1727@gmail.com](mailto:dalwadipriyanka1727@gmail.com)
