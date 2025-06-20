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
        private String context; // 추가
        private String region;
        private String days;
        private int userCount;
        private int maxP; // 추가 
        private String status;
        private Timestamp deadline; // 추가
        private String hostEmail;
        
        public HostedClass(int classId, String title, String category, String context, String region,
                           int maxP, String days, int userCount, String status, Timestamp deadline) {
            this.classId = classId;
            this.title = title;
            this.category = category;
            this.context = context;
            this.region = region;
            this.maxP = maxP;
            this.days = days;
            this.userCount = userCount;
            this.status = status;
            this.deadline = deadline; 
        }

        public int getClassId() { return classId; }
        public String getTitle() { return title; }
        public String getCategory() { return category; }
        public String getContext() { return context; }
        public String getRegion() { return region; }
        public int getMax() { return maxP; }
        public String getDays() { return days; }
        public int getUserCount() { return userCount; }
        public String getStatus() { return status; }
        public Timestamp getDeadline() { return deadline; } // getter 추가
        public String getHostEmail() {
            return hostEmail;
        }

        public void setHostEmail(String hostEmail) {
            this.hostEmail = hostEmail;
        }
    }

    public List<HostedClass> getMyHostedClasses(int hostId) {
        List<HostedClass> result = new ArrayList<>();
        String sql =
        	    "SELECT c.class_id, c.title, c.category, c.context, c.main_region, c.max_participants, " +
        	    "  c.recruit_deadline, " +
        	    "  GROUP_CONCAT(DISTINCT CASE s.day_of_week " +
        	    "      WHEN 'Mon' THEN '월' WHEN 'Tues' THEN '화' WHEN 'Wed' THEN '수' " +
        	    "      WHEN 'Thur' THEN '목' WHEN 'Fri' THEN '금' WHEN 'Sat' THEN '토' WHEN 'Sun' THEN '일' " +
        	    "      ELSE s.day_of_week END " +
        	    "      ORDER BY FIELD(s.day_of_week, 'Mon','Tues','Wed','Thur','Fri','Sat','Sun') SEPARATOR ', ' " +
        	    "    ) AS days, " +
        	    "  IFNULL(u.user_count, 0) AS user_count, c.status " +
        	    "FROM class c " +
        	    "JOIN user u2 ON c.host_id = u2.user_id " +
        	    "LEFT JOIN schedule s ON c.class_id = s.class_id " +
        	    "LEFT JOIN ( " +
        	    "    SELECT class_id, user_count " +
        	    "    FROM class_user_cube " +
        	    "    WHERE gender IS NULL AND region IS NULL " +
        	    ") u ON c.class_id = u.class_id " + 
        	    "WHERE c.host_id = ? AND c.deleted_at IS NULL " +
        	    "GROUP BY c.class_id, c.title, c.category, c.context, c.main_region, c.status, c.recruit_deadline, u.user_count " +
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
                        rs.getString("context"),
                        rs.getString("main_region"),
                        rs.getInt("max_participants"),
                        rs.getString("days"),
                        rs.getInt("user_count"),
                        rs.getString("status"),
                        rs.getTimestamp("recruit_deadline")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public HostedClass getHostedClassById(int classId) {
        String sql =
                "SELECT c.class_id, c.title, c.category, c.context, c.main_region, c.max_participants, " +
                        "  c.recruit_deadline, " +
                        "  GROUP_CONCAT(DISTINCT CASE s.day_of_week " +
                        "      WHEN 'Mon' THEN '월' WHEN 'Tues' THEN '화' WHEN 'Wed' THEN '수' " +
                        "      WHEN 'Thur' THEN '목' WHEN 'Fri' THEN '금' WHEN 'Sat' THEN '토' WHEN 'Sun' THEN '일' " +
                        "      ELSE s.day_of_week END " +
                        "      ORDER BY FIELD(s.day_of_week, 'Mon','Tues','Wed','Thur','Fri','Sat','Sun') SEPARATOR ', ' " +
                        "    ) AS days, " +
                        "  IFNULL(u1.sum_user_count, 0) AS user_count, c.status, u2.email AS host_email " +  // host_email 추가
                        "FROM class c " +
                        "JOIN user u2 ON c.host_id = u2.user_id " +  // 주최자 정보 조인
                        "LEFT JOIN schedule s ON c.class_id = s.class_id " +
                        "LEFT JOIN ( " +
                        "  SELECT class_id, SUM(user_count) AS sum_user_count " +
                        "  FROM class_user_cube " +
                        "  WHERE gender IS NULL AND region IS NULL " + // 원하는 조건에 맞게 유지
                        "  GROUP BY class_id " +
                        ") u1 ON c.class_id = u1.class_id " +
                        "WHERE c.class_id = ? AND c.deleted_at IS NULL " +
                        "GROUP BY c.class_id, c.title, c.category, c.context, c.main_region, c.status, c.recruit_deadline, u1.sum_user_count, u2.email";

        try (
                Connection conn = DBConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                HostedClass hc = new HostedClass(
                        rs.getInt("class_id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("context"),
                        rs.getString("main_region"),
                        rs.getInt("max_participants"),
                        rs.getString("days"),
                        rs.getInt("user_count"),
                        rs.getString("status"),
                        rs.getTimestamp("recruit_deadline")
                );
                hc.setHostEmail(rs.getString("host_email"));  // ⬅️ 주최자 이메일 세팅
                return hc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean removeParticipant(int classId, String email) {
        String sql = "DELETE FROM participation WHERE class_id = ? AND user_id = (SELECT user_id FROM user WHERE email = ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setString(2, email);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
