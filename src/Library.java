import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    public void addBook(Book book) {
        String query = "INSERT INTO books (isbn, title, author, publication_year) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
           PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getPublicationYear());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE available = TRUE";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("publication_year");
                books.add(new Book(isbn, title, author, year));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void registerUser(User user) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Check if the user already exists
            checkStmt.setString(1, user.getUsername());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("Username already exists.");
                }
            }

            // Register the new user
            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, user.getPassword());
            insertStmt.setString(3, user.isAdmin() ? "admin" : "user");
            insertStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    boolean isAdmin = rs.getString("role").equals("admin");
                    return new User(username, password, isAdmin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void borrowBook(String isbn, User user) {
        String checkQuery = "SELECT * FROM books WHERE isbn = ? AND available = TRUE";
        String insertQuery = "INSERT INTO borrowed_books (username, isbn) VALUES (?, ?)";
        String updateQuery = "UPDATE books SET available = FALSE WHERE isbn = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            checkStmt.setString(1, isbn);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    insertStmt.setString(1, user.getUsername());
                    insertStmt.setString(2, isbn);
                    insertStmt.executeUpdate();

                    updateStmt.setString(1, isbn);
                    updateStmt.executeUpdate();
                } else {
                    throw new RuntimeException("Book is not available.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(String isbn) {
        String deleteQuery = "DELETE FROM borrowed_books WHERE isbn = ?";
        String updateQuery = "UPDATE books SET available = TRUE WHERE isbn = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            deleteStmt.setString(1, isbn);
            deleteStmt.executeUpdate();

            updateStmt.setString(1, isbn);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String isbn) throws SQLException {
        String sql = "DELETE FROM books WHERE isbn = ?";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                // Optionally handle the case where no book was found
                System.out.println("No book found with ISBN: " + isbn);
            }
        }
    }
}
