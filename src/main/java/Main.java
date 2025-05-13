package gatcha;

import gatcha.common.FontLoader;
import gatcha.ui.AuthScreen;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 인트로 화면 구성
            JFrame introFrame = new JFrame("Gatcha!");
            introFrame.setSize(800, 600);
            introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            introFrame.setLayout(new BorderLayout());

            // 왼쪽: 이미지
            ImageIcon icon = new ImageIcon(Main.class.getResource("/images/gatchi.png"));
            Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // ← 여기서 크기 조절
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(resizedIcon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // 오른쪽: 텍스트
            JLabel textLabel = new JLabel("1인 가구를 위한 소모임 플랫폼 Gatcha!", SwingConstants.LEFT);
            textLabel.setFont(FontLoader.loadCustomFont(24f));

            // 중앙 패널
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(imageLabel, BorderLayout.WEST);
            contentPanel.add(textLabel, BorderLayout.CENTER);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

            introFrame.add(contentPanel, BorderLayout.CENTER);
            introFrame.setLocationRelativeTo(null);
            introFrame.setVisible(true);

            // 3초 후 로그인 화면으로 전환
            Timer timer = new Timer(3000, e -> {
                introFrame.dispose(); // 인트로 종료

                frame = new JFrame("Gatcha!");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);

                frame.setContentPane(new AuthScreen()); // 로그인 화면 표시
                frame.setVisible(true);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    // 화면 전환 메서드
    public static void setScreen(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }
}
