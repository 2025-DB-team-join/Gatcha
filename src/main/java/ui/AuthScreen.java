package gatcha.ui;

import gatcha.Main;
import gatcha.ui.HomeScreen;
import gatcha.common.FontLoader;

import javax.swing.*;
import java.awt.*;

public class AuthScreen extends JPanel {
    public AuthScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(80, 200, 80, 200)); // 여백

        // 제목
        JLabel title = new JLabel("로그인", SwingConstants.CENTER);
        title.setFont(FontLoader.loadCustomFont(24f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(30));

        // 아이디 패널
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("아이디:");
        JTextField username = new JTextField(15);
        username.setPreferredSize(new Dimension(200, 25));
        idPanel.add(idLabel);
        idPanel.add(username);
        add(idPanel);

        // 비밀번호 패널
        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel pwLabel = new JLabel("비밀번호:");
        JPasswordField password = new JPasswordField(15);
        password.setPreferredSize(new Dimension(200, 25));
        pwPanel.add(pwLabel);
        pwPanel.add(password);
        add(pwPanel);

        // 로그인 버튼
        JButton loginBtn = new JButton("로그인");
        loginBtn.setFont(FontLoader.loadCustomFont(16f));
        loginBtn.setPreferredSize(new Dimension(200, 35));
        loginBtn.setMaximumSize(new Dimension(200, 35));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            Main.setScreen(new HomeScreen());
        });

        add(Box.createVerticalStrut(20));
        add(loginBtn);
    }
}
