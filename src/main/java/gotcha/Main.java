package gotcha;

import gotcha.common.DBConnector;
import gotcha.common.FontLoader;
import gotcha.ui.home.AuthScreen;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {
    public static JFrame frame;

    public static void main(String[] args) {
        FontLoader.applyGlobalFont(14f);
        Connection conn = DBConnector.getConnection();
        if (conn != null) {
            System.out.println("🎉 DB 연결 성공");
        } else {
            System.out.println("❌ DB 연결 실패");
        }

        SwingUtilities.invokeLater(() -> {
            // 인트로 화면 구성
            JFrame introFrame = new JFrame("Gatcha!");
            introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            introFrame.setLayout(new BorderLayout());

            // 이미지 로딩 및 크기 자동 조정
            ImageIcon icon = new ImageIcon(Main.class.getResource("/images/onboarding.png"));
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);

            // preferredSize로 인트로 이미지 크기 보장 (필요시)
            imageLabel.setPreferredSize(new Dimension(800, 600));
            introFrame.add(imageLabel, BorderLayout.CENTER);

            introFrame.pack(); // 내용에 맞게 자동 크기 조정
            introFrame.setLocationRelativeTo(null);
            introFrame.setVisible(true);

            // 3초 후 로그인 화면으로 전환
            Timer timer = new Timer(3000, e -> {
                introFrame.dispose(); // 인트로 종료

                frame = new JFrame("Gatcha!");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ImageIcon mainIcon = new ImageIcon(Main.class.getResource("/images/icon.png"));
                frame.setIconImage(mainIcon.getImage());

                // 로그인 화면 패널 생성
                AuthScreen authScreen = new AuthScreen();

                frame.setContentPane(authScreen);

                // pack()으로 내용에 맞는 크기 자동 조정
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    // 화면 전환 메서드
    public static void setScreen(JPanel panel) {
        frame.setContentPane(panel);
        frame.pack(); // 새 패널 내용에 맞게 크기 자동 조정
        frame.setLocationRelativeTo(null); // 항상 중앙에 위치
        frame.revalidate();
        frame.repaint();
    }
}
