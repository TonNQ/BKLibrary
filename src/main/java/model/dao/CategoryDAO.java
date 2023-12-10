package model.dao;

import model.bean.Category;

import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO {
    public ArrayList<Category> getAllCategories() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        Connection conn = ConnectionToDB.ConnectToMySQL();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM category");
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt("Id"));
            category.setName(resultSet.getString("Name"));
            categories.add(category);
        }
        conn.close();
        return categories;
    }
    public void addCategory(Category category) throws SQLException {
        Connection conn = ConnectionToDB.ConnectToMySQL();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO category (Name) VALUES (?)");
        statement.setString(1, category.getName());
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
}
