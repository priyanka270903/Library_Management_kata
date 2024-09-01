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
    
}
