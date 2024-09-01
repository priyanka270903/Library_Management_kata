/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

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
    
    public LibraryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addBook method, of class Library.
     */
    @Test
    public void testAddBook() {
        System.out.println("addBook");
        Book book = null;
        Library instance = new Library();
        instance.addBook(book);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableBooks method, of class Library.
     */
    @Test
    public void testGetAvailableBooks() {
        System.out.println("getAvailableBooks");
        Library instance = new Library();
        List<Book> expResult = null;
        List<Book> result = instance.getAvailableBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class Library.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        User user = null;
        Library instance = new Library();
        instance.registerUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class Library.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String username = "";
        String password = "";
        Library instance = new Library();
        User expResult = null;
        User result = instance.loginUser(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of borrowBook method, of class Library.
     */
    @Test
    public void testBorrowBook() {
        System.out.println("borrowBook");
        String isbn = "";
        User user = null;
        Library instance = new Library();
        instance.borrowBook(isbn, user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnBook method, of class Library.
     */
    @Test
    public void testReturnBook() {
        System.out.println("returnBook");
        String isbn = "";
        Library instance = new Library();
        instance.returnBook(isbn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteBook method, of class Library.
     */
    @Test
    public void testDeleteBook() throws Exception {
        System.out.println("deleteBook");
        String isbn = "";
        Library instance = new Library();
        instance.deleteBook(isbn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
