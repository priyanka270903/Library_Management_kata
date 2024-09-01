/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
import java.sql.*;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author priya
 */
public class LibraryTest {
    
      private Library library;

    @Before
    public void setUp() {
        library = new Library();
        // Clean up database before each test
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM borrowed_books");
            stmt.execute("DELETE FROM books");
            stmt.execute("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddBook() {
        Book book = new Book("12345", "Test Book", "Test Author", 2023);
        library.addBook(book);
        
        List<Book> availableBooks = library.getAvailableBooks();
        assertEquals(1, availableBooks.size());
        assertEquals(book.getIsbn(), availableBooks.get(0).getIsbn());
    }
 @Test
    public void testRegisterUser() {
        User user = new User("john", "password123", false);
        library.registerUser(user);
        
        // Verify user is registered
        User retrievedUser = library.loginUser("john", "password123");
        assertNotNull(retrievedUser);
        assertEquals("john", retrievedUser.getUsername());
        assertFalse(retrievedUser.isAdmin());
    }

    @Test
    public void testLoginUser() {
        User user = new User("john", "password123", false);
        library.registerUser(user);
        
        User loggedInUser = library.loginUser("john", "password123");
        assertNotNull(loggedInUser);
        assertTrue("User should not be admin", !loggedInUser.isAdmin());
    }
    
    
    @Test
    public void testBorrowBook() {
        User user = new User("john", "password123", false);
        library.registerUser(user);
        Book book = new Book("12345", "Test Book", "Test Author", 2023);
        library.addBook(book);
        
        library.borrowBook("12345", user);
        
        List<Book> availableBooks = library.getAvailableBooks();
        assertTrue(availableBooks.isEmpty());
        
        // Verify book is borrowed
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowed_books WHERE username = ? AND isbn = ?")) {
            stmt.setString(1, "john");
            stmt.setString(2, "12345");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReturnBook() {
        User user = new User("john", "password123", false);
        library.registerUser(user);
        Book book = new Book("12345", "Test Book", "Test Author", 2023);
        library.addBook(book);
        library.borrowBook("12345", user);
        library.returnBook("12345");
        
        List<Book> availableBooks = library.getAvailableBooks();
        assertEquals(1, availableBooks.size());
        assertEquals("12345", availableBooks.get(0).getIsbn());
        
        // Verify book is returned
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowed_books WHERE isbn = ?")) {
            stmt.setString(1, "12345");
            ResultSet rs = stmt.executeQuery();
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
@Test
    public void testBorrowBookNotAvailable() {
        User user = new User("john", "password123", false);
        library.registerUser(user);
        Book book = new Book("12345", "Test Book", "Test Author", 2023);
        library.addBook(book);
        
        library.borrowBook("12345", user);
        
        try {
            library.borrowBook("12345", user);
            fail("Exception should be thrown when borrowing an already borrowed book.");
        } catch (Exception e) {
            assertEquals("Book is already borrowed.", e.getMessage());
        }
    }
 @Test
    public void testRegisterUserWithExistingUsername() {
        User user1 = new User("john", "password123", false);
        library.registerUser(user1);
        User user2 = new User("john", "newpassword", false);
        
        try {
            library.registerUser(user2);
            fail("Exception should be thrown when registering a user with an existing username.");
        } catch (Exception e) {
            assertEquals("Username already exists.", e.getMessage());
        }
    }
    
      @Test
    public void testGetAvailableBooksWhenEmpty() {
        List<Book> availableBooks = library.getAvailableBooks();
        assertTrue("Book list should be empty", availableBooks.isEmpty());
    }
    @Test
    public void testDeleteBook() throws SQLException {
        Book book = new Book("12345", "Test Book", "Test Author", 2023);
        library.addBook(book);
        library.deleteBook("12345");
        
        List<Book> availableBooks = library.getAvailableBooks();
        assertTrue("Book list should be empty after deletion", availableBooks.isEmpty());
    }
    
    @Test
    public void testDeleteBookThatDoesNotExist() {
        // Try to delete a book that doesn't exist
        try {
            library.deleteBook("99999");
            // Check that no exception was thrown
            // Optionally, you can verify that the state of the database remains unchanged
            List<Book> availableBooks = library.getAvailableBooks();
            assertTrue("Book list should still be empty", availableBooks.isEmpty());
        } catch (Exception e) {
            fail("Exception should not be thrown when deleting a non-existing book.");
        }
    }
}
