package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // 주최자 리뷰 정보를 담는 내부 클래스
    public static class HostReview {
        private String reviewerName; // 리뷰 작성자 이름
        private float rating;
        private String content;      // 리뷰 내용

        public HostReview(String reviewerName, float rating, String content) {
            this.reviewerName = reviewerName;
            this.rating = rating;
            this.content = content;
        }

        public String getReviewerName() { return reviewerName; }
        public float getRating() { return rating; }
        public String getContent() { return content; }
    }

    // 특정 classId에 해당하는 주최자에 대한 리뷰들을 반환
    public List<HostReview> getHostReviewsByClassId(int classId) {
        List<HostReview> result = new ArrayList<>();
        String sql =
            "SELECT u.nickname AS reviewer, r.rating, r.content AS comment " +
            "FROM review r " +
            "JOIN user u ON r.writer_id = u.user_id " +
            "WHERE r.class_id = ? AND r.target_id = (" +
            "    SELECT host_id FROM class WHERE class_id = ?" +
            ")";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setInt(2, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new HostReview(
                    rs.getString("reviewer"),
                    rs.getFloat("rating"),
                    rs.getString("comment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}