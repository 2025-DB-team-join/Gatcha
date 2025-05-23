package gotcha.ui;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.common.Session;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    public HomeScreen() {
        setLayout(new BorderLayout());
        FontLoader.applyGlobalFont(14f);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        JButton groupBtn = new JButton("소모임 탐색");
        JButton createBtn = new JButton("소모임 생성");
        JButton myPageBtn = new JButton("마이페이지");

        Font btnFont = FontLoader.loadCustomFont(16f);
        JButton[] buttons = {groupBtn, createBtn, myPageBtn};
        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(140, 40));
            buttonPanel.add(btn);
        }

        JLabel imageLabel;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/gatchi.png"));
            Image scaled = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            imageLabel = new JLabel("이미지를 불러올 수 없습니다.");
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        add(buttonPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        createBtn.addActionListener(e -> Main.setScreen(new GroupFormScreen()));
        myPageBtn.addActionListener(e -> {
            int userId = Session.loggedInUserId;
            if (userId != -1) {
                Main.setScreen(new MyPageScreen(userId));
            } else {
                JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 다시 로그인해주세요.");
            }
        });
    }
}
