package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardDAO{
	private final Connection conn;

	public BoardDAO() {
		this.conn = DBConnector.getConnection();
	}

	public int findHostId(int classId) {
		String sql = "SELECT host_id FROM class WHERE class_id = ?";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, classId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) return rs.getInt("host_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean insertPost(int userId, int classId, String title, String context) {
		String sql = "Insert INTO board (user_id, class_id, title, context, created_at) VALUES (?, ?, ?, ?, NOW())";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, classId);
			stmt.setString(3, title);
			stmt.setString(4, context);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Map<String, Object>> findPostsByClassId(int classId) {
		List<Map<String, Object>> posts = new ArrayList<>();

		String sql = "SELECT b.board_id, b.title, u.nickname AS writer_nickname, b.created_at " +
				"FROM board b " +
				"JOIN user u ON b.user_id = u.user_id " +
				"WHERE b.class_id = ? AND b.deleted_at IS NULL " +
				"ORDER BY b.created_at DESC";

		try (Connection conn = DBConnector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, classId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Map<String, Object> post = new HashMap<>();
				post.put("board_id", rs.getInt("board_id"));
				post.put("title", rs.getString("title"));
				post.put("writer_nickname", rs.getString("writer_nickname"));
				post.put("created_at", rs.getTimestamp("created_at"));
				posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return posts;
	}

	public String findClassTitleById(int classId) {
		String sql = "SELECT title FROM class WHERE class_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, classId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("title");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "알 수 없음";
	}

	public Map<String, Object> findPostById(int boardId) {
		Map<String, Object> post = new HashMap<>();
		String sql = "SELECT b.board_id, b.title, b.context, b.created_at, b.class_id, u.nickname AS writer " +
				"FROM board b JOIN user u ON b.user_id = u.user_id " +
				"WHERE b.board_id = ? AND b.deleted_at IS NULL";

		try (Connection conn = DBConnector.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, boardId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				post.put("board_id", rs.getInt("board_id"));
				post.put("title", rs.getString("title"));
				post.put("context", rs.getString("context"));
				post.put("created_at", rs.getTimestamp("created_at"));
				post.put("class_id", rs.getInt("class_id"));
				post.put("writer", rs.getString("writer"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return post;
	}

}