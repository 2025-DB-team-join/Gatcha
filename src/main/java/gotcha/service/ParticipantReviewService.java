package gotcha.service;

import gotcha.dao.ParticipantReviewDAO;
import gotcha.dto.ParticipantReview; 
import java.util.List;

public class ParticipantReviewService {

    private ParticipantReviewDAO dao = new ParticipantReviewDAO();

    public List<ParticipantReview> getReviewsForParticipant(int classId, int targetId) {
        return dao.getReviewsForParticipant(classId, targetId);
    }

    public boolean writeReview(int classId, int writerId, int targetId, float rating, String content) {
        return dao.writeReview(classId, writerId, targetId, rating, content);
    }

    public boolean reviewExists(int classId, int writerId, int targetId) {
        return dao.reviewExists(classId, writerId, targetId);
    }
}
