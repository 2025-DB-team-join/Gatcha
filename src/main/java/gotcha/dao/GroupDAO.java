package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;

public class GroupDAO {

    public boolean insertGroup(
            int hostId,
            String title,
            String context,
            int maxParticipants,
            String mainRegion,
            String category,
            Timestamp recruitDeadline,
            String status
    ) {
        String sql = "INSERT INTO class " +
                "(host_id, title, context, max_participants, main_region, category, recruit_deadline, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hostId);
            stmt.setString(2, title);
            stmt.setString(3, context);
            stmt.setInt(4, maxParticipants);
            stmt.setString(5, mainRegion);
            stmt.setString(6, category);

            if (recruitDeadline != null) {
                stmt.setTimestamp(7, recruitDeadline);
            } else {
                stmt.setNull(7, Types.TIMESTAMP);
            }
            stmt.setString(8, status);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
