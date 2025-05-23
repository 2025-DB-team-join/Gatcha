package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;

public class BoardDAO{
	public int findHostId(int gatheringId) {
		String sql = "SELECT host_id FROM gathering WHERE gathering_id = ?";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, gatheringId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) return rs.getInt("host_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean insertPost(int userId, int gatheringId, String title, String content) {
		String sql = "Insert INTO board (user_id, gathering_id, title, content, created_at) VALUES (?, ?, ?, ?, NOW())";
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, gatheringId);
			stmt.setString(3, title);
			stmt.setString(4, content);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}