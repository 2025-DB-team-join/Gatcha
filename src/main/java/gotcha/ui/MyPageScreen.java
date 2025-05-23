package gotcha.ui;

import javax.swing.*;
import java.awt.*;

public class MyPageScreen extends JPanel {
    public MyPageScreen(int userId) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("마이페이지"));

        // 상단: 내 정보 (예시)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("내 정보 (닉네임, 이메일 등 표시 가능)"));

        // 하단: 내가 참여중인 소모임 목록
        CurrentGroupScreen currentGroupPanel = new CurrentGroupScreen(userId);

        add(infoPanel, BorderLayout.NORTH);
        add(currentGroupPanel, BorderLayout.CENTER);
    }
}
