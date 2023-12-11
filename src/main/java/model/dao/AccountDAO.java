package model.dao;

import java.sql.*;
import java.util.ArrayList;

import model.bean.Account;

public class AccountDAO {
    Connection conn = ConnectionToDB.ConnectToMySQL();

    public ArrayList<Account> searchAccounts(String searchKey, int status) throws SQLException {
        ArrayList<Account> accounts = new ArrayList<>();

        String query = "SELECT * FROM account " +
                "where (username like ?) and (?=0 " +
                "or (?=1 and isAdmin) " +
                "or (?=2 and !isAdmin)) " +
                "order by isAdmin desc";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%"+searchKey+"%");
            preparedStatement.setInt(2, status);            
            preparedStatement.setInt(3, status);
            preparedStatement.setInt(4, status);            

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Account account = new Account(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("isAdmin")
            );
            accounts.add(account);
        }
        return accounts;
    }

    public Account getAccountDetail(int id) throws SQLException {
        String query = "SELECT * FROM account WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Account account = new Account(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("isAdmin")
            );
            return account;
        }
        return null;
    }

    public void addAccount(Account account) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO account (username, password, isAdmin) VALUES (?, ?, ?)");
        statement.setString(1, account.getUsername());
        statement.setString(2, account.getPassword());
        statement.setBoolean(3, account.isAdmin());
        statement.executeUpdate();
    }

    public Boolean checkUsername(String username) throws SQLException {
        String query = "SELECT * FROM account WHERE username = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return false;
        }
        return true;
    }

    public void updateAccount(Account account) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE account SET password = ?, isAdmin = ? WHERE id = ?");
        statement.setString(1, account.getPassword());
        statement.setBoolean(2, account.isAdmin());
        statement.setInt(3, account.getId());
        statement.executeUpdate();
    }

    public void deleteAccount(int id) throws SQLException {
        String query = "DELETE FROM account WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}