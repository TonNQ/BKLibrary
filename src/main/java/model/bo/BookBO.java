package model.bo;

import model.bean.Book;
import model.dao.BookDAO;

import java.util.ArrayList;

public class BookBO {
    BookDAO bookDAO = new BookDAO();
    public ArrayList<Book> getAllBooks() throws Exception {
        return bookDAO.getAllBooks();
    }
    public ArrayList<Book> getBooksById(String id) throws Exception {
        return bookDAO.getBooksById(id);
    }
    public ArrayList<Book> getBooksByName(String name) throws Exception {
        return bookDAO.getBooksByName(name);
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

}
