package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HostedClassDAO {
    public static class HostedClass {
        private int classId;
        private String title;
        private String category;
        private String region;
        private String days;
        private int userCount;
        private String status;

        public HostedClass(int classId, String title, String category, String region, String days, int userCount, String status) {
            this.classId = classId;
            this.title = title;
            this.category = category;
            this.region = region;
            this.days = days;
            this.userCount = userCount;
            this.status = status;
        }
        public int getClassId() { return classId; }
        public String getTitle() { return title; }
        public String getCategory() { return category; }
        public String getRegion() { return region; }
        public String getDays() { return days; }
        public int getUserCount() { return userCount; }
        public String getStatus() { return status; }
    }

    public List<HostedClass> getMyHostedClasses(int hostId) {
        List<HostedClass> result = new ArrayList<>();
        String sql =
            "SELECT c.class_id, c.title, c.category, c.main_region, " +
            "  GROUP_CONCAT(DISTINCT CASE s.day_of_week " +
            "      WHEN 'Mon' THEN '월' WHEN 'Tues' THEN '화' WHEN 'Wed' THEN '수' " +
            "      WHEN 'Thur' THEN '목' WHEN 'Fri' THEN '금' WHEN 'Sat' THEN '토' WHEN 'Sun' THEN '일' " +
            "      ELSE s.day_of_week END " +
            "      ORDER BY FIELD(s.day_of_week, 'Mon','Tues','Wed','Thur','Fri','Sat','Sun') SEPARATOR ', ' " +
            "    ) AS days, " +
            "  IFNULL(u.sum_user_count, 0) AS user_count, c.status " +
            "FROM class c " +
            "JOIN user u2 ON c.host_id = u2.user_id " +
            "LEFT JOIN schedule s ON c.class_id = s.class_id " +
            "LEFT JOIN (SELECT class_id, SUM(user_count) AS sum_user_count FROM class_user_cube GROUP BY class_id) u ON c.class_id = u.class_id " +
            "WHERE c.host_id = ? " +
            "GROUP BY c.class_id, c.title, c.category, c.main_region, c.status, u.sum_user_count " +
            "ORDER BY FIELD(c.status, '진행중', '모집중', '진행완료'), c.class_id";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, hostId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new HostedClass(
                    rs.getInt("class_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("main_region"),
                    rs.getString("days"),
                    rs.getInt("user_count"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
