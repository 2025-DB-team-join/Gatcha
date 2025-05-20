package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagDAO {
    public List<String> fetchTagNames() {
        List<String> tags = new ArrayList<>();
        String sql = "SELECT name FROM tag ORDER BY tag_id";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tags.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    public boolean insertUserTags(int userId, List<Integer> tagIds) {
        String sql = "INSERT INTO usertag (user_id, tag_id) VALUES (?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int tagId : tagIds) {
                stmt.setInt(1, userId);
                stmt.setInt(2, tagId);
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
