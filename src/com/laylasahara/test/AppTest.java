package com.laylasahara.test;

import com.laylasahara.main.*;

import java.util.Objects;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private Library library;
    private Book book;
    private Borrower user;


    @org.junit.jupiter.api.Test
    void borrowBook() {
        book = new Book("Brotherhood", "Ahmad Faruk", 1);
        user = new Staff("Akram Jamal", "akram@jamal.com", 102);
        int response1 = book.borrowBook(user);
        int response2 = book.borrowBook(user);
        assertEquals(0, response1, "Should return 0, being the first person to borrow book");
        assertEquals(-3, response2, "Trying to borrow already borrowed book should return -3");
    }

    @org.junit.jupiter.api.Test
    void returnBook() {
        user = new Junior("Micah Saleh", "m@universe.com", 23);
        book = new Book("Moms", "Layla Sahara", 3);
        book.borrowBook(user);
        assertTrue(book.returnBook(user), "Book return successfully");
    }

    @org.junit.jupiter.api.Test
    void getTitle() {
        String title = "Why You Act The Way You Do";
        book = new Book(title, "Tim Lahaye", 10);
        assertEquals(title, book.getTitle(), "Book title");
    }

    @org.junit.jupiter.api.Test
    void getAuthor() {
        String author = "Bash Terminal";
        book = new Book("Coder Blunt", author, 4);
        assertEquals(author, book.getAuthor(), "Book author");
    }

    @org.junit.jupiter.api.Test
    void getTotalCopies() {
        book = new Book("The Art Of Not Giving A F*ck", "Uncle Suru", 2);
        assertEquals(2, book.getTotalCopies());
    }

    @org.junit.jupiter.api.Test
    void getCopiesLeft() {
        user = new Senior("Salma Mahmud", "salma@terminal.com", 16);
        book = new Book("The Lambda Chaos", "Shuaib Bashir", 3);
        book.borrowBook(user);
        assertEquals(2, book.getCopiesLeft(), "Given 1 total copy and 1 borrow, 0 copy should be left");
    }

    @org.junit.jupiter.api.Test
    void checkUser() {
        library = new Library("Jodava");
        String email = "binwaleed@kenchi.com";
        library.registerUser("Khaleed Bin Walled", email, 2);
        assertNotNull(library.checkUser(email));
        assertNull(library.checkUser("undefined@unknown.com"));

    }

    @org.junit.jupiter.api.Test
    void addBook() {
        library = new Library("Abuja Central Area");
        boolean isAdded = library.addBook("Uncertainty Principle", "Schrodinger Homeless", 34);
        assertTrue(isAdded);
    }

    @org.junit.jupiter.api.Test
    void removeBook() {
        library = new Library("Eko Atlantic");
        String title = "Java Jaundice";
        library.addBook(title, "Chibueze & Ibrahim Decagon", 9);
        assertTrue(library.removeBook(title));
        assertFalse(library.removeBook("The Tale"));
    }

    @org.junit.jupiter.api.Test
    void borrow() {
        library = new Library("Bash Catalog");
        user = new Senior("Abubakar Sabo", "asabo@gmail.com", 32);
        String title = "Upper Sweet Side";
        library.addBook(title, "Shuaib M. Bashir", 90);
        assertEquals(0, library.borrow(user, title), "User successfully borrowed a book");
    }

    @org.junit.jupiter.api.Test
    void getBooks() {
        library = new Library(("Oxford"));
        library.addBook("Kenchi Family Dynasty", "Shuaib Bashir II", 53);
        library.addBook("The Prince", "Napoleon Makievelli", 4);
        library.addBook("The Art of War", "Sun Tzu", 5);
        library.addBook("The Fault in Our Stars", "John Green", 4);
        assertEquals(4, library.getBooks().size());
    }

    @org.junit.jupiter.api.Test
    void priorityQueue() {
        book = new Book("Sunflower", "Swae Lee", 1);

        Borrower senior1 = new Senior("Abubakar Sabo", "asabo@gmail.com", 32);
        Borrower junior1 = new Junior("Victor Onu", "asabo@gmail.com", 3);
        Borrower staff1 = new Staff("Odudu Abasi", "abasi@gmail.com", 10);
        Borrower junior2 = new Junior("Ruqayyah Aliyu", "rukky@gmail.com", 1);
        Borrower senior2 = new Senior("Linda Ilonze", "linda@gmail.com", 5);
        Borrower staff2 = new Staff("Promila Ojobo", "prom@gmail.com", 9);
        Borrower senior3 = new Senior("Everest Godwin", "everest@gmail.com", 12);
        Borrower staff3 = new Staff("Michael Williams", "mike@gmail.com", 2);

        book.borrowBook(senior1);
        book.borrowBook(junior1);
        book.borrowBook(staff1);
        book.borrowBook(junior2);
        book.borrowBook(senior2);
        book.borrowBook(staff2);
        book.borrowBook(senior3);
        book.borrowBook(staff3);

        PriorityQueue<Borrow> queue = book.getWaitList();

        Borrower nextInLineUser1 = Objects.requireNonNull(queue.poll()).getBorrower();
        Borrower nextInLineUser2 = Objects.requireNonNull(queue.poll()).getBorrower();
        Borrower nextInLineUser3 = Objects.requireNonNull(queue.poll()).getBorrower();
        Borrower nextInLineUser4 = Objects.requireNonNull(queue.poll()).getBorrower();
        Borrower nextInLineUser5 = Objects.requireNonNull(queue.poll()).getBorrower();
        Borrower nextInLineUser6 = Objects.requireNonNull(queue.poll()).getBorrower();
        Borrower nextInLineUser7 = Objects.requireNonNull(queue.poll()).getBorrower();

        assertEquals(staff1, nextInLineUser1, "Odudu Abasi");
        assertEquals(staff2, nextInLineUser2, "Promila Ojobo");
        assertEquals(staff3, nextInLineUser3, "Michael Williams");
        assertEquals(senior2, nextInLineUser4, "Linda Ilonze");
        assertEquals(senior3, nextInLineUser5, "Everest Godwin");
        assertEquals(junior1, nextInLineUser6, "Victor Onu");
        assertEquals(junior2, nextInLineUser7, "Ruqayyah Aliyu");
    }
}