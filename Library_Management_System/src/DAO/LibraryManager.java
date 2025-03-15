package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class LibraryManager {
    public void addBook(String title, String author) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Books (title, author) VALUES (?, ?)")) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> viewBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Books")) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getBoolean("available")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void borrowBook(int userId, int bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement checkStmt = conn.prepareStatement("SELECT available FROM Books WHERE book_id = ?");
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getBoolean("available")) {
                PreparedStatement borrowStmt = conn.prepareStatement("INSERT INTO Transactions (user_id, book_id) VALUES (?, ?)");
                borrowStmt.setInt(1, userId);
                borrowStmt.setInt(2, bookId);
                borrowStmt.executeUpdate();

                PreparedStatement updateStmt = conn.prepareStatement("UPDATE Books SET available = FALSE WHERE book_id = ?");
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                conn.commit();
                System.out.println("Book borrowed successfully!");
            } else {
                System.out.println("Book is not available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE Books SET available = TRUE WHERE book_id = ?");
            updateStmt.setInt(1, bookId);
            updateStmt.executeUpdate();

            PreparedStatement returnStmt = conn.prepareStatement("UPDATE Transactions SET return_date = NOW() WHERE book_id = ? AND return_date IS NULL");
            returnStmt.setInt(1, bookId);
            returnStmt.executeUpdate();

            System.out.println("Book returned successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
