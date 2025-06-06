package gotcha.service;

import gotcha.dao.UserDAO;

import java.util.Map;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public boolean login(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean register(String username, String nickname, String password, String email, String gender, String region, Integer birthyear) {
        if (userDAO.emailExists(email)) return false;
        return userDAO.register(username, nickname, password, email, gender, region, birthyear);
    }

    public int getUserIdByEmail(String email) {
        return userDAO.findUserIdByEmail(email);
    }

    public Map<String, Object> getUserInfo(int userId) {
        return userDAO.getUserInfo(userId);
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

    public boolean validateUserPassword(int userId, String inputPassword) {
        return userDAO.checkPassword(userId, inputPassword);
    }

    public void updatePassword(int userId, String newPassword) {
        userDAO.updatePassword(userId, newPassword);
    }

    public boolean updateUserInfo(int userId, String newNickname, String newEmail, String newRegion) {
        return userDAO.updateUserInfo(userId, newNickname, newEmail, newRegion);
    }

	public String getNicknameById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
