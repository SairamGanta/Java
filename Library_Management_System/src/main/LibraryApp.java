package main;

import java.util.Scanner;

import DAO.LibraryManager;
import model.Book;

public class LibraryApp {
    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    manager.addBook(title, author);
                    break;
                case 2:
                    System.out.println("\nAvailable Books:");
                    for (Book book : manager.viewBooks()) {
                        System.out.println(book);
                    }
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    manager.borrowBook(userId, bookId);
                    break;
                case 4:
                    System.out.print("Enter Book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    manager.returnBook(returnBookId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
