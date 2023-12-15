package controller;

import model.bean.Faculty;
import model.bo.FacultyBO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "FacultyController", urlPatterns = "/faculty")
public class FacultyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination = null;
        FacultyBO facultyBO = new FacultyBO();

        String mod = request.getParameter("mod");
        String id = request.getParameter("id");
        String searchKeyFaculty = request.getParameter("searchKeyFaculty");

        if (searchKeyFaculty == null) {
            searchKeyFaculty = "";
        } else {
            searchKeyFaculty = searchKeyFaculty.trim();
        }

        ArrayList<Faculty> facultyList = null;
        Faculty faculty = null;

        try {
            if (mod != null) {
                switch (mod) {
                    case "insert":
                        destination = "/WEB-INF/Faculty/FacultyInsert.jsp";
                        break;
                        
                    case "checkId":
                        boolean check = facultyBO.checkAddFaculty(id);
                        response.setContentType("application/json");
                        PrintWriter out = response.getWriter();
                        out.print(check);
                        out.flush();
                        out.close();
                        break;

                    case "update":
                        faculty = facultyBO.getFacultyDetail(id);
                        request.setAttribute("faculty", faculty);
                        destination = "/WEB-INF/Faculty/FacultyUpdate.jsp";
                        break;

                    case "delete":
                        if (id != null) {
                            facultyBO.deleteFaculty(id);
                        }
                        facultyList = facultyBO.searchFaculties(searchKeyFaculty);
                        request.setAttribute("facultyList", facultyList);
                        destination = "/WEB-INF/Faculty/FacultyList.jsp";
                        break;

                    default:
                        facultyList = facultyBO.searchFaculties(searchKeyFaculty);
                        request.setAttribute("facultyList", facultyList);
                        destination = "/WEB-INF/Faculty/FacultyList.jsp";
                        break;
                }

            } else {
                facultyList = facultyBO.searchFaculties(searchKeyFaculty);
                request.setAttribute("facultyList", facultyList);
                destination = "/WEB-INF/Faculty/FacultyList.jsp";
            }

            RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
            rd.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        FacultyBO facultyBO = new FacultyBO();

        String action = request.getParameter("action");
        Faculty faculty = null;

        try {
            if (action != null) {
                switch (action) {
                    case "update":
                        faculty = new Faculty(
                                request.getParameter("id"),
                                request.getParameter("name"));
                        facultyBO.updateFaculty(faculty);
                        break;

                    case "insert":
                        faculty = new Faculty(
                                request.getParameter("id"),
                                request.getParameter("name"));
                        facultyBO.addFaculty(faculty);
                        break;
                }
            }
            doGet(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
