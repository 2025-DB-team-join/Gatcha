package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;

public class BoardWriteDAO{
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
}