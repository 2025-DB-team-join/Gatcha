package gotcha;

import gotcha.common.FontLoader;
import gotcha.ui.HomeScreen;

import javax.swing.*;

public class HomeScreenTest {
    public static void main(String[] args) {
        // 전역 폰트 설정 (선택사항)
        FontLoader.applyGlobalFont(14f);

        SwingUtilities.invokeLater(() -> {
            // Main 클래스의 frame 직접 초기화
            Main.frame = new JFrame("테스트: 홈 화면");
            Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Main.frame.setSize(800, 600);
            Main.frame.setLocationRelativeTo(null); // 화면 가운데 정렬

            // HomeScreen을 화면에 띄움
            Main.setScreen(new HomeScreen());

            // 화면 보여주기
            Main.frame.setVisible(true);
        });
    }
}

