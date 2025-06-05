package gotcha.dto;

public class Participant {
    private int userId;
    private String nickname;

    public Participant(int userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    public int getUserId() { return userId; }
    public String getNickname() { return nickname; }

    @Override
    public String toString() {
        return nickname;
    }
}
