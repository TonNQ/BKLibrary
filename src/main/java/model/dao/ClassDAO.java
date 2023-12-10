package model.dao;

import model.bean.ClassEntity;

import java.sql.*;
import java.util.ArrayList;

public class ClassDAO {
    Connection conn = ConnectionToDB.ConnectToMySQL();

    public ArrayList<ClassEntity> getAllClasses() throws SQLException {
        ArrayList<ClassEntity> classes = new ArrayList<>();
        Statement statement = conn.createStatement();
        String query = " select class.Id, class.Name, faculty.Name as FacultyName, count(student.Id) as TotalStudent from class " +
                    " join faculty on faculty.Id = class.facultyId " +
                    " left join student on class.Id = student.classId " +
                    " group by class.Id, class.Name, FacultyName";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            ClassEntity classEntity = new ClassEntity(
                    resultSet.getInt("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("FacultyName"),
                    resultSet.getInt("TotalStudent")
            );
            classes.add(classEntity);
        }
        return classes;
    }

    public ArrayList<ClassEntity> getCBBClasses() throws SQLException {
        ArrayList<ClassEntity> classes = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(" select * from class ");
        while (resultSet.next()) {
            ClassEntity classEntity = new ClassEntity(
                    Integer.parseInt(resultSet.getString("Id")),
                    resultSet.getString("Name")
            );
            classes.add(classEntity);
        }
        return classes;
    }

    public ArrayList<ClassEntity> searchClasses(String searchKey, String facultyId) throws SQLException {
        ArrayList<ClassEntity> classes = new ArrayList<>();

        String query = " select class.Id, class.Name, faculty.Name as FacultyName, count(student.Id) as TotalStudent from class " +
                    " join faculty on faculty.Id = class.facultyId " +
                    " left join student on class.Id = student.classId " +
                    " where class.Name like ? and (''=? or class.FacultyId=?) " +
                    " group by class.Id, class.Name, FacultyName";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, "%"+searchKey+"%");
        preparedStatement.setString(2, facultyId);
        preparedStatement.setString(3, facultyId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            ClassEntity classEntity = new ClassEntity(
                    resultSet.getInt("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("FacultyName"),
                    resultSet.getInt("TotalStudent")
            );
            classes.add(classEntity);
        }
        return classes;
    }

    public ClassEntity getClassDetail(int id) throws SQLException {
        String query = "Select * from class where id=?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        ClassEntity classEntity = null;
        if (resultSet.next()) {
            classEntity = new ClassEntity(
                    resultSet.getInt("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("FacultyId")
            );
        }
        return classEntity;
    }
    
    public void addClass(ClassEntity classEntity) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO class (Name, FacultyId) VALUES (?, ?)");
        statement.setString(1, classEntity.getName());
        statement.setString(2, classEntity.getFacultyId());
        statement.executeUpdate();
    }

    public void updateClass(ClassEntity classEntity) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE class SET Name=?, FacultyId=? WHERE Id=?");
        statement.setString(1, classEntity.getName());
        statement.setString(2, classEntity.getFacultyId());
        statement.setInt(3, classEntity.getId());
        statement.executeUpdate();
    }

    public void deleteClass(int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM class WHERE Id=?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
