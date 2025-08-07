package org.example.library.Model;

import org.example.library.Entites.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
