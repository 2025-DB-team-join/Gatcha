package gotcha.service;
import gotcha.dao.BoardReadDAO;

import java.util.List;
import java.util.Map;

public class BoardReadService {
	private BoardReadDAO boardReadDAO;
	
	public BoardReadService() {
		boardReadDAO = new BoardReadDAO();
	}
	
	public List<Map<String, Object>> getBoardList(int userId) {
		return boardReadDAO.getBoards(userId);
	}
}