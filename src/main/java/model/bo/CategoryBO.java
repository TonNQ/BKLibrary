package model.bo;

import model.bean.Category;
import model.dao.CategoryDAO;

import java.util.ArrayList;

public class CategoryBO {
    CategoryDAO categoryDAO = new CategoryDAO();
    public ArrayList<Category> getAllCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }
    public void addCategory(String name) throws Exception {
        categoryDAO.addCategory(name);
    }
    public void updateCategory(Category category) throws Exception {
        categoryDAO.updateCategory(category);
    }
    public void deleteCategory(int id) throws Exception {
        categoryDAO.deleteCategory(id);
    }
    public Category getCategoryById(int id) throws Exception {
        return categoryDAO.getCategoryById(id);
    }
    public boolean isNameExist(String name) throws Exception {
        return categoryDAO.isNameExist(name);
    }
}
