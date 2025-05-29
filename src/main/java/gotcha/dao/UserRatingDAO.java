package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;

public class UserRatingDAO {
    // 내가 참여한 클래스에서 받은 리뷰의 평균 평점 구하기
    public double getAverageRating(int userId) {
        String sql =
            "SELECT AVG(r.rating) AS avg_rating " +
            "FROM review r " +
            "WHERE r.target_id = ? " +
            "AND r.class_id IN (SELECT class_id FROM participation WHERE user_id = ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
