package gotcha.dao;

import gotcha.common.DBConnector;
import gotcha.dto.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    public List<Participant> getParticipants(int classId, int writerId) {
        List<Participant> result = new ArrayList<>();
        String sql = "SELECT u.user_id, u.nickname " +
                     "FROM participation p " +
                     "JOIN user u ON p.user_id = u.user_id " +
                     "WHERE p.class_id = ? AND u.user_id != ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setInt(2, writerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Participant(
                    rs.getInt("user_id"),
                    rs.getString("nickname")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
