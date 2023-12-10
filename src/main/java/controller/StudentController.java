package controller;

import model.bean.ClassEntity;
import model.bean.Student;
import model.bo.ClassBO;
import model.bo.StudentBO;

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

@WebServlet(name = "StudentController", urlPatterns = "/student")
public class StudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination = null;
        StudentBO studentBO = new StudentBO();
        ClassBO classBO = new ClassBO();

        String mod = request.getParameter("mod");
        String id = request.getParameter("id");
        String searchKeyStudent = request.getParameter("searchKeyStudent");
        if (searchKeyStudent == null) {
            searchKeyStudent = "";
        }
        else {
            searchKeyStudent = searchKeyStudent.trim();
        }

        int searchClassId = request.getParameter("searchClassId") != null 
            ? Integer.parseInt(request.getParameter("searchClassId").trim()) : 0;

        ArrayList<ClassEntity> classCBB = new ArrayList<>();
        try {
            classCBB = classBO.getCBBClasses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("classCBB", classCBB);

        ArrayList<Student> studentList = null;
        Student student = null;

        try {
            if (mod != null) {
                switch (mod) {
                    case "insert":
                        destination = "/WEB-INF/Student/StudentInsert.jsp";
                        break;

                    case "checkId":
                        boolean check = studentBO.checkAddStudent(id);
                        response.setContentType("application/json");
                        PrintWriter out = response.getWriter();
                        out.print(check);
                        out.flush();
                        out.close();
                        break;

                    case "update":
                        student = studentBO.getStudentDetail(id);
                        request.setAttribute("student", student);
                        destination = "/WEB-INF/Student/StudentUpdate.jsp";
                        break;

                    case "delete":
                        if (id != null) {
                            studentBO.deleteStudent(id);
                        }
                        studentList = studentBO.searchStudents(searchKeyStudent, searchClassId);
                        request.setAttribute("studentList", studentList);
                        destination = "/WEB-INF/Student/StudentList.jsp";
                        break;

                    default:
                        studentList = studentBO.searchStudents(searchKeyStudent, searchClassId);
                        request.setAttribute("studentList", studentList);
                        destination = "/WEB-INF/Student/StudentList.jsp";
                        break;
                }

            } else {
                studentList = studentBO.searchStudents(searchKeyStudent, searchClassId);
                request.setAttribute("studentList", studentList);
                destination = "/WEB-INF/Student/StudentList.jsp";
            }

            RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
            rd.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentBO studentBO = new StudentBO();

        String action = request.getParameter("action");
        Student student = null;

        try {
            if (action != null) {
                switch (action) {
                    case "update":
                        student = new Student(
                                request.getParameter("id"),
                                request.getParameter("name"),
                                Integer.parseInt(request.getParameter("classId")));
                        studentBO.updateStudent(student);
                        break;

                    case "insert":
                        student = new Student(
                                request.getParameter("id"),
                                request.getParameter("name"),
                                Integer.parseInt(request.getParameter("classId")));
                        studentBO.addStudent(student);
                        break;
                }
            }
            doGet(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
