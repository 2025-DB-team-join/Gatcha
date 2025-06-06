package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    public boolean login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM user WHERE email = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // 중복된 것으로 간주
        }
    }

    public boolean register(String username, String nickname, String password, String email, String gender, String region, Integer birthyear) {
        String sql = "INSERT INTO user (username, nickname, password, email, gender, region, birthyear) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, nickname);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setString(5, gender);
            stmt.setString(6, region);
            if (birthyear != null) stmt.setInt(7, birthyear);
            else stmt.setNull(7, Types.INTEGER);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int findUserIdByEmail(String email) {
        String sql = "SELECT user_id FROM user WHERE email = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Map<String, Object> getUserInfo(int userId) {
        Map<String, Object> userInfo = new HashMap<>();

        String sql =
                "SELECT user_id, username, nickname, email, birthyear, gender, region, registered_at " +
                        "FROM user WHERE user_id = ? AND deleted_at IS NULL";

        try (
                Connection conn = DBConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                userInfo.put("user_id", rs.getInt("user_id"));
                userInfo.put("username", rs.getString("username"));
                userInfo.put("nickname", rs.getString("nickname"));
                userInfo.put("email", rs.getString("email"));
                userInfo.put("birthyear", rs.getInt("birthyear"));
                userInfo.put("gender", rs.getString("gender"));
                userInfo.put("region", rs.getString("region"));
                userInfo.put("registered_at", rs.getTimestamp("registered_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public void deleteUser(int userId) {
        String sql = "UPDATE user SET deleted_at = NOW() WHERE user_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPassword(int userId, String inputPassword) {
        String sql = "SELECT password FROM user WHERE user_id = ? AND deleted_at IS NULL";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String realPassword = rs.getString("password");
                return realPassword.equals(inputPassword); // 단순 비교 (암호화는 실제론 필요!)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE user_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // 성공시 true 반환

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserInfo(int userId, String newNickname, String newEmail, String newRegion) {
        String sql = "UPDATE user SET nickname = ?, email = ?, region = ?, updated_at = NOW() WHERE user_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newNickname);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, newRegion);
            pstmt.setInt(4, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // 업데이트가 되면 true 반환!

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 참여 중인 소모임 목록 조회
    public List<Map<String, Object>> getParticipatedClasses(int userId) {
        List<Map<String, Object>> classList = new ArrayList<>();
        String sql = "SELECT c.class_id, c.title, s.day_of_week, s.start_time, s.duration " +
                "FROM participation p " +
                "JOIN class c ON p.class_id = c.class_id " +
                "LEFT JOIN schedule s ON c.class_id = s.class_id " +
                "WHERE p.user_id = ? " +
                "ORDER BY c.class_id";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("class_id", rs.getInt("class_id"));
                row.put("title", rs.getString("title"));
                row.put("day_of_week", rs.getString("day_of_week"));
                row.put("start_time", rs.getTime("start_time"));
                row.put("duration", rs.getInt("duration"));
                classList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList;
    }

    public List<Map<String, Object>> getParticipantsByClassId(int classId) {
        List<Map<String, Object>> participants = new ArrayList<>();
        String sql = "SELECT u.nickname, u.email, p.joined_at, p.absent " +
                "FROM participation p " +
                "JOIN user u ON p.user_id = u.user_id " +
                "WHERE p.class_id = ? AND p.deleted_at IS NULL";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("nickname", rs.getString("nickname"));
                row.put("email", rs.getString("email"));
                row.put("joined_at", rs.getDate("joined_at"));
                row.put("absent", rs.getInt("absent"));
                participants.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    public boolean increaseAbsentByEmail(int classId, String email) {
        String sql = "UPDATE participation p " +
                "JOIN user u ON p.user_id = u.user_id " +
                "SET p.absent = p.absent + 1 " +
                "WHERE p.class_id = ? AND u.email = ? AND p.deleted_at IS NULL";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setString(2, email);
            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}