package gotcha.ui.mypage;

import gotcha.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class EditUserInfoDialog extends JDialog {
    private final UserService userService = new UserService();

    public EditUserInfoDialog(int userId) {
        setTitle("개인정보 수정");
        setSize(300, 250);
        setModal(true);
        setLayout(new GridLayout(4, 2, 5, 5));

        // 기존 정보 불러오기
        Map<String, Object> userInfo = userService.getUserInfo(userId);

        JLabel nicknameLabel = new JLabel("닉네임:");
        JTextField nicknameField = new JTextField((String) userInfo.get("nickname"));
        JLabel emailLabel = new JLabel("이메일:");
        JTextField emailField = new JTextField((String) userInfo.get("email"));
        JLabel regionLabel = new JLabel("지역:");
        String[] regions = {
                "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
                "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
                "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
        };
        JComboBox<String> regionBox = new JComboBox<>(regions);
        regionBox.setSelectedItem(userInfo.get("region"));

        JButton saveBtn = new JButton("저장");
        JButton cancelBtn = new JButton("취소");

        add(nicknameLabel); add(nicknameField);
        add(emailLabel); add(emailField);
        add(regionLabel); add(regionBox);
        add(saveBtn); add(cancelBtn);

        saveBtn.addActionListener(e -> {
            String newNickname = nicknameField.getText();
            String newEmail = emailField.getText();
            String newRegion = (String) regionBox.getSelectedItem();

            userService.updateUserInfo(userId, newNickname, newEmail, newRegion);
            JOptionPane.showMessageDialog(this, "정보가 수정되었습니다.");
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
    }
}
