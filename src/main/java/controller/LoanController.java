package controller;

import model.bean.Loan;
import model.bo.LoanBO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "LoanController", urlPatterns = "/loan")
public class LoanController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination = null;
        LoanBO loanBO = new LoanBO();

        String mod = request.getParameter("mod");
        int id = request.getParameter("id") != null
            ? Integer.parseInt(request.getParameter("id")) : 0;
        String searchStudentId = request.getParameter("searchStudentId");

        if (searchStudentId == null) {
            searchStudentId = "";
        } else {
            searchStudentId = searchStudentId.trim();
        }

        int loanStatus = request.getParameter("loanStatus") != null
                ? Integer.parseInt(request.getParameter("loanStatus").trim())
                : 1;

        ArrayList<Loan> loanList = null;

        try {
            if (mod != null) {
                switch (mod) {
                    case "insert":
                        destination = "/WEB-INF/Loan/LoanInsert.jsp";
                        break;

                    case "return":
                        loanBO.returnBook(id);
                        loanList = loanBO.searchLoans(searchStudentId, loanStatus);
                        request.setAttribute("loanList", loanList);
                        destination = "/WEB-INF/Loan/LoanHistory.jsp";
                        break;

                    default:
                        loanList = loanBO.searchLoans(searchStudentId, loanStatus);
                        request.setAttribute("loanList", loanList);
                        destination = "/WEB-INF/Loan/LoanHistory.jsp";
                        break;
                }

            } else {
                loanList = loanBO.searchLoans(searchStudentId, loanStatus);
                request.setAttribute("loanList", loanList);
                destination = "/WEB-INF/Loan/LoanHistory.jsp";
            }

            RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
            rd.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // LoanBO loanBO = new LoanBO();

        // String action = request.getParameter("action");

        // try {
        //     if (action != null) {
        //         switch (action) {
        //             case "insert":
        //                 // thêm mượn sách nè
        //                 break;
        //         }
        //     }
        //     doGet(request, response);
        // } catch (SQLException e) {
        //     throw new RuntimeException(e);
        // }
    }
}
