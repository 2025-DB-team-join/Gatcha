package gotcha.service;

import gotcha.dao.BoardDAO;

public class BoardService {
	private gotcha.dao.BoardDAO boardDAO = new BoardDAO();
	
	public void createPost(int userId, int gatheringId, String title, String content) {
		int hostId = boardDAO.findHostId(gatheringId);
		
		if (hostId == -1) {
			System.out.println("소모임이 존재하지 않습니다.");
			return;
		}
		
		if (userId != hostId) {
			System.out.println("게시글 작성 권한이 없습니다.");
			return;
		}
		
		boolean success = boardDAO.insertPost(userId, gatheringId, title, content);
		if (success) {
			System.out.println("게시글을 등록했습니다!");
		} else {
			System.out.println("게시글 등록을 실패했습니다.");
		}
	}
}