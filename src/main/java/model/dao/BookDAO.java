package model.dao;

import model.bean.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    public ArrayList<Book> getAllBooks() throws SQLException {
        ArrayList<Book> listBook = new ArrayList<>();
        Connection conn = ConnectionToDB.ConnectToMySQL();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from book left join category on book.CategoryId = category.Id");
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getString("Id"));
            book.setName(rs.getString("Name"));
            book.setAuthor(rs.getString("Author"));
            book.setQuantity(rs.getInt("Quantity"));
            book.setCategoryId(rs.getInt("CategoryId"));
            if (rs.getString("category.Name") == null) {
                book.setCategoryName("");
            } else {
                book.setCategoryName(rs.getString("category.Name"));
            }
            listBook.add(book);
        }
        conn.close();
        return listBook;
    }

    public void addBook(Book book) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement stmt = conn.prepareStatement("insert into book values(?,?,?,?,?)");
        stmt.setString(1, book.getId());
        stmt.setString(2, book.getName());
        stmt.setString(3, book.getAuthor());
        stmt.setInt(4, book.getQuantity());
        stmt.setInt(5, book.getCategoryId());
        stmt.executeUpdate();
        conn.close();
    }

    public void updateBook(Book book) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement stmt = conn.prepareStatement("update book set Name=?,Author=?,Quantity=?,CategoryId=? where Id=?");
        stmt.setString(1, book.getName());
        stmt.setString(2, book.getAuthor());
        stmt.setInt(3, book.getQuantity());
        stmt.setInt(4, book.getCategoryId());
        stmt.setString(5, book.getId());
        stmt.executeUpdate();
        conn.close();
    }

    public void deleteBook(String id) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement stmt = conn.prepareStatement("delete from libraryloan where BookId=?");
        stmt.setString(1, id);
        stmt.executeUpdate();
        stmt = conn.prepareStatement("delete from book where Id=?");
        stmt.setString(1, id);
        stmt.executeUpdate();
        conn.close();
    }

    public boolean isIdExist(String id) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement stmt = conn.prepareStatement("select * from book where Id=?");
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        boolean result = rs.next();
        conn.close();
        return result;
    }

    public Book getBookById(String id) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement stmt = conn.prepareStatement("select * from book left join category on book.CategoryId = category.Id where book.Id=?");
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        Book book = new Book();
        while (rs.next()) {
            book.setId(rs.getString("Id"));
            book.setName(rs.getString("Name"));
            book.setAuthor(rs.getString("Author"));
            book.setQuantity(rs.getInt("Quantity"));
            book.setCategoryId(rs.getInt("CategoryId"));
            book.setCategoryName(rs.getString("category.Name"));
        }
        conn.close();
        return book;
    }

    public ArrayList<Book> getBooksByCategoryId(int categoryId) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        ArrayList<Book> listBook = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("select * from book left join category on book.CategoryId = category.Id where book.CategoryId=?");
        stmt.setInt(1, categoryId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getString("Id"));
            book.setName(rs.getString("Name"));
            book.setAuthor(rs.getString("Author"));
            book.setQuantity(rs.getInt("Quantity"));
            book.setCategoryId(rs.getInt("CategoryId"));
            book.setCategoryName(rs.getString("category.Name"));
            listBook.add(book);
        }
        conn.close();
        return listBook;
    }
}
