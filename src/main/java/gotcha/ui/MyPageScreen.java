package gotcha.ui;

import gotcha.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MyPageScreen extends JPanel {
    private final UserService userService = new UserService();

    public MyPageScreen(int userId) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("마이페이지"));

        Map<String, Object> userInfo = userService.getUserInfo(userId);

        // 상단: 내 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        if (!userInfo.isEmpty()) {
            infoPanel.add(new JLabel("닉네임: " + userInfo.get("nickname")));
            infoPanel.add(new JLabel("이메일: " + userInfo.get("email")));
            infoPanel.add(new JLabel("출생연도: " + userInfo.get("birthyear")));
            infoPanel.add(new JLabel("성별: " + userInfo.get("gender")));
            infoPanel.add(new JLabel("지역: " + userInfo.get("region")));
            infoPanel.add(new JLabel("가입일: " + userInfo.get("registered_at")));
            infoPanel.add(Box.createVerticalStrut(20));
        } else {
            infoPanel.add(new JLabel("사용자 정보를 불러올 수 없습니다."));
            infoPanel.add(Box.createVerticalStrut(20));
        }

        // 하단: 내가 참여중인 소모임 목록
        CurrentGroupScreen currentGroupPanel = new CurrentGroupScreen(userId);

        add(infoPanel, BorderLayout.NORTH);
        add(currentGroupPanel, BorderLayout.CENTER);
    }
}
