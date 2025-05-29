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
				//row.add(String.valueOf(rs.getInt("class_Id")));
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
}