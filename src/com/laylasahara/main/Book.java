package com.laylasahara.main;

import java.util.*;

public class Book {
    private String title;
    private String author;
    private final int totalCopies;
    private int copiesLeft;
    private List<Borrower> borrowers;
    Comparator<Borrow> priority = Comparator.comparing(Borrow::getAccessType).thenComparing(Borrow::getDate);
    PriorityQueue<Borrow> waitList;

    public Book(String title, String author, int InitialCopies) {
        this.title = title;
        this.author = author;
        this.totalCopies = InitialCopies;
        this.copiesLeft = this.totalCopies;
        this.waitList = new PriorityQueue<>(priority);
        this.borrowers = new ArrayList<>();
    }

    public int borrowBook(Borrower user) {
        if(checkBorrower(user)) return -3;

        if(this.copiesLeft > 0) {
            if(!user.withinBorrowLimit()) {
                return -1;
            }
            this.copiesLeft--;
            this.borrowers.add(user);
            return 0;
        }

        for (Borrow borrow : waitList) {
            if (borrow.getBorrower().equals(user)) {
                return -2;
            }
        }

        this.waitList.add(new Borrow(user, this, false));
        return 1;
    }

    public boolean returnBook(Borrower user) {
        this.borrowers.remove(user);
        this.copiesLeft++;
        if(this.waitList.size() > 0) {
            Borrower nextUser = this.waitList.poll().getBorrower();
            nextUser.removeFromQueueBooks(this);
            borrowBook(nextUser);
            nextUser.borrowBook(this);
        }
        return true;
    }

    private boolean checkBorrower(Borrower borrower) {
        return this.borrowers.contains(borrower);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getCopiesLeft() {
        return copiesLeft;
    }

    public PriorityQueue<Borrow> getWaitList() {
        return new PriorityQueue<>(waitList);
    }

    @Override
    public String toString() {
        return "'" + title + "' by " +  author;
    }
}
