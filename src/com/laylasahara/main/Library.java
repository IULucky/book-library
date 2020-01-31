package com.laylasahara.main;

import java.util.*;

public class Library {
    private String name;
    private List<Book> books;
    private List<Borrow> borrowHistory;
    private List<Borrower> users;


    public Library(String name) {
        this.name = name;
        this.borrowHistory = new ArrayList<>();
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Librarian login check
    public Borrower checkLibrarian(String email) {
        if(!email.equalsIgnoreCase("bash@terminal.com")) {
            return null;
        }

        return checkUser(email);
    }

    public Borrower checkUser(String email) {
        ListIterator<Borrower> userListIterator = users.listIterator();
        Borrower user;
        while (userListIterator.hasNext()) {
            user = userListIterator.next();
            if(email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public void registerUser(String name,String email, int staffId) {
        Borrower user = new Staff(name, email, staffId);
        this.users.add(user);
    }

    public void registerUser(String name,String email, int regNo,  boolean isSenior) {
        Borrower user;
        if(isSenior) {
            user = new Senior(name, email, regNo);
        } else {
            user = new Junior(name, email, regNo);
        }
        this.users.add(user);
    }

    // Add book to library collections
    public boolean addBook(String title, String author, int initialCopies) {
        if(checkBook(title) != null) return false;

        books.add(new Book(title, author, initialCopies));
        return true;
    }

    // Remove book from library collections
    public boolean removeBook(String title) {
        Book book = checkBook(title);

        if(book == null) return false;

        this.books.remove(book);
        PriorityQueue<Borrow> waitList = book.getWaitList();
        while(waitList.size() != 0) {
            waitList.poll().getBorrower().removeFromQueueBooks(book);
        }

        return true;
    }

    public int borrow(Borrower user, String title) {

        Book book = checkBook(title);
        // Book doesn't exist
        if(book == null) return -1;

        // Borrow book to user
        int isBorrowed = book.borrowBook(user);

        // Book has already been borrowed. User can't borrow the same book twice
        if(isBorrowed == -3) return -2;

        // No copies of the book left. User queued pending return of copies
        if(isBorrowed == 1) {
            user.addToQueueBooks(book);
            return 1;
        }

        // User has reached max borrow limit
        if(isBorrowed == -1) return 2;
        if(isBorrowed == -2) return -3;

        // At this point, book has been borrowed.
        // Add book to user borrowed books and library borrow history
        user.borrowBook(book);
        this.borrowHistory.add(new Borrow(user, book, false));
        return 0;
    }

    public void returnBook(Borrower user, Book book) {
        book.returnBook(user);
        user.returnBook(book);
    }

    private Book checkBook(String title) {
        ListIterator<Book> bookListIterator = books.listIterator();
        Book book;
        while (bookListIterator.hasNext()) {
            book = bookListIterator.next();
            if(title.equals(book.getTitle())) {
                return book;
            }
        }
        return null;
    }

    private Borrower getUser(String id) {
        ListIterator<Borrower> userListIterator = users.listIterator();
        Borrower user;
        while (userListIterator.hasNext()) {
            user = userListIterator.next();
            if(id.equals(user.getLibraryId())) {
                return user;
            }
        }
        return null;
    }

    public List<Book> getBooks() {
        return books;
    }
}
