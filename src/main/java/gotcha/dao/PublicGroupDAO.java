package gotcha.dao;

import gotcha.common.DBConnector;
import gotcha.dto.PublicGroup;

import java.sql.*;

public class PublicGroupDAO {
    public PublicGroup getPublicGroupById(int classId) {
        String sql =
            "SELECT c.class_id, c.host_id, c.title, c.category, c.context, c.main_region, u.nickname AS host_nickname, " +
            "  (SELECT GROUP_CONCAT(DISTINCT CASE s.day_of_week " +
            "      WHEN 'Mon' THEN '월' WHEN 'Tues' THEN '화' WHEN 'Wed' THEN '수' " +
            "      WHEN 'Thur' THEN '목' WHEN 'Fri' THEN '금' WHEN 'Sat' THEN '토' WHEN 'Sun' THEN '일' " +
            "      ELSE s.day_of_week END " +
            "      ORDER BY FIELD(s.day_of_week, 'Mon','Tues','Wed','Thur','Fri','Sat','Sun') SEPARATOR ', ' " +
            "    ) FROM schedule s WHERE s.class_id = c.class_id) AS days, " +
            "  (SELECT COUNT(*) FROM participation p WHERE p.class_id = c.class_id) AS user_count, " +
            "  c.max_participants, c.status " +
            "FROM class c " +
            "JOIN user u ON c.host_id = u.user_id " +
            "WHERE c.class_id = ?";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new PublicGroup(
                    rs.getInt("class_id"),
                    rs.getInt("host_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("context"),
                    rs.getString("main_region"),
                    rs.getString("host_nickname"),
                    rs.getString("days"),
                    rs.getInt("user_count"),
                    rs.getInt("max_participants"),  
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
