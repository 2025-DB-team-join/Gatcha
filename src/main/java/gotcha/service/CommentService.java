package gotcha.service;

import gotcha.dao.CommentDAO;

import java.util.List;
import java.util.Map;

public class CommentService {
    private final CommentDAO commentDAO = new CommentDAO();

    public List<Map<String, Object>> getCommentsByBoardId(int boardId) {
        return commentDAO.findCommentsByBoardId(boardId);
    }

    public boolean addComment(int boardId, int userId, String content) {
        return commentDAO.insertComment(boardId, userId, content);
    }
}
