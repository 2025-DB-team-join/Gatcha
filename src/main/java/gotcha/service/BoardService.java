package gotcha.service;

import gotcha.dao.BoardDAO;

import java.util.List;
import java.util.Map;

public class BoardService {
	private gotcha.dao.BoardDAO boardDAO = new BoardDAO();

	// 게시글 목록 가져오기
	public List<Map<String, Object>> getPostsByClassId(int classId) {
		return boardDAO.findPostsByClassId(classId);
	}

	// 게시글 등록 (호스트 체크 포함)
	public boolean createPostAndReturnStatus(int userId, int classId, String title, String context) {
		int hostId = boardDAO.findHostId(classId);
		if (hostId == -1 || userId != hostId) return false;
		return boardDAO.insertPost(userId, classId, title, context);
	}
	
	public int getHostId(int classId) {
		return boardDAO.findHostId(classId);
	}

	public String getClassTitleById(int classId) {
		return boardDAO.findClassTitleById(classId);
	}

	public Map<String, Object> getPostById(int boardId) {// 상세조회
		return boardDAO.findPostById(boardId);
	}
}