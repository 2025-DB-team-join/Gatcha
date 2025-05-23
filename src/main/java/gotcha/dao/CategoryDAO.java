package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // 내부 클래스 데이터 모델
    public static class CategoryClass {
        private int classId;
        private String title;
        private String context;
        private String category;

        public CategoryClass(int classId, String title, String context, String category) {
            this.classId = classId;
            this.title = title;
            this.context = context;
            this.category = category;
        }

        public int getClassId() { return classId; }
        public String getTitle() { return title; }
        public String getContext() { return context; }
        public String getCategory() { return category; }
    }

    // DB에서 카테고리에 해당하는 클래스 리스트 조회
    public List<CategoryClass> getClassesByCategory(String category) {
        List<CategoryClass> classes = new ArrayList<>();
        String sql = "SELECT class_id, title, context, category FROM class WHERE category = ?";

        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CategoryClass c = new CategoryClass(
                    rs.getInt("class_id"),
                    rs.getString("title"),
                    rs.getString("context"),
                    rs.getString("category")
                );
                classes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 추후 로그로 대체 추천
        }

        return classes;
    }
}
