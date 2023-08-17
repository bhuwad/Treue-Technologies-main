import java.util.*;

// class "Book" start

class Book {
    int id;
    String title;
    String author;
    boolean isAvailable;
    double totalRatingPoints;
    int numRatings;
    String review;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.totalRatingPoints = 0.0;
        this.numRatings = 0;
        this.review = "";
    }

    public double getAverageRating() {
        if (numRatings == 0) {
            return 0.0;
        }
        return totalRatingPoints / numRatings;
    }
}

// class "Book" end
// class "Library" start

class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
        // Pre-stored book data
        books.add(new Book(1, "Book 1", "Author 1"));
        books.add(new Book(2, "Book 2", "Author 2"));
        books.add(new Book(3, "Book 3", "Author 3"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public void addBook(int id, String title, String author) {
        books.add(new Book(id, title, author));
    }


    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.id == id) {
                return book;
            }
        }
        return null; // Book not found
    }

    // method borrowBook
    public void borrowBook(int id) {
        Book book = getBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        if (!book.isAvailable) {
            System.out.println("Book with ID " + id + " is not available for borrowing.");
            return;
        }

        // Book is available, mark it as borrowed
        book.isAvailable = false;
        System.out.println("Book with ID " + id + " has been borrowed.");
    }

    // method returnBook
    public void returnBook(int id) {
        Book book = getBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        if (book.isAvailable) {
            System.out.println("Book with ID " + id + " is already available.");
            return;
        }

        // Book is returned, mark it as available
        book.isAvailable = true;
        System.out.println("Book with ID " + id + " has been returned.");
    }

    // method rateAndReviewBook
    public void rateAndReviewBook(int id, int rating, String review) {
        Book book = getBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Rating should be on a scale of 1 to 5.");
            return;
        }

        // Update the book's rating and review
        book.totalRatingPoints += rating;
        book.numRatings++;
        book.review = review;

        System.out.println("Book with ID " + id + " has been rated and reviewed.");
    }
    
}

// class "Library" end
// class "User" start

class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

// class "User" end
// class "LibraryApp" start

public class LibraryApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();
    private static User loggedInUser;

    // main method
    public static void main(String[] args) {
        showMainMenu();
    }

    // method borrowBook
    private static void borrowBooks() {
        System.out.print("Enter the book ID you want to borrow: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        library.borrowBook(id);
    }

    // method returnBook
    private static void returnBook() {
        System.out.print("Enter the book ID you want to return: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        library.returnBook(id);
    }

    // method rateAndReviewBook
    private static void rateAndReviewBook() {
        System.out.print("Enter the book ID you want to rate and review: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter the rating (1 to 5): ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter your review: ");
        String review = scanner.nextLine();
        library.rateAndReviewBook(id, rating, review);
    }

    // method showMainMenu
    private static void showMainMenu() {
        System.out.println("Welcome to the Library!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainMenu();
        }
    }

    // method register
    private static void register() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        loggedInUser = new User(username, password);
        System.out.println("Registration successful!");
        showBookSection();
    }

    // method login
    private static void login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check if the username and password match with a registered user and not Null
        if (loggedInUser != null && loggedInUser.username.equals(username) && loggedInUser.password.equals(password)) {
            System.out.println("Login successful!");
            showBookSection();
        } else {
            System.out.println("Invalid username or password. Please try again.");
            showMainMenu();
        }
    }

    // method showBookSection
    private static void showBookSection() {
        System.out.println("\nBook Section");
        System.out.println("1. Add Books");
        System.out.println("2. Show All Books");
        System.out.println("3. Show Available Books");
        System.out.println("4. Borrow Books");
        System.out.println("5. Return Book");
        System.out.println("6. Rate and Review Book");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addBooks();
                break;
            case 2:
                showAllBooks();
                break;
            case 3:
                showAvailableBooks();
                break;
            case 4:
                borrowBooks();
                break;
            case 5:
                returnBook();
                break;
            case 6:
                rateAndReviewBook();
                break;
            case 7:
                System.out.println("Thank you for using the Library!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        showBookSection(); // Show the book section menu again
    }

    // method addBooks
    private static void addBooks() {
        System.out.print("Enter the book ID you want to add: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        library.addBook(id, title, author);
    }

    // method showAllBooks
    private static void showAllBooks() {
        System.out.println("\nAll Books:");
        List<Book> allBooks = library.getAllBooks();
        displayBooks(allBooks);
    }

    // method showAvailableBooks
    private static void showAvailableBooks() {
        System.out.println("\nAvailable Books:");
        List<Book> availableBooks = library.getAvailableBooks();
        displayBooks(availableBooks);
    }

    // method displayBooks
    private static void displayBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.print("Id: " + book.id);
                System.out.print("\tTitle: " + book.title);
                System.out.print("\tAuthor: " + book.author);
                System.out.print("\tAvailable: " + (book.isAvailable ? "Yes" : "No"));
                System.out.println("\tAverage Rating: " + book.getAverageRating());
                System.out.println("Review: " + book.review);
                System.out.println();
            }
        }
    }
}

// class "LibraryApp" end