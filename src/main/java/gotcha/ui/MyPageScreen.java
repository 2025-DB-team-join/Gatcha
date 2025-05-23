package gotcha.ui;

import gotcha.common.Session;

import javax.swing.*;
import java.awt.*;

public class MyPageScreen extends JPanel {
    public MyPageScreen(int userId) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("마이페이지"));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("내 정보 (닉네임, 이메일 등 표시 가능)"));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(new CurrentGroupScreen(userId));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(new HostedClassScreen(userId));

        add(infoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
