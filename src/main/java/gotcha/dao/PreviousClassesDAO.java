package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PreviousClassesDAO {
	public List<Vector<String>> getPreviousClasses(int userId) {
		List<Vector<String>> result = new ArrayList<>();
		String sql = "SELECT c.class_id, c.title, c.context " + "FROM participation p " + "JOIN class c ON p.class_id = c.class_id " + "WHERE p.user_id = ? " + "AND c.status = '진행완료' " + "AND p.deleted_at IS NULL " + "AND c.deleted_at IS NULL";
		
		try (Connection conn = DBConnector.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Vector<String> row = new Vector<>();
				row.add(rs.getString("title"));
				row.add(rs.getString("context"));
				result.add(row);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}