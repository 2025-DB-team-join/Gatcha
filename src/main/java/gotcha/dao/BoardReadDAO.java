package gotcha.dao;
import gotcha.common.DBConnector;

import java.sql.*;
import java.util.*;

public class BoardReadDAO {
	public List<Map<String, Object>> getBoards(int userId) {
		String sql = "SELECT b.board_id, b.title, b.context, b.created_at, c.class_id "
				+ "FROM board b "
				+ "JOIN class c ON b.class_id = c.class_id "
				+ "JOIN participation p ON b.class_id=p.class_id "
				+ "WHERE p.user_id = ? "
				+ "ORDER BY b.created_at DESC";
		List<Map<String, Object>> boardList = new ArrayList<>();
		
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Map<String, Object> board = new HashMap<>();
				board.put("board_id", rs.getInt("board_id"));
				board.put("title", rs.getString("title"));
				board.put("context", rs.getString("context"));
				board.put("created_at", rs.getTimestamp("created_at"));
				board.put("class_id", rs.getString("class_id"));
				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boardList;
	}
}