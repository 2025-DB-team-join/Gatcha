package gotcha.service;

import gotcha.dao.CurrentGroupDAO;
import java.util.List;

public class CurrentGroupService {
    private CurrentGroupDAO dao = new CurrentGroupDAO();

    public List<CurrentGroupDAO.CurrentGroup> getCurrentGroups(int userId) {
        return dao.getCurrentGroups(userId);
    }
    
    public boolean cancelParticipation(int userId, int classId) {
    	return dao.removeParticipation(userId, classId);
    }
}
