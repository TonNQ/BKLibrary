package controller;

import model.bean.Category;
import model.bo.CategoryBO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoryController", urlPatterns = "/category")
public class CategoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String destination = null;
            String mod = req.getParameter("mod");
            CategoryBO categoryBO = new CategoryBO();
            if (mod == null) {
                req.setAttribute("categoryList", categoryBO.getAllCategories());
                destination = "/WEB-INF/Category/CategoryList.jsp";
                req.setAttribute("categoryList", categoryBO.getAllCategories());
            } else if (mod.equals("insert")) {
                destination = "/WEB-INF/Category/InsertCategory.jsp";
            } else if (mod.equals("update")) {
                String categoryId = req.getParameter("id");
                Category category = categoryBO.getCategoryById(Integer.parseInt(categoryId));
                req.setAttribute("category", category);
                destination = "/WEB-INF/Category/UpdateCategory.jsp";
            } else if (mod.equals("check")) {
                String name = req.getParameter("name");
                if (categoryBO.isNameExist(name)) {
                    resp.getWriter().write("true");
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                return;
            }

            req.getRequestDispatcher(destination).forward(req, resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            String action = req.getParameter("action");
            CategoryBO categoryBO = new CategoryBO();
            if (action.equals("insert")) {
                String name = req.getParameter("name");
                categoryBO.addCategory(name);
            } else if (action.equals("update")) {
                String id = req.getParameter("id");
                String name = req.getParameter("name");
                categoryBO.updateCategory(new Category(Integer.parseInt(id), name));
            }

            doGet(req, resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String action = req.getParameter("action");
            CategoryBO categoryBO = new CategoryBO();
            if (action.equals("delete")) {
                categoryBO.deleteCategory(Integer.parseInt(req.getParameter("id")));
                resp.getWriter().write("Xoá danh mục thành công");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
