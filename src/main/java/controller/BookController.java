package controller;

import model.bean.Book;
import model.bo.BookBO;
import model.bo.CategoryBO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BookController", urlPatterns = "/book")
public class BookController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String destination = null;
            String mod = req.getParameter("mod");
            BookBO bookBO = new BookBO();
            CategoryBO categoryBO = new CategoryBO();
            if (mod == null) {
                req.setAttribute("categoryList", categoryBO.getAllCategories());
                destination = "/WEB-INF/Book/BookList.jsp";
                req.setAttribute("bookList", bookBO.getAllBooks());
            } else if (mod.equals("insert")) {
                req.setAttribute("categoryList", categoryBO.getAllCategories());
                destination = "/WEB-INF/Book/InsertBook.jsp";
            } else if (mod.equals("update")) {
                String bookId = req.getParameter("id");
                Book book = bookBO.getBookById(bookId);
                req.setAttribute("book", book);
                req.setAttribute("categoryList", categoryBO.getAllCategories());
                destination = "/WEB-INF/Book/UpdateBook.jsp";
            } else if (mod.equals("check")) {
                String bookId = req.getParameter("id");
                if (bookBO.isIdExist(bookId)) {
                    resp.getWriter().write("true");
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                return;
            } else if (mod.equals("filter")) {
                String categoryId = req.getParameter("category");
                req.setAttribute("categoryList", categoryBO.getAllCategories());
                req.setAttribute("bookList", bookBO.getBooksByCategoryId(Integer.parseInt(categoryId)));
                destination = "/WEB-INF/Book/BookList.jsp";
            }

            req.getRequestDispatcher(destination).forward(req, resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            String destination = null;
            String action = req.getParameter("action");
            BookBO bookBO = new BookBO();
            if (action.equals("insert")) {
                String bookId = req.getParameter("bookId");
                String name = req.getParameter("name");
                String author = req.getParameter("author");
                String quantity = req.getParameter("quantity");
                String categoryId = req.getParameter("category");
                bookBO.addBook(new Book(bookId, name, author, Integer.parseInt(quantity), Integer.parseInt(categoryId)));
                destination = "/book";
            } else if (action.equals("update")) {
                String bookId = req.getParameter("bookId");
                String name = req.getParameter("name");
                String author = req.getParameter("author");
                String quantity = req.getParameter("quantity");
                String categoryId = req.getParameter("category");
                bookBO.updateBook(new Book(bookId, name, author, Integer.parseInt(quantity), Integer.parseInt(categoryId)));
                destination = "/book";
            }

            resp.sendRedirect(destination);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String action = req.getParameter("action");
            BookBO bookBO = new BookBO();
            if (action.equals("delete")) {
                bookBO.deleteBook(req.getParameter("id"));
                resp.getWriter().write("Xoá sách thành công");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
