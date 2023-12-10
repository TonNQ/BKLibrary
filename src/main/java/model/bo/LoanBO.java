package model.bo;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Loan;
import model.dao.LoanDAO;

public class LoanBO {
    LoanDAO loanDAO = new LoanDAO();

    public ArrayList<Loan> searchLoans(String searchKey, int status) throws SQLException {
        return loanDAO.searchLoans(searchKey, status);
    }

    public void returnBook(int id) throws SQLException {
        loanDAO.returnBook(id);
    }
}
