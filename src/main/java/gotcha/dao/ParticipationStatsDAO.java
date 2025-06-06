package gotcha.dao;
import gotcha.common.DBConnector;
import gotcha.dto.RegionGenderCount;
import java.sql.*;
import java.util.*;

public class ParticipationStatsDAO {
    public List<RegionGenderCount> getRegionGenderStats(int classId) {
        List<RegionGenderCount> list = new ArrayList<>();
        String sql =
            "SELECT region, " +
            "SUM(CASE WHEN gender = 'M' THEN user_count ELSE 0 END) AS male_count, " +
            "SUM(CASE WHEN gender = 'F' THEN user_count ELSE 0 END) AS female_count, " +
            "SUM(CASE WHEN gender = 'Other' THEN user_count ELSE 0 END) AS other_count " +
            "FROM class_user_cube " +
            "WHERE class_id = ? AND region IS NOT NULL " +
            "GROUP BY region " +
            "HAVING male_count > 0 OR female_count > 0 OR other_count > 0 " +
            "ORDER BY region";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String region = rs.getString("region");
                int maleCount = rs.getInt("male_count");
                int femaleCount = rs.getInt("female_count");
                int otherCount = rs.getInt("other_count");
                list.add(new RegionGenderCount(region, maleCount, femaleCount, otherCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
