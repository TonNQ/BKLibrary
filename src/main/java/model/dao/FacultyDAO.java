package model.dao;

import model.bean.Faculty;

import java.sql.*;
import java.util.ArrayList;

public class FacultyDAO {
    Connection conn = ConnectionToDB.ConnectToMySQL();

    public ArrayList<Faculty> getAllFaculties() throws SQLException {
        ArrayList<Faculty> faculties = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(" select faculty.Id, faculty.Name, count(class.Id) as TotalClass from faculty " +
                " left join class on faculty.Id = class.facultyId " +
                " group by faculty.Id, faculty.Name");
        while (resultSet.next()) {
            Faculty faculty = new Faculty(
                    resultSet.getString("Id"),
                    resultSet.getString("Name"),
                    resultSet.getInt("TotalClass")
            );
            faculties.add(faculty);
        }
        return faculties;
    }

    public ArrayList<Faculty> getCBBFaculties() throws SQLException {
        ArrayList<Faculty> faculties = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(" select * from faculty ");
        while (resultSet.next()) {
            Faculty faculty = new Faculty(
                    resultSet.getString("Id"),
                    resultSet.getString("Name")
            );
            faculties.add(faculty);
        }
        return faculties;
    }

    public ArrayList<Faculty> searchFaculties(String searchKey) throws SQLException {
        ArrayList<Faculty> faculties = new ArrayList<>();

        String query = " select faculty.Id, faculty.Name, count(class.Id) as TotalClass from faculty " +
                    " left join class on faculty.Id = class.facultyId " +
                    " where faculty.Id like ? or faculty.Name like ? " +
                    " group by faculty.Id, faculty.Name";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%"+searchKey+"%");
            preparedStatement.setString(2, "%"+searchKey+"%");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Faculty faculty = new Faculty(
                    resultSet.getString("Id"),
                    resultSet.getString("Name"),
                    resultSet.getInt("TotalClass")
            );
            faculties.add(faculty);
        }
        return faculties;
    }

    public Faculty getFacultyDetail(String id) throws SQLException {
        String query = "Select * from faculty where id=?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        Faculty faculty = null;
        if (resultSet.next()) {
            faculty = new Faculty(
                    resultSet.getString("Id"),
                    resultSet.getString("Name")
            );
        }
        return faculty;
    }

    public void addFaculty(Faculty faculty) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO faculty VALUES (?, ?)");
        statement.setString(1, faculty.getId());
        statement.setString(2, faculty.getName());
        statement.executeUpdate();
    }

    public boolean checkAddFaculty (String id) throws SQLException {
        boolean isExist = false;
        if(conn != null) {
            String query = "Select * from faculty where id=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            isExist = resultSet.next();
            preparedStatement.close();
        }
        return !isExist;
    }

    public void updateFaculty(Faculty faculty) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE faculty SET Name=? WHERE Id=?");
        statement.setString(1, faculty.getName());
        statement.setString(2, faculty.getId());
        statement.executeUpdate();
    }

    public void deleteFaculty(String id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM faculty WHERE Id=?");
        statement.setString(1, id);
        statement.executeUpdate();
    }
}
