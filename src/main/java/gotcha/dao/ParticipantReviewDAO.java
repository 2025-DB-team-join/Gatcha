package gotcha.dao;

import gotcha.common.DBConnector;
import gotcha.dto.ParticipantReview;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantReviewDAO {

    // 특정 참여자(targetId)에 대해 받은 모든 리뷰 조회
    public List<ParticipantReview> getReviewsForParticipant(int classId, int targetId) {
        List<ParticipantReview> result = new ArrayList<>();
        String sql = "SELECT review_id, rating, content FROM review " +
                     "WHERE class_id = ? AND target_id = ? " +
                     "ORDER BY review_id DESC";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setInt(2, targetId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new ParticipantReview(
                    rs.getInt("review_id"),
                    rs.getFloat("rating"),
                    rs.getString("content")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 특정 참여자에 대해 내가 쓴 리뷰 1개 조회
    public ParticipantReview getReview(int classId, int writerId, int targetId) {
        String sql = "SELECT review_id, rating, content FROM review " +
                     "WHERE class_id = ? AND writer_id = ? AND target_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setInt(2, writerId);
            pstmt.setInt(3, targetId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new ParticipantReview(
                    rs.getInt("review_id"),
                    rs.getFloat("rating"),
                    rs.getString("content")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 리뷰 작성
    public boolean writeReview(int classId, int writerId, int targetId, float rating, String content) {
        String sql = "INSERT INTO review(class_id, writer_id, target_id, rating, content) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setInt(2, writerId);
            pstmt.setInt(3, targetId);
            pstmt.setFloat(4, rating);
            pstmt.setString(5, content);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 리뷰 수정
    public boolean updateReview(int reviewId, float rating, String content) {
        String sql = "UPDATE review SET rating = ?, content = ? WHERE review_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, rating);
            pstmt.setString(2, content);
            pstmt.setInt(3, reviewId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 리뷰 삭제
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM review WHERE review_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 특정 class, writer, target 조합의 리뷰 존재 여부 확인
    public boolean reviewExists(int classId, int writerId, int targetId) {
        String sql = "SELECT COUNT(*) FROM review " +
                     "WHERE class_id = ? AND writer_id = ? AND target_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setInt(2, writerId);
            pstmt.setInt(3, targetId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

