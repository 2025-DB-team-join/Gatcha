package gotcha.service;

import gotcha.dao.UserRatingDAO;

public class UserRatingService {
    private UserRatingDAO dao = new UserRatingDAO();

    public double getAverageRating(int userId) {
        return dao.getAverageRating(userId);
    }
}
