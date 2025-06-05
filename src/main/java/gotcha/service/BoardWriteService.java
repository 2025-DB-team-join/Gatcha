package gotcha.service;

import gotcha.dao.BoardWriteDAO;

public class BoardWriteService {
	private gotcha.dao.BoardWriteDAO boardWriteDAO = new BoardWriteDAO();
	
	public void createPost(int userId, int classId, String title, String context) {
		int hostId = boardWriteDAO.findHostId(classId);
		
		if (hostId == -1) {
			System.out.println("소모임이 존재하지 않습니다.");
			return;
		}
		
		if (userId != hostId) {
			System.out.println("게시글 작성 권한이 없습니다.");
			return;
		}
		
		boolean success = boardWriteDAO.insertPost(userId, classId, title, context);
		if (success) {
			System.out.println("게시글을 등록했습니다!");
		} else {
			System.out.println("게시글 등록을 실패했습니다.");
		}
	}
	
	public int getHostId(int classId) {
		return boardWriteDAO.findHostId(classId);
	}
	
	public boolean createPostAndReturnStatus(int userId, int classId, String title, String context) {
		int hostId = boardWriteDAO.findHostId(classId);
		if (hostId == -1 || userId != hostId) return false;
		return boardWriteDAO.insertPost(userId, classId, title, context);
	}
}