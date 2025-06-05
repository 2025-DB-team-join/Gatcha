package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;
import java.util.*;

public class CommentDAO {

    public List<Map<String, Object>> findCommentsByBoardId(int boardId) {
        List<Map<String, Object>> comments = new ArrayList<>();
        String sql = "SELECT c.comment_id, c.content, c.created_at, c.parent_id, u.nickname " +
                "FROM comment c JOIN user u ON c.user_id = u.user_id " +
                "WHERE c.board_id = ? AND c.deleted_at IS NULL " +
                "ORDER BY c.created_at ASC";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, boardId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> comment = new HashMap<>();
                comment.put("comment_id", rs.getInt("comment_id"));
                comment.put("content", rs.getString("content"));
                comment.put("created_at", rs.getTimestamp("created_at"));
                comment.put("nickname", rs.getString("nickname"));
                comment.put("parent_id", rs.getObject("parent_id"));  // ✅ 여기!!
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }


    public boolean insertComment(int boardId, int userId, String content) {
        String sql = "INSERT INTO comment (board_id, user_id, content) VALUES (?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, boardId);
            stmt.setInt(2, userId);
            stmt.setString(3, content);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean insertReply(int boardId, int userId, String content, int parentId) {
        String sql = "INSERT INTO comment (board_id, user_id, content, parent_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, boardId);
            stmt.setInt(2, userId);
            stmt.setString(3, content);
            stmt.setInt(4, parentId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateComment(int commentId, String content) {
        String sql = "UPDATE comment SET content = ? WHERE comment_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, content);
            stmt.setInt(2, commentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComment(int commentId) {
        String sql = "UPDATE comment SET deleted_at = CURRENT_TIMESTAMP WHERE comment_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
