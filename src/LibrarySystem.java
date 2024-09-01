import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private Library library;
    private Scanner scanner;
    private User loggedInUser;

    public LibrarySystem() {
        library = new Library();
        scanner = new Scanner(System.in);
        loggedInUser = null;
    }

    public void start() {
        while (true) {
            if (loggedInUser == null) {
                showMainMenu();
            } else {
                if (loggedInUser.isAdmin()) {
                    showAdminMenu();
                } else {
                    showUserMenu();
                }
            }
        }
    }

    private void showMainMenu() {
        System.out.println("Library Management System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                loginUser();
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void showAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("1. Add Book");
        System.out.println("2. View Available Books");
        System.out.println("3. Logout");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                viewAvailableBooks();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void showUserMenu() {
        System.out.println("User Menu");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. View Available Books");
        System.out.println("4. Logout");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                borrowBook();
                break;
            case 2:
                returnBook();
                break;
            case 3:
                viewAvailableBooks();
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void addBook() {
        System.out.println("Enter ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Enter Title:");
        String title = scanner.nextLine();
        System.out.println("Enter Author:");
        String author = scanner.nextLine();
        System.out.println("Enter Publication Year:");
        int year = getIntInput();

        Book book = new Book(isbn, title, author, year);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    private void registerUser() {
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        System.out.println("Are you an Admin or User? (Type 'Admin' or 'User'):");
        String role = scanner.nextLine();

        boolean isAdmin = role.equalsIgnoreCase("Admin");
        User newUser = new User(username, password, isAdmin);

        try {
            library.registerUser(newUser);
            System.out.println("User registered successfully.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loginUser() {
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        User user = library.loginUser(username, password);
        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void borrowBook() {
        viewAvailableBooks(); // Show available books before borrowing

        System.out.println("Enter ISBN of the book to borrow:");
        String isbn = scanner.nextLine();

        try {
            library.borrowBook(isbn, loggedInUser);
            System.out.println("Book borrowed successfully.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void returnBook() {
        System.out.println("Enter ISBN of the book to return:");
        String isbn = scanner.nextLine();

        try {
            library.returnBook(isbn);
            System.out.println("Book returned successfully.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewAvailableBooks() {
        // Retrieve available books
        List<Book> availableBooks = library.getAvailableBooks();

        // Check if there are any available books
        if (availableBooks.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        // Print table header
        System.out.printf("%-15s %-30s %-25s %-4s%n", "ISBN", "Title", "Author", "Year");
        System.out.println("---------------------------------------------------------------");

        // Print book details in a formatted manner
        for (Book book : availableBooks) {
            System.out.printf("%-15s %-30s %-25s %-4d%n", book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublicationYear());
        }
    }

    private void logout() {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
