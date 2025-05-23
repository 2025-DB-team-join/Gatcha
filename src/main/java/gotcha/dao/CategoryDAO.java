package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // 내부 데이터 모델 클래스
    public static class CategoryClassDays {
        private int classId;
        private String title;
        private String context;
        private String days;

        public CategoryClassDays(int classId, String title, String context, String days) {
            this.classId = classId;
            this.title = title;
            this.context = context;
            this.days = days;
        }

        public int getClassId() { return classId; }
        public String getTitle() { return title; }
        public String getContext() { return context; }
        public String getDays() { return days; }
    }

    // 카테고리별 클래스+요일 리스트 조회
    public List<CategoryClassDays> getClassesWithDaysByCategory(String category) {
        List<CategoryClassDays> result = new ArrayList<>();
        String sql =
        	    "SELECT c.class_id, c.title, c.context, " +
        	    "GROUP_CONCAT(CASE s.day_of_week " +
        	    " WHEN 'Mon' THEN '월' WHEN 'Tues' THEN '화' WHEN 'Wed' THEN '수' " +
        	    " WHEN 'Thur' THEN '목' WHEN 'Fri' THEN '금' WHEN 'Sat' THEN '토' WHEN 'Sun' THEN '일' " +
        	    " ELSE s.day_of_week END " +
        	    " ORDER BY FIELD(s.day_of_week, 'Mon','Tues','Wed','Thur','Fri','Sat','Sun') SEPARATOR ', ') AS days " +
        	    "FROM class c " +
        	    "JOIN schedule s ON c.class_id = s.class_id " +
        	    "WHERE c.category = ? " +
        	    "GROUP BY c.class_id, c.title, c.context " +
        	    "ORDER BY c.title";



        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new CategoryClassDays(
                    rs.getInt("class_id"),
                    rs.getString("title"),
                    rs.getString("context"),
                    rs.getString("days")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
