package gotcha.ui;

import gotcha.Main;
import gotcha.common.FontLoader;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    public HomeScreen() {
        setLayout(new BorderLayout());
        FontLoader.applyGlobalFont(14f);

        // 1. 상단 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));

        JButton groupBtn = new JButton("소모임 탐색");
        JButton createBtn = new JButton("소모임 생성");
        JButton myPageBtn = new JButton("마이페이지");
        JButton categoryBtn = new JButton("카테고리별 조회");

        Font btnFont = FontLoader.loadCustomFont(16f);
        JButton[] buttons = {groupBtn, createBtn, myPageBtn, categoryBtn};
        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(140, 40));
            buttonPanel.add(btn);
        }

        // 2. 하단 이미지 패널
        JLabel imageLabel;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/gatchi.png"));
            Image scaled = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH); // 사이즈 조절
            imageLabel = new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            imageLabel = new JLabel("이미지를 불러올 수 없습니다.");
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // 3. 화면 구성
        add(buttonPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        // 4. 버튼 이벤트 연결 (예시: 소모임 생성 → GroupFormScreen 전환)
        createBtn.addActionListener(e -> Main.setScreen(new GroupFormScreen()));
        categoryBtn.addActionListener(e -> Main.setScreen(new CategoryScreen()));
    }
}
