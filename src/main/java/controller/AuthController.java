package controller;

import model.bean.Account;
import model.bo.AccountBO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthController", urlPatterns = "/auth")
public class AuthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String mod = req.getParameter("mod");
            if (mod == null) {
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String action = req.getParameter("action");
            AccountBO accountBO = new AccountBO();
            if (action.equals("login")) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                Account account = accountBO.getAccount(username, password);
                if (account != null) {
                    req.getSession().setAttribute("account", account);
                } else {
                    resp.getWriter().write("false");
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            } else if (action.equals("logout")) {
                req.getSession().removeAttribute("account");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
