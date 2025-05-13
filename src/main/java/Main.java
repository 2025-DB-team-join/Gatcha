import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // 초기 소개 화면 프레임
        JFrame introFrame = new JFrame("Gatcha!");
        introFrame.setSize(800, 600);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setLayout(new BorderLayout());

        JLabel introLabel = new JLabel("1인 가구를 위한 소모임 플랫폼 Gatcha!", SwingConstants.CENTER);
        introLabel.setFont(FontLoader.loadCustomFont(28f));
        introFrame.add(introLabel, BorderLayout.CENTER);

        introFrame.setLocationRelativeTo(null); // 화면 중앙 정렬
        introFrame.setVisible(true);

        // 3초 후 메인 화면으로 전환
        Timer timer = new Timer(3000, e -> {
            introFrame.dispose(); // 인트로 화면 닫기
            showMainUI();         // 메인 화면 표시
        });
        timer.setRepeats(false); // 1회만 실행
        timer.start();
    }

    // 메인 UI (버튼 있는 창)
    public static void showMainUI() {
        JFrame frame = new JFrame("Gatcha!");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("같이 해요!");
        button.setFont(FontLoader.loadCustomFont(20f));
        frame.add(button);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

