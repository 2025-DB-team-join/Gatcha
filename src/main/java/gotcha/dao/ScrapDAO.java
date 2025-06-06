package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ScrapDAO {
	public List<Vector<String>> getScrappedClass(int userId) {
		List<Vector<String>> result = new ArrayList<>();
		String sql = "SELECT c.class_id, c.title, c.context, c.main_region, c.status, c.recruit_deadline " +
	             "FROM scrap s " +
	             "JOIN class c ON s.class_id = c.class_id " +
	             "WHERE s.user_id = ? AND s.deleted_at IS NULL";
		
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Vector<String> row = new Vector<>();
				row.add(String.valueOf(rs.getInt("class_id")));
			    row.add(rs.getString("title"));
			    row.add(rs.getString("context"));
			    row.add(rs.getString("main_region"));
			    row.add(rs.getString("status"));
			    row.add(rs.getString("recruit_deadline"));
			    result.add(row);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean cancelScrap(int userId, int classId) {
	    String sql = "UPDATE scrap SET deleted_at = NOW() WHERE user_id = ? AND class_id = ?";

	    try (Connection conn = DBConnector.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        ps.setInt(2, classId);
	        int affectedRows = ps.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean addScrap(int userId, int classId) {
		String checkSql = "SELECT * FROM scrap WHERE user_id = ? AND class_id = ?";
		String insertSql = "INSERT INTO scrap (user_id, class_id, created_at) VALUES (?, ?, NOW())";
		String restoreSql = "UPDATE scrap SET deleted_at = NULL WHERE user_id = ? AND class_id = ?";

		try (Connection conn = DBConnector.getConnection();
			 PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

			checkStmt.setInt(1, userId);
			checkStmt.setInt(2, classId);
			ResultSet rs = checkStmt.executeQuery();

			if (rs.next()) {
				try (PreparedStatement restoreStmt = conn.prepareStatement(restoreSql)) {
					restoreStmt.setInt(1, userId);
					restoreStmt.setInt(2, classId);
					return restoreStmt.executeUpdate() > 0;
				}
			} else {
				try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
					insertStmt.setInt(1, userId);
					insertStmt.setInt(2, classId);
					return insertStmt.executeUpdate() > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


}