package gotcha;

import gotcha.ui.GroupFormScreen;

import javax.swing.*;

public class GroupFormScreenTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("테스트: 소모임 생성 화면");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GroupFormScreen());
        frame.setVisible(true);
    }
}
