package model.bo;

import model.bean.Book;
import model.dao.BookDAO;

import java.util.ArrayList;

public class BookBO {
    BookDAO bookDAO = new BookDAO();
    public ArrayList<Book> getAllBooks() throws Exception {
        return bookDAO.getAllBooks();
    }
    public void addBook(Book book) throws Exception {
        bookDAO.addBook(book);
    }
    public void updateBook(Book book) throws Exception {
        bookDAO.updateBook(book);
    }
    public void deleteBook(String id) throws Exception {
        bookDAO.deleteBook(id);
    }
    public boolean isIdExist(String id) throws Exception {
        return bookDAO.isIdExist(id);
    }

    public Book getBookById(String id) throws Exception {
        return bookDAO.getBookById(id);
    }
}
