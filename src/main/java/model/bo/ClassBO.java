package model.bo;

import model.bean.ClassEntity;
import model.dao.ClassDAO;

import java.sql.*;
import java.util.ArrayList;

public class ClassBO {
    ClassDAO classDAO = new ClassDAO();

    public ArrayList<ClassEntity> searchClasses(String searchKey, String facultyId) throws SQLException {
        return classDAO.searchClasses(searchKey, facultyId);
    }

    public ArrayList<ClassEntity> getCBBClasses() throws SQLException {
        return classDAO.getCBBClasses();
    }

    public ClassEntity getClassDetail(int id) throws SQLException {
        return classDAO.getClassDetail(id);
    }

    public void addClass(ClassEntity classEntity) throws SQLException {
        classDAO.addClass(classEntity);
    }

    public void updateClass(ClassEntity classEntity) throws SQLException {
        classDAO.updateClass(classEntity);
    }

    public void deleteClass(int id) throws SQLException {
        classDAO.deleteClass(id);
    }
}
