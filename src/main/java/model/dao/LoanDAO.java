package model.dao;

import java.sql.*;
import java.util.ArrayList;

import model.bean.ClassEntity;
import model.bean.Loan;

public class LoanDAO {
    Connection conn = ConnectionToDB.ConnectToMySQL();

    public ArrayList<Loan> searchLoans(String searchKey, int status) throws SQLException {
        ArrayList<Loan> loans = new ArrayList<>();

        String query = "SELECT * FROM libraryloan " +
                "where (studentId like ?) and (?=0 " +
                "or (?=1 and ReturnDate is null) " +
                "or (?=2 and ReturnDate > dueDate) " +
                "or (?=3 and ReturnDate < dueDate)) ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%"+searchKey+"%");
            preparedStatement.setInt(2, status);            
            preparedStatement.setInt(3, status);
            preparedStatement.setInt(4, status);            
            preparedStatement.setInt(5, status);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Loan faculty = new Loan(
                    resultSet.getInt("id"),
                    resultSet.getString("bookId"),
                    resultSet.getString("studentId"),
                    resultSet.getDate("borrowDate"),
                    resultSet.getDate("dueDate"),
                    resultSet.getDate("returnDate")
            );
            loans.add(faculty);
        }
        return loans;
    }

    public void returnBook(int id) throws SQLException {
        String query = "UPDATE libraryloan SET returnDate = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public void addLoanList(ArrayList<Loan> loanList) throws SQLException {
        for (int i = 0; i < loanList.size(); i++) {
            Loan loan = loanList.get(i);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO libraryloan (StudentId, BookId, BorrowDate, DueDate, ReturnDate) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, loan.getStudentId());
            statement.setString(2, loan.getBookId());
            statement.setDate(3, loan.getBorrowDate());
            statement.setDate(4, loan.getDueDate());
            statement.setDate(5, loan.getReturnDate());
            statement.executeUpdate();
        }

    }
}
