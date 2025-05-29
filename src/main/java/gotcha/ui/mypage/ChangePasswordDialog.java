package gotcha.ui.mypage;

import gotcha.service.UserService;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordDialog extends JDialog {
    private final UserService userService = new UserService();

    public ChangePasswordDialog(int userId) {
        setTitle("비밀번호 변경");
        setSize(300, 200);
        setModal(true);
        setLayout(new GridLayout(4, 2, 5, 5));

        JLabel currentPwLabel = new JLabel("현재 비밀번호:");
        JPasswordField currentPwField = new JPasswordField();
        JLabel newPwLabel = new JLabel("새 비밀번호:");
        JPasswordField newPwField = new JPasswordField();
        JLabel newPwLabelCheck = new JLabel("새 비밀번호 확인:");
        JPasswordField newPwFieldCheck = new JPasswordField();

        JButton saveBtn = new JButton("저장");
        JButton cancelBtn = new JButton("취소");

        add(currentPwLabel); add(currentPwField);
        add(newPwLabel); add(newPwField);
        add(newPwLabelCheck); add(newPwFieldCheck);
        add(saveBtn); add(cancelBtn);

        saveBtn.addActionListener(e -> {
            String currentPw = new String(currentPwField.getPassword());
            String newPw = new String(newPwField.getPassword());
            String newPwCheck = String.valueOf(newPwFieldCheck.getPassword());

            if (userService.validateUserPassword(userId, currentPw)) {
                if (newPw.equals(newPwCheck)) {
                    userService.updatePassword(userId, newPw);
                    JOptionPane.showMessageDialog(this, "비밀번호가 변경되었습니다.");
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "새로운 비밀번호끼리 일치하지 않습니다..", "오류", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "현재 비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
    }
}
