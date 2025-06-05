package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserReviewDAO {
    public static class ReviewInfo {
        private String classTitle;
        private String comment;

        public ReviewInfo(String classTitle, String comment) {
            this.classTitle = classTitle;
            this.comment = comment;
        }
        public String getClassTitle() { return classTitle; }
        public String getComment() { return comment; }
    }

    public List<ReviewInfo> getReviewsAboutMe(int userId) {
        List<ReviewInfo> result = new ArrayList<>();
        String sql =
            "SELECT c.title AS class_title, r.content AS comment " +
            "FROM review r " +
            "JOIN class c ON r.class_id = c.class_id " +
            "WHERE r.target_id = ? " +
            "ORDER BY r.review_id DESC";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new ReviewInfo(
                    rs.getString("class_title"),
                    rs.getString("comment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
