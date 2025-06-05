package gotcha.service;

import gotcha.dao.UserReviewDAO;
import java.util.List;

public class UserReviewService {
    private UserReviewDAO dao = new UserReviewDAO();

    public List<UserReviewDAO.ReviewInfo> getReviewsAboutMe(int userId) {
        return dao.getReviewsAboutMe(userId);
    }
}
