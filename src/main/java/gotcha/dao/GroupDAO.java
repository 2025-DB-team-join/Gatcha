package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

    public List<Vector<String>> getGroupsForAttendance(String keyword, String category) {
        List<Vector<String>> result = new ArrayList<>();

        // 오늘 날짜까지 스케줄 기준 실제 모임 횟수 산출 및 출석률 계산
        String sql =
                "SELECT c.class_id, c.title, c.category, " +
                        "       CONCAT(ROUND(((SUM(sc.진행_횟수) - SUM(p.absent)) / SUM(sc.진행_횟수)) * 100, 0), '%') AS 출석률, " +
                        "       SUM(sc.진행_횟수) AS 횟수 " +
                        "FROM class c " +
                        "JOIN participation p ON c.class_id = p.class_id " +
                        "JOIN (" +
                        "    SELECT s.class_id, " +
                        "           (" +
                        "               TIMESTAMPDIFF(WEEK, s.created_at, CURDATE()) + " +
                        "               IF(WEEKDAY(s.created_at) <= " +
                        "                  (CASE s.day_of_week " +
                        "                      WHEN 'Mon' THEN 0 " +
                        "                      WHEN 'Tues' THEN 1 " +
                        "                      WHEN 'Wed' THEN 2 " +
                        "                      WHEN 'Thur' THEN 3 " +
                        "                      WHEN 'Fri' THEN 4 " +
                        "                      WHEN 'Sat' THEN 5 " +
                        "                      WHEN 'Sun' THEN 6 " +
                        "                  END), 1, 0)" +
                        "           ) AS 진행_횟수 " +
                        "    FROM schedule s " +
                        "    WHERE s.deleted_at IS NULL " +
                        ") sc ON c.class_id = sc.class_id " +
                        "WHERE c.title LIKE ? ";

        if (!"전체".equals(category)) {
            sql += "AND c.category = ? ";
        }

        sql += "GROUP BY c.class_id, c.title, c.category ";
        sql += "ORDER BY 출석률 DESC";

        try (Connection conn = gotcha.common.DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            if (!"전체".equals(category)) {
                ps.setString(2, category);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("title"));       // 소모임 이름
                row.add(rs.getString("category"));    // 카테고리
                row.add(rs.getString("출석률"));       // 출석률
                row.add(rs.getString("횟수"));        // 실제 진행된 모임 횟수
                result.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Vector<String>> getGroupDetails(String keyword, String category) {
        List<Vector<String>> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT c.title, c.context, c.status, c.main_region " +
                        "FROM class c " +
                        "WHERE c.deleted_at IS NULL AND c.title LIKE ? "
        );
        if (!"전체".equals(category)) {
            sql.append("AND c.category = ? ");
        }
        sql.append("ORDER BY c.created_at DESC");

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setString(1, "%" + keyword + "%");
            if (!"전체".equals(category)) {
                ps.setString(2, category);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("title"));
                row.add(rs.getString("context"));
                row.add(rs.getString("status"));
                row.add(rs.getString("main_region"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
