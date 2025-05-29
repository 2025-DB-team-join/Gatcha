package gotcha.dao;

import gotcha.common.DBConnector;

import java.sql.*;
import java.util.HashMap;
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

}
