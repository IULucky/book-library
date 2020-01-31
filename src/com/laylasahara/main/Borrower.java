package com.laylasahara.main;

import java.util.ArrayList;
import java.util.List;

public abstract class Borrower {
    private static int idCounter = 0;
    private int maxBorrowAtATime = 5;
    private String name;
    private String libraryId;
    private String email;
    private String accessType;
    private List<Book> borrowedBooks;
    private List<Book> booksInQueue;

    public Borrower(String name, String email, String accessType) {
        this.name = name;
        this.email = email;
        this.accessType = accessType;
        this.libraryId = accessType + String.format("%03d", ++idCounter);
        this.borrowedBooks = new ArrayList<>();
        this.booksInQueue = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        this.borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        this.borrowedBooks.remove(book);
    }

    public void addToQueueBooks(Book book) {
        this.booksInQueue.add(book);
    }

    public void removeFromQueueBooks(Book book) {
        this.booksInQueue.remove(book);
    }

    public boolean withinBorrowLimit() {
        return borrowedBooks.size() < maxBorrowAtATime;
    }

    public String getName() {
        return name;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<Book> getBooksInQueue() {
        return booksInQueue;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessType() {
        return accessType;
    }
}
