package controller;

import model.bo.BookBO;

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
            String action = req.getParameter("action");
            BookBO bookBO = new BookBO();
            if (action == null) {
                destination = "/WEB-INF/Book/BookList.jsp";
                req.setAttribute("bookList", bookBO.getAllBooks());
            } else if (action.equals("add")) {
                destination = "/WEB-INF/book/add.jsp";
            } else if (action.equals("edit")) {
                destination = "/WEB-INF/book/edit.jsp";
            } else if (action.equals("delete")) {
                destination = "/WEB-INF/book/delete.jsp";
            }

            req.getRequestDispatcher(destination).forward(req, resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
