package gotcha.service;

import gotcha.dao.CategoryDAO;
import gotcha.dao.CategoryDAO.CategoryClass;

import java.util.List;

public class CategoryService {
    private CategoryDAO dao = new CategoryDAO();

    public List<CategoryClass> searchClassesByCategory(String category) {
        return dao.getClassesByCategory(category);
    }
}
