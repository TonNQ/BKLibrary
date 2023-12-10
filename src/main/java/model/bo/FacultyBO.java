package model.bo;

import model.bean.Faculty;
import model.dao.FacultyDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class FacultyBO {
    FacultyDAO facultyDAO = new FacultyDAO();

    public ArrayList<Faculty> getCBBFaculties() throws SQLException {
        return facultyDAO.getCBBFaculties();
    }

    public Faculty getFacultyDetail(String id) throws SQLException {
        return facultyDAO.getFacultyDetail(id);
    }

    public ArrayList<Faculty> searchFaculties(String searchKey) throws SQLException {
        return facultyDAO.searchFaculties(searchKey);
    }

    public void addFaculty(Faculty faculty) throws SQLException {
        facultyDAO.addFaculty(faculty);
    }

    public boolean checkAddFaculty (String id) throws SQLException {
        return facultyDAO.checkAddFaculty(id);
    }

    public void updateFaculty(Faculty faculty) throws SQLException {
        facultyDAO.updateFaculty(faculty);
    }

    public void deleteFaculty(String id) throws SQLException {
        facultyDAO.deleteFaculty(id);
    }
}
