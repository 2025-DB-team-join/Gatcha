package gotcha.service;

import gotcha.dao.ScrapDAO;
import java.util.List;
import java.util.Vector;

public class ScrapService {
	private ScrapDAO scrapDAO;
	
	public ScrapService() {
		scrapDAO = new ScrapDAO();
	}
	
	public List<Vector<String>> getScrappedClasses(int userId) {
		return scrapDAO.getScrappedClass(userId);
	}

	public boolean cancelScrap(int userId, int classId) {
        return scrapDAO.cancelScrap(userId, classId);
    }

	public boolean addScrap(int userId, int classId) {
		return scrapDAO.addScrap(userId, classId);
	}
}