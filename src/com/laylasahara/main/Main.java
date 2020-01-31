package com.laylasahara.main;
import java.util.*;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Library library = new Library("ABU Zaria");

    public static void main(String[] args) {

        // Add mock books and users
        mockData();

        homePageInstruction();
    }

    private static void homePageInstruction() {
        boolean quit = false;
        System.out.println("Welcome to the library");
        while (!quit) {
            System.out.println("Press:::\n" +
                    "1 - to login\n" +
                    "2 - to register\n" +
                    "3 - to login as Librarian\n" +
                    "0 - to quit");

            nextInt();
            int number = scanner.nextInt();
            scanner.nextLine();

            switch (number) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    librarianLogin();
                    break;
                case 0:
                    quit = true;
                    System.out.println("You've successfully quit the application.");
            }
        }
    }

    private static void librarianLogin() {
        System.out.print("Librarian Login: ");
        String email = scanner.nextLine();

        Borrower user = library.checkLibrarian(email);
        if (user == null) {
            System.out.println("\nIncorrect librarian login!!!.");
            backButton();
            return;
        }

        System.out.println("\n[][ Welcome Librarian ][]\n");

        boolean quit = false;
        while (!quit) {
            System.out.println("Press:::\n" +
                    "1 - to add a book\n" +
                    "2 - to remove a book\n" +
                    "3 - to view books with waitlists\n" +
                    "4 - to view library collections\n" +
                    "0 - to quit");

            nextInt();
            int number = scanner.nextInt();
            scanner.nextLine();

            switch (number) {
                case 1:
                    System.out.print("\nBook title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("How many copies: ");
                    nextInt();
                    int copies = scanner.nextInt();
                    scanner.nextLine();

                    boolean isAdded = library.addBook(title, author, copies);
                    if(!isAdded) {
                        System.out.printf("'%s' already exists in the library collection. Cannot readd.%n", title);

                    } else {
                        System.out.printf("'%s' added to the library collection.%n", title);
                    }
                    backButton();
                    break;

                case 2:
                    System.out.print("\nBook title: ");
                    String titleToRemove = scanner.nextLine();

                    if(library.removeBook(titleToRemove)) {
                        System.out.printf("%n'%s' removed from library collections successfully.%n", titleToRemove);
                    } else {
                        System.out.printf("%n'%s' was not found in the library collections.%n", titleToRemove);
                    }
                    backButton();
                    break;

                case 3:
                    List<Book> waitListedBook = new ArrayList<>();
                    List<Book> allBooks = library.getBooks();
                    boolean exit = false;
                    while (!exit) {
                        if(!waitListedBook.isEmpty()) {
                            waitListedBook.clear();
                        }

                        ListIterator<Book> bookListIterator = allBooks.listIterator();
                        while (bookListIterator.hasNext()) {
                            Book book = bookListIterator.next();
                            if (book.getWaitList().size() > 0) {
                                waitListedBook.add(book);
                            }
                        }
                        if(waitListedBook.size() > 0) {
                            System.out.println("\n*Select a book's number to view its waitlist*");
                            System.out.println("============================");
                            System.out.println("Books people are waiting for");
                            System.out.println("============================");
                        }else {
                            System.out.println("============================");
                            System.out.println("No book with waitlist");
                            System.out.println("============================");
                        }

                        bookIterator(waitListedBook);
                        System.out.println("___________________________");
                        System.out.println("0 - to go back");
                        while (true) {
                            nextInt();
                            int choice = scanner.nextInt();
                            scanner.nextLine();

                            if (choice == 0) {
                                exit = true;
                                break;
                            }

                            if (choice > 0 && choice <= waitListedBook.size()) {
                                Book book = waitListedBook.get(choice - 1);
                                PriorityQueue<Borrow> waitList = book.getWaitList();

                                int count = 0;
                                Borrow borrow;

                                boolean quitWaitList = false;
                                while(!quitWaitList) {
                                    System.out.println("============================");
                                    System.out.printf("'%s' Wait list%n", book.getTitle());
                                    System.out.println("============================");
                                    while (waitList.size() != 0) {
                                        count++;
                                        borrow = waitList.poll();
                                        System.out.printf("%d. %s%n", count, borrow.getBorrower().getName());
                                    }

                                    backButton();
                                    quitWaitList = true;
                                }
                                
                                break;
                            }
                        }
                    }
                    break;

                case 4:
                    printLibraryCollections();
                    break;

                case 0:
                    quit = true;
                    break;
            }
        }
    }

    private static void login() {
        System.out.println("Provide your email address to login");
        String email = scanner.nextLine();

        Borrower user = library.checkUser(email);
        if (user == null) {
            System.out.println("Email address provided is not registered.\n");
            return;
        }

        loggedIn(user);
    }

    private static void register() {
        System.out.printf("Email address: ");
        String email = scanner.nextLine();

        Borrower user = library.checkUser(email);
        if (user != null) {
            System.out.println("Email provided is already registered as a user.\n");
            return;
        }

        System.out.println("Register as:::\n" +
                "1 - Staff\n" +
                "2 - Senior student\n" +
                "3 - Junior student");

        nextInt();
        int number = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Full name: ");
        String name = scanner.nextLine();

        System.out.print("Institution ID Number: ");
        nextInt();
        int id = scanner.nextInt();
        scanner.nextLine();

        switch (number) {
            case 1:
                library.registerUser(name, email, id);
                break;
            case 2:
                library.registerUser(name, email, id, true);
                break;
            case 3:
                library.registerUser(name, email, id, false);
                break;
        }

        System.out.println("\nYou're are registered successfully\n");
    }


    private static void loggedIn(Borrower user) {
        System.out.println("\nLogged in successfully\n");

        boolean quit = false;
        System.out.println("Welcome to the library");
        while (!quit) {
            System.out.println("Press:::\n" +
                    "1 - to borrow a book\n" +
                    "2 - to view/return borrowed books\n" +
                    "3 - to view waitlists you are on\n" +
                    "4 - to check library collections\n" +
                    "0 - to quit\n");

            nextInt();
            int number = scanner.nextInt();
            scanner.nextLine();

            switch (number) {
                case 1:
                    System.out.print("\nProvide book title: ");

                    String title = scanner.nextLine();
                    int val = library.borrow(user, title);

                    if(val == -1) System.out.println("'" + title + "' doesn't exist");
                    else if(val == -2) System.out.println("You've already borrowed '" + title + "'");
                    else if(val == -3) System.out.println("You're already on '" + title + "' waitlist.");
                    else if(val == 0) System.out.println("You've borrowed '" + title + "' successfully.");
                    else if(val == 1) System.out.println("All copies have been borrowed. You've been added to the wait list.");
                    else if(val == 2) System.out.println("You've reached your maximum borrow limit. Try return some books.");
                    backButton();
                    break;

                case 2:
                    List<Book> borrowedBooks = user.getBorrowedBooks();
                    if (borrowedBooks.isEmpty()) {
                        System.out.println("You are yet to borrow a book");
                        backButton();
                    } else {
                        System.out.println("\n***Select a book's number to return it***");
                        System.out.println("==========================");
                        System.out.println("Borrowed Books");
                        System.out.println("==========================");

                        bookIterator(borrowedBooks);
                        System.out.println("___________________________");
                        System.out.println("0 - to go back");
                        while (true) {
                            nextInt();
                            int choice = scanner.nextInt();
                            scanner.nextLine();

                            if(choice == 0) {
                                break;
                            }

                            if(choice > 0 && choice <= borrowedBooks.size()) {
                                Book book = borrowedBooks.get(choice - 1);
                                library.returnBook(user, book);
                                System.out.printf("%n'%s' has been returned successfully%n", book.getTitle());
                                backButton();
                                break;
                            }

                        }
                    }
                    break;

                case 3:
                    List<Book> booksInQueue = user.getBooksInQueue();
                    if (booksInQueue.isEmpty()) {
                        System.out.println("You are not on any book's waitlist");
                    } else {
                        System.out.println("==========================");
                        System.out.println("Waitlisted Books");
                        System.out.println("==========================");

                        bookIterator(booksInQueue);
                    }

                    backButton();
                    break;

                case 4:
                    printLibraryCollections();
                    break;

                case 0:
                    quit = true;
                    break;
            }
        }
    }

    private static void printLibraryCollections() {
        List<Book> libraryBooks = library.getBooks();
        if (libraryBooks.isEmpty()) {
            System.out.println("\nWe currently have no books in the library\n");
        } else {
            System.out.println("==========================");
            System.out.println("Library Books");
            System.out.println("==========================");

            bookIterator(libraryBooks);
        }

        backButton();
    }

    private static void bookIterator(List<Book> books) {
        ListIterator<Book> bookListIterator = books.listIterator();
        int count = 0;
        while (bookListIterator.hasNext()) {
            count++;
            System.out.printf("%d. %s%n", count, bookListIterator.next());
        }
    }

    private static void nextInt() {
        while(!scanner.hasNextInt()) {
            scanner.nextLine();
        }
    }

    private static void backButton() {
        boolean quit = false;

        while (!quit) {
            System.out.println("___________________________");
            System.out.println("0 - to go back");
            nextInt();
            int number = scanner.nextInt();
            scanner.nextLine();

            if(number == 0) {
                quit = true;
            }
        }
    }

    private static void mockData() {
        library.addBook("Alchemy", "Chichi", 2);
        library.addBook("Brotherhood", "Ahmad Faruk", 1);
        library.addBook("Mom", "Bash Terminal", 1);
        library.addBook("The Tale", "Twitter", 2);
        library.addBook("Kenchi Family", "Sadiq Bashir", 3);
        library.addBook("Silicon Valley", "Mike", 4);
        library.addBook("Decagon", "Louis V", 4);

        library.registerUser("Odudu", "o@jide.com", 16, false);
        library.registerUser("Abbas", "o@a.com", 28, true);
        library.registerUser("Shuaib Bashir", "bash@terminal.com", 101);
        library.registerUser("Okpas", "ee@a.com", 21, false);
        library.registerUser("Loveth", "fa@a.com", 23);
        library.registerUser("Blessing", "d@a.com", 10);
        library.registerUser("Aminah", "asoebi@terminal.com", 55, false);
    }
}


