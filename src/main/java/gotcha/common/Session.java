package gotcha.common;

public class Session {
    public static int loggedInUserId = -1;

    public static void login(int userId) {
        loggedInUserId = userId;
    }

    public static void logout() {
        loggedInUserId = -1;
    }

    public static boolean isLoggedIn() {
        return loggedInUserId != -1;
    }
}
