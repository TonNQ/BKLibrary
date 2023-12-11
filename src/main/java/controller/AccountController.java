package controller;

import model.bean.Account;
import model.bo.AccountBO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "AccountController", urlPatterns = "/account")
public class AccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination = null;
        AccountBO accountBO = new AccountBO();

        String mod = request.getParameter("mod");
        int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id").trim());
        int statusAccount = request.getParameter("statusAccount") == null ? 0
                : Integer.parseInt(request.getParameter("statusAccount").trim());

        String searchKeyUsername = request.getParameter("searchKeyUsername");
        if (searchKeyUsername == null) {
            searchKeyUsername = "";
        } else {
            searchKeyUsername = searchKeyUsername.trim();
        }

        ArrayList<Account> accountList = null;
        Account account = null;

        try {
            if (mod != null) {
                switch (mod) {
                    case "insert":
                        destination = "/WEB-INF/Account/AccountInsert.jsp";
                        break;

                    case "checkUsername":
                        String username = request.getParameter("username");
                        if (username == null) {
                            username = "";
                        } else {
                            username = username.trim();
                        }
                        boolean check = accountBO.checkUsername(username);
                        response.setContentType("application/json");
                        PrintWriter out = response.getWriter();
                        out.print(check);
                        out.flush();
                        out.close();
                        break;

                    case "update":
                        account = accountBO.getAccountDetail(id);
                        request.setAttribute("account", account);
                        destination = "/WEB-INF/Account/AccountUpdate.jsp";
                        break;

                    case "delete":
                        if (id != 0) {
                            accountBO.deleteAccount(id);
                        }

                    default:
                        accountList = accountBO.searchAccounts(searchKeyUsername, statusAccount);
                        request.setAttribute("accountList", accountList);
                        destination = "/WEB-INF/Account/AccountList.jsp";
                        break;
                }

            } else {
                accountList = accountBO.searchAccounts(searchKeyUsername, statusAccount);
                request.setAttribute("accountList", accountList);
                destination = "/WEB-INF/Account/AccountList.jsp";
            }

            RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
            rd.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountBO accountBO = new AccountBO();

        int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id").trim());
        String password = request.getParameter("password");
        if (password == null) {
            password = "";
        } else {
            password = password.trim();
        }
        String action = request.getParameter("action");
        Account account = null;

        try {
            if (action != null) {
                switch (action) {
                    case "update":
                        if (id > 0) {
                            account = new Account(
                                    id,
                                    request.getParameter("username"),
                                    request.getParameter("password"),
                                    Boolean.parseBoolean(request.getParameter("isAdmin")));
                            accountBO.updateAccount(account);
                        }
                        break;

                    case "insert":
                        account = new Account(
                                request.getParameter("username"),
                                request.getParameter("password"),
                                Boolean.parseBoolean(request.getParameter("isAdmin")));
                        accountBO.addAccount(account);
                        break;
                }
            }
            doGet(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}