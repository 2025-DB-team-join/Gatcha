package gotcha.service;

import gotcha.dao.CategoryDAO;
import java.util.List;

public class CategoryService {
    private CategoryDAO dao = new CategoryDAO();

    public List<CategoryDAO.CategoryClassDays> searchClassesWithDaysByCategory(String category) {
        return dao.getClassesWithDaysByCategory(category);
    }
}
