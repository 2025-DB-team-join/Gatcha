package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrentGroupDAO {
    public static class CurrentGroup {
        private int classId;
        private String title;
        private String category;
        private String region;
        private String hostNickname;
        private String days;

        public CurrentGroup(int classId, String title, String category, String region, String hostNickname, String days) {
            this.classId = classId;
            this.title = title;
            this.category = category;
            this.region = region;
            this.hostNickname = hostNickname;
            this.days = days;
        }
        public int getClassId() { return classId; }
        public String getTitle() { return title; }
        public String getCategory() { return category; }
        public String getRegion() { return region; }
        public String getHostNickname() { return hostNickname; }
        public String getDays() { return days; }
    }

    public List<CurrentGroup> getCurrentGroups(int userId) {
        List<CurrentGroup> result = new ArrayList<>();
        String sql =
            "SELECT c.class_id, c.title, c.category, c.main_region, u.nickname AS host_nickname, " +
            "  (SELECT GROUP_CONCAT(DISTINCT CASE s.day_of_week " +
            "      WHEN 'Mon' THEN '월' WHEN 'Tues' THEN '화' WHEN 'Wed' THEN '수' " +
            "      WHEN 'Thur' THEN '목' WHEN 'Fri' THEN '금' WHEN 'Sat' THEN '토' WHEN 'Sun' THEN '일' " +
            "      ELSE s.day_of_week END " +
            "      ORDER BY FIELD(s.day_of_week, 'Mon','Tues','Wed','Thur','Fri','Sat','Sun') SEPARATOR ', ' " +
            "    ) FROM schedule s WHERE s.class_id = c.class_id) AS days " +
            "FROM participation p " +
            "JOIN class c ON p.class_id = c.class_id " +
            "JOIN user u ON c.host_id = u.user_id " +
            "WHERE p.user_id = ? AND c.status = '진행중'";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new CurrentGroup(
                    rs.getInt("class_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("main_region"),
                    rs.getString("host_nickname"),
                    rs.getString("days")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean removeParticipation(int userId, int classId) {
    	String sql = "DELETE FROM participation WHERE user_id = ? AND class_id = ?";
    	
    	try (Connection conn = DBConnector.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		pstmt.setInt(1, userId);
    		pstmt.setInt(2, classId);
    		int affected = pstmt.executeUpdate();
    		return affected > 0;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
}
