package gotcha;

import gotcha.common.DBConnector;
import gotcha.ui.AuthScreen;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {
    public static JFrame frame;

    public static void main(String[] args) {

        Connection conn = DBConnector.getConnection();
        if (conn != null) {
            System.out.println("🎉 DB 연결 성공");
        } else {
            System.out.println("❌ DB 연결 실패");
        }

        SwingUtilities.invokeLater(() -> {
            // 인트로 화면 구성
            JFrame introFrame = new JFrame("Gatcha!");
            introFrame.setSize(800, 600);
            introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            introFrame.setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon(Main.class.getResource("/images/onboarding.png"));
            Image scaledImage = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH); // 프레임 크기에 맞게 조절
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);

            introFrame.add(imageLabel, BorderLayout.CENTER);

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
