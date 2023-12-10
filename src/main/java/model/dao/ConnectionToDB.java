package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    private static Connection cnn;
    public static Connection ConnectToMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            String url = "jdbc:mysql://localhost:3333/BKLibrary";
            String url = "jdbc:mysql://localhost:3306/btnhom";
            cnn = DriverManager.getConnection(url, "root", "");
            return cnn;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối với MySQL Server: " + e.getMessage());
        }
        return null;
    }
}
