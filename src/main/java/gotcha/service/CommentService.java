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

    public boolean addReply(int boardId, int userId, String content, int parentId) {
        System.out.println("📥 Reply Insert: boardId=" + boardId + ", parentId=" + parentId + ", content=" + content);
        return commentDAO.insertReply(boardId, userId, content, parentId);
    }

    public boolean updateComment(int commentId, String content) {
        return new CommentDAO().updateComment(commentId, content);
    }

    public boolean deleteComment(int commentId) {
        return new CommentDAO().deleteComment(commentId);
    }


}
