package org.example.library.Model;

import org.example.library.Entites.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookModel extends BaseModel{
    public List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT * FROM book";
        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();
        List<Book> listBook = new ArrayList<>();

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String genre = resultSet.getString("genre");
            int price = resultSet.getInt("price");

            Book book = new Book(id, name, genre, price);
            listBook.add(book);
        }

        return listBook;
    }
    public Book addBook(Book book) throws SQLException {
        String sql = "INSERT INTO book (name, genre, price) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, book.getName());
        ps.setString(2, book.getGenre());
        ps.setInt(3, book.getPrice());
        ps.executeUpdate();


        return book;
    }
    public void deleteBookById(int id) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void addRent(int userId, int bookId) throws SQLException {
        String sql = "INSERT INTO user_book (id_user, id_book) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, bookId);
        ps.executeUpdate();
    }
    public List<Book> getRentBook(int userId) throws SQLException {
        String sql = """
                    SELECT b.id, b.name, b.genre, b.price
                    FROM user_book ub
                    JOIN book b ON ub.id_book = b.id
                    WHERE ub.id_user = ?
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        List<Book> listBook = new ArrayList<>();

        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String genre = rs.getString("genre");
            int price = rs.getInt("price");

            Book book = new Book(id, name, genre, price);

            listBook.add(book);
        }
        return listBook;
    }
    public void returnBook(int userId, int bookId) throws SQLException {
        String sql = "DELETE FROM user_book WHERE id_user = ? AND id_book = ?";
             PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

    }


}
