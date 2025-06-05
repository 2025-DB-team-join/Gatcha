package gotcha.service;

import gotcha.common.DBConnector;
import java.sql.*;

public class JoinService {

    public boolean isAlreadyJoined(int userId, int classId) {
        String sql = "SELECT COUNT(*) FROM participation WHERE user_id = ? AND class_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, classId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int joinClass(int userId, int classId) {
        String sql = "INSERT INTO participation(user_id, class_id) VALUES (?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, classId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
