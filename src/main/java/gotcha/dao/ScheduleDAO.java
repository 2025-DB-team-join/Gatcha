package gotcha.dao;

import gotcha.common.DBConnector;
import java.sql.*;
import java.util.*;

public class ScheduleDAO {
	
	public static class ScheduleInfo {
        public final String dayOfWeek;
        public final Time startTime;
        public final int duration;

        public ScheduleInfo(String dayOfWeek, Time startTime, int duration) {
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.duration = duration;
        }
    }

    public List<ScheduleInfo> getSchedulesByClassId(int classId) {
        List<ScheduleInfo> schedules = new ArrayList<>();
        String sql = "SELECT day_of_week, start_time, duration FROM schedule WHERE class_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                schedules.add(new ScheduleInfo(
                    rs.getString("day_of_week"),
                    rs.getTime("start_time"),
                    rs.getInt("duration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public boolean insertSchedule(int classId, String dayOfWeek, Time startTime, int duration) {
        String sql = "INSERT INTO schedule (class_id, day_of_week, start_time, duration) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, classId);
            pstmt.setString(2, dayOfWeek);
            pstmt.setTime(3, startTime);
            pstmt.setInt(4, duration);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getDaysByClassId(int classId) {
        List<String> days = new ArrayList<>();
        String sql = "SELECT day_of_week FROM schedule WHERE class_id = ? AND deleted_at IS NULL";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                days.add(rs.getString("day_of_week"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return days;
    }

    public boolean softDeleteSchedulesByClassId(int classId) {
        String sql = "UPDATE schedule SET deleted_at = NOW() WHERE class_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSchedulesByClassId(int classId) {
        String sql = "DELETE FROM schedule WHERE class_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
