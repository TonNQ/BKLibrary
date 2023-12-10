package model.bo;

import model.bean.Student;
import model.dao.StudentDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBO {
    StudentDAO studentDAO = new StudentDAO();

    public ArrayList<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public ArrayList<Student> searchStudents(String searchKey, int classId) throws SQLException {
        return studentDAO.searchStudents(searchKey, classId);
    }

    public Student getStudentDetail(String id) throws SQLException {
        return studentDAO.getStudentDetail(id);
    }

    public void addStudent(Student student) throws SQLException {
        studentDAO.addStudent(student);
    }

    public boolean checkAddStudent (String id) throws SQLException {
        return studentDAO.checkAddStudent(id);
    }

    public void updateStudent(Student student) throws SQLException {
        studentDAO.updateStudent(student);
    }

    public void deleteStudent(String id) throws SQLException {
        studentDAO.deleteStudent(id);
    }
}
