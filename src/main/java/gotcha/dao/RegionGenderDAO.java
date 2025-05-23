package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegionGenderDAO {
    public static class ClassRank {
        private String title;
        private String context;
        private String category;
        private String days;
        private int userCount;
        private int rank;

        public ClassRank(String title, String context, String category, String days, int userCount, int rank) {
            this.title = title;
            this.context = context;
            this.category = category;
            this.days = days;
            this.userCount = userCount;
            this.rank = rank;
        }
        public String getTitle() { return title; }
        public String getContext() { return context; }
        public String getCategory() { return category; }
        public String getDays() { return days; }
        public int getUserCount() { return userCount; }
        public int getRank() { return rank; }
    }

    public List<ClassRank> getRankedClassesWithDays(String region, String gender) {
        List<ClassRank> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT c.title, c.context, c.category, " +
            "GROUP_CONCAT(DISTINCT CASE s.day_of_week " +
            " WHEN 'Mon' THEN '월' WHEN 'Tues' THEN '화' WHEN 'Wed' THEN '수' " +
            " WHEN 'Thur' THEN '목' WHEN 'Fri' THEN '금' WHEN 'Sat' THEN '토' WHEN 'Sun' THEN '일' " +
            " ELSE s.day_of_week END " +
            " ORDER BY FIELD(s.day_of_week, 'Mon','Tues','Wed','Thur','Fri','Sat','Sun') SEPARATOR ', ') AS days, " +
            "c.user_count, " +
            "RANK() OVER (ORDER BY c.user_count DESC) AS ranking " +
            "FROM class_user_cube c " +
            "JOIN schedule s ON c.class_id = s.class_id "
        );
        // 조건
        if (!region.equals("전체") && gender != null) {
            sql.append("WHERE c.region = ? AND c.gender = ? ");
        } else if (!region.equals("전체")) {
            sql.append("WHERE c.region = ? AND c.gender IS NULL ");
        } else if (gender != null) {
            sql.append("WHERE c.region IS NULL AND c.gender = ? ");
        } else {
            sql.append("WHERE c.region IS NULL AND c.gender IS NULL ");
        }
        sql.append("GROUP BY c.class_id, c.title, c.context, c.category, c.user_count ");
        sql.append("ORDER BY ranking");

        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ) {
            int idx = 1;
            if (!region.equals("전체") && gender != null) {
                pstmt.setString(idx++, region);
                pstmt.setString(idx++, gender);
            } else if (!region.equals("전체")) {
                pstmt.setString(idx++, region);
            } else if (gender != null) {
                pstmt.setString(idx++, gender);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new ClassRank(
                    rs.getString("title"),
                    rs.getString("context"),
                    rs.getString("category"),
                    rs.getString("days"),
                    rs.getInt("user_count"),
                    rs.getInt("ranking")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
