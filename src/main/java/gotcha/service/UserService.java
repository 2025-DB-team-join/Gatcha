package gotcha.service;

import gotcha.dao.UserDAO;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public boolean login(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean register(String username, String nickname, String password, String email, String gender, String region, Integer birthyear) {
        if (userDAO.emailExists(email)) return false;
        return userDAO.register(username, nickname, password, email, gender, region, birthyear);
    }
}
