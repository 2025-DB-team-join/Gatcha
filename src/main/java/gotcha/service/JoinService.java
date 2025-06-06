package gotcha.service;

import gotcha.dao.GroupDAO;

import java.sql.Timestamp;

public class JoinService {

    private final GroupDAO dao = new GroupDAO();

    public boolean isAlreadyJoined(int userId, int classId) {
        return dao.isAlreadyJoined(userId, classId);
    }

    public int joinClass(int userId, int classId) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return dao.insertParticipation(userId, classId, now);
    }
}
