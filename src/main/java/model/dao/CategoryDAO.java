package model.dao;

import model.bean.Category;

import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO {
    public ArrayList<Category> getAllCategories() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        Connection conn = ConnectionToDB.ConnectToMySQL();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT category.Id, category.Name, count(book.Id) as TotalBook FROM category " +
                "left join book on category.Id = book.CategoryId " +
                "group by category.Id, category.Name " +
                "order by category.Name COLLATE utf8mb4_unicode_ci ASC");
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt("Id"));
            category.setName(resultSet.getString("Name"));
            category.setTotalBook(resultSet.getInt("TotalBook"));
            categories.add(category);
        }
        conn.close();
        return categories;
    }
    public void addCategory(String name) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO category (Name) VALUES (?)");
        statement.setString(1, name);
        statement.executeUpdate();
        conn.close();
    }
    public void updateCategory(Category category) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement statement = conn.prepareStatement("UPDATE category SET Name=? WHERE Id=?");
        statement.setString(1, category.getName());
        statement.setInt(2, category.getId());
        statement.executeUpdate();
        conn.close();
    }
    public void deleteCategory(int id) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement statement = conn.prepareStatement("DELETE FROM category WHERE Id=?");
        statement.setInt(1, id);
        statement.executeUpdate();
        conn.close();
    }
    public Category getCategoryById(int id) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM category WHERE Id=?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Category category = new Category();
        while (resultSet.next()) {
            category.setId(resultSet.getInt("Id"));
            category.setName(resultSet.getString("Name"));
        }
        conn.close();
        return category;
    }
    public boolean isNameExist(String name) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM category WHERE Name=?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        boolean isExist = resultSet.next();
        conn.close();
        return isExist;
    }
}
