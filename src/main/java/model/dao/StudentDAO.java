package model.dao;

import model.bean.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    Connection conn = ConnectionToDB.ConnectToMySQL();

    public ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(" select student.Id, student.Name, class.Name as ClassName from student " +
                " join class on student.ClassId = class.Id ");
        while (resultSet.next()) {
            Student student = new Student(
                    resultSet.getString("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("ClassName")
            );
            students.add(student);
        }
        return students;
    }

    public ArrayList<Student> searchStudents(String searchKey, int classId) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();

        String query = " select student.Id, student.Name, class.Name as ClassName from student " +
                    " join class on student.ClassId = class.Id " +
                    " where (student.Id like ? or student.Name like ?) and (0=? or class.Id=?) ";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, "%"+searchKey+"%");
        preparedStatement.setString(2, "%"+searchKey+"%");
        preparedStatement.setInt(3, classId);
        preparedStatement.setInt(4, classId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Student student = new Student(
                    resultSet.getString("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("ClassName")
            );
            students.add(student);
        }
        return students;
    }

    public Student getStudentDetail(String id) throws SQLException {
        String query = "Select * from student where id=?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        Student student = null;
        if (resultSet.next()) {
            student = new Student(
                    resultSet.getString("Id"),
                    resultSet.getString("Name"),
                    resultSet.getInt("ClassId")
            );
        }
        return student;
    }

    public void addStudent(Student student) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO student VALUES (?, ?, ?)");
        statement.setString(1, student.getId());
        statement.setString(2, student.getName());
        statement.setInt(3, student.getClassId());
        statement.executeUpdate();
    }

    public boolean checkAddStudent (String id) throws SQLException {
        boolean isExist = false;
        if(conn != null) {
            String query = "Select * from student where id=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            isExist = resultSet.next();
            preparedStatement.close();
        }
        return !isExist;
    }

    public void updateStudent(Student student) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE student SET Name=?, ClassId=? WHERE Id=?");
        statement.setString(1, student.getName());
        statement.setInt(2, student.getClassId());
        statement.setString(3, student.getId());
        statement.executeUpdate();
    }

    public void deleteStudent(String id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM libraryloan WHERE studentId=?");
        statement.setString(1, id);
        statement.executeUpdate();

        statement = conn.prepareStatement("DELETE FROM student WHERE Id=?");
        statement.setString(1, id);
        statement.executeUpdate();
    }
}