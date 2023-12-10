package controller;

import model.bean.ClassEntity;
import model.bean.Faculty;
import model.bo.ClassBO;
import model.bo.FacultyBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ClassController", urlPatterns = "/class")
public class ClassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String destination = null;
            FacultyBO facultyBO = new FacultyBO();
            ClassBO classBO = new ClassBO();

            String mod = request.getParameter("mod");
            int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;

            String searchKeyClass = request.getParameter("searchKeyClass");
            if (searchKeyClass == null) {
                searchKeyClass = "";
            } else {
                searchKeyClass = searchKeyClass.trim();
            }

            String searchFacultyId = request.getParameter("searchFacultyId");
            if (searchFacultyId == null) {
                searchFacultyId = "";
            } else {
                searchFacultyId = searchFacultyId.trim();
            }

            ArrayList<Faculty> facultyCBB = new ArrayList<>();
            try {
                facultyCBB = facultyBO.getCBBFaculties();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("facultyCBB", facultyCBB);

            ArrayList<ClassEntity> classList = null;
            ClassEntity classEntity = null;

            if (mod != null) {
                switch (mod) {
                    case "insert":
                        destination = "/WEB-INF/Class/ClassInsert.jsp";
                        break;

                    case "update":
                        classEntity = classBO.getClassDetail(id);
                        request.setAttribute("classEntity", classEntity);
                        destination = "/WEB-INF/Class/ClassUpdate.jsp";
                        break;

                    case "delete":
                        if (id > 0) {
                            classBO.deleteClass(id);
                        }
                        classList = classBO.searchClasses(searchKeyClass, searchFacultyId);
                        request.setAttribute("classList", classList);
                        destination = "/WEB-INF/Class/ClassList.jsp";
                        break;

                    default:
                        classList = classBO.searchClasses(searchKeyClass, searchFacultyId);
                        request.setAttribute("classList", classList);
                        destination = "/WEB-INF/Class/ClassList.jsp";
                        break;
                }
            } else {
                classList = classBO.searchClasses(searchKeyClass, searchFacultyId);
                request.setAttribute("classList", classList);
                destination = "/WEB-INF/Class/ClassList.jsp";
            }
            request.getRequestDispatcher(destination).forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClassBO classBO = new ClassBO();
        String action = request.getParameter("action");
        ClassEntity classEntity = null;

        try {
            if (action != null) {
                switch (action) {
                    case "update":
                        classEntity = new ClassEntity(
                                Integer.parseInt(request.getParameter("id")),
                                request.getParameter("name"),
                                request.getParameter("facultyId"));
                        classBO.updateClass(classEntity);
                        break;

                    case "insert":
                        classEntity = new ClassEntity(
                                request.getParameter("name"),
                                request.getParameter("facultyId"));
                        classBO.addClass(classEntity);
                        break;
                }
            }

            doGet(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
