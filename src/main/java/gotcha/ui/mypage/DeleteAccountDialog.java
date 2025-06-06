package gotcha.ui.mypage;

import gotcha.service.UserService;
import gotcha.Main;
import gotcha.ui.home.HomeScreen;

import javax.swing.*;
import java.awt.*;

public class DeleteAccountDialog extends JDialog {
    private final UserService userService = new UserService();

    public DeleteAccountDialog(int userId) {
        setTitle("회원탈퇴");
        setSize(300, 180);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // 메시지
        JLabel messageLabel = new JLabel("비밀번호를 입력 후 탈퇴를 진행합니다.", SwingConstants.CENTER);

        // 비밀번호 입력 필드
        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPasswordField pwField = new JPasswordField(15);
        pwPanel.add(new JLabel("비밀번호:"));
        pwPanel.add(pwField);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton confirmBtn = new JButton("탈퇴");
        JButton cancelBtn = new JButton("취소");
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);

        add(messageLabel, BorderLayout.NORTH);
        add(pwPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 버튼 이벤트
        confirmBtn.addActionListener(e -> {
            String inputPw = new String(pwField.getPassword());
            if (inputPw != null && !inputPw.trim().isEmpty()) {
                boolean isValid = userService.validateUserPassword(userId, inputPw);
                if (isValid) {
                    int choice = JOptionPane.showConfirmDialog(this, "정말 회원탈퇴하시겠습니까?", "회원탈퇴 확인", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        userService.deleteUser(userId);
                        JOptionPane.showMessageDialog(this, "회원탈퇴가 완료되었습니다. 메인화면으로 이동합니다.");
                        dispose();
                        Main.setScreen(new HomeScreen());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        setLocationRelativeTo(null); // 화면 중앙
        pack();
    }
}
