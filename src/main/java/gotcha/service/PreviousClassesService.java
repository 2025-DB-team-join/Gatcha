package gotcha.service;

import gotcha.dao.PreviousClassesDAO;
import java.util.List;
import java.util.Vector;

public class PreviousClassesService {
	private PreviousClassesDAO previousClassesDAO = new PreviousClassesDAO();
	
	public List<Vector<String>> getPreviousClasses(int userId) {
		return previousClassesDAO.getPreviousClasses(userId);
	}
}