package com.laylasahara.main;

public class Borrow {
    private Borrower borrower;
    private Book book;
    private String date;
    private boolean isReturned;

    public Borrow(Borrower borrower, Book book, boolean isReturned) {
        this.borrower = borrower;
        this.book = book;
        this.isReturned = isReturned;
        this.date = java.time.Clock.systemUTC().instant().toString() + System.nanoTime();
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public String getDate() {
        return date;
    }

    public String getAccessType() {
        return borrower.getAccessType();
    }
}
