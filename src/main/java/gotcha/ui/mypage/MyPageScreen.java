package gotcha.ui.mypage;

import gotcha.service.UserService;
import gotcha.ui.CurrentGroupScreen;
import gotcha.ui.home.HomeScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MyPageScreen extends JPanel {
    private final UserService userService = new UserService();

    public MyPageScreen(int userId) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("마이페이지"));

        Map<String, Object> userInfo = userService.getUserInfo(userId);

        // 상단: 내 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        if (!userInfo.isEmpty()) {
            infoPanel.add(new JLabel("닉네임: " + userInfo.get("nickname")));
            infoPanel.add(new JLabel("이메일: " + userInfo.get("email")));
            infoPanel.add(new JLabel("출생연도: " + userInfo.get("birthyear")));
            infoPanel.add(new JLabel("성별: " + userInfo.get("gender")));
            infoPanel.add(new JLabel("지역: " + userInfo.get("region")));
            infoPanel.add(new JLabel("가입일: " + userInfo.get("registered_at")));
            infoPanel.add(Box.createVerticalStrut(20));
        } else {
            infoPanel.add(new JLabel("사용자 정보를 불러올 수 없습니다."));
            infoPanel.add(Box.createVerticalStrut(20));
        }

        // 개인정보 수정, 비밀번호 변경, 탈퇴 추가
        JPanel buttonPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton editInfoBtn = new JButton("개인정보 수정");
        JButton changePwBtn = new JButton("비밀번호 변경");
        JButton deleteAccountBtn = new JButton("회원탈퇴");
        leftPanel.add(editInfoBtn);
        leftPanel.add(changePwBtn);
        leftPanel.add(deleteAccountBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton backBtn = new JButton("뒤로가기");
        rightPanel.add(backBtn);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        editInfoBtn.addActionListener(e -> {
            EditUserInfoDialog dialog = new EditUserInfoDialog(userId);
            dialog.setVisible(true);
        });

        changePwBtn.addActionListener(e -> {
            ChangePasswordDialog dialog = new ChangePasswordDialog(userId);
            dialog.setVisible(true);
        });

        deleteAccountBtn.addActionListener(e -> {
            DeleteAccountDialog dialog = new DeleteAccountDialog(userId);
            dialog.setVisible(true);
        });

        backBtn.addActionListener(e -> gotcha.Main.setScreen(new HomeScreen()));


        // 하단: 내가 참여중인 소모임 목록
        CurrentGroupScreen currentGroupPanel = new CurrentGroupScreen(userId);

        add(infoPanel, BorderLayout.NORTH);
        add(currentGroupPanel, BorderLayout.CENTER);

    }
}
