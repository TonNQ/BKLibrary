package model.bo;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Account;
import model.dao.AccountDAO;

public class AccountBO {
    AccountDAO accountDAO = new AccountDAO();

    public Account getAccount(String username, String password) throws Exception {
        return accountDAO.getAccount(username, password);
    }

    public ArrayList<Account> searchAccounts(String searchKey, int status) throws SQLException {
        return accountDAO.searchAccounts(searchKey, status);
    }

    public Account getAccountDetail(int id) throws SQLException {
        return accountDAO.getAccountDetail(id);
    }

    public void addAccount(Account account) throws SQLException {
        accountDAO.addAccount(account);
    }

    public Boolean checkUsername(String username) throws SQLException {
        return accountDAO.checkUsername(username);
    }

    public void updateAccount(Account account) throws SQLException {
        accountDAO.updateAccount(account);
    }

    public void deleteAccount(int id) throws SQLException {
        accountDAO.deleteAccount(id);
    }
}