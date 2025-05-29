package gotcha;

import gotcha.ui.ManageGroupScreen;

import javax.swing.*;

public class ManageGroupScreenTest {
    public static void main(String[] args) {

        int testUserId = 4;  // test user ID

        JFrame frame = new JFrame("소모임 관리 테스트");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        gotcha.Main.frame = frame;
        ManageGroupScreen manageScreen = new ManageGroupScreen(testUserId);

        frame.add(manageScreen);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
