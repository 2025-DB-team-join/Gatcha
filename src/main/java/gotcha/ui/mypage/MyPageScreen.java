package gotcha.ui.mypage;

import gotcha.service.UserService;
import gotcha.service.UserRatingService;
import gotcha.ui.CurrentGroupScreen;
import gotcha.ui.home.HomeScreen;
import gotcha.ui.UserReviewScreen;
import gotcha.ui.mypage.PreviousClassesPanel;
import gotcha.ui.mypage.ScrapListPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MyPageScreen extends JPanel {
    private final UserService userService = new UserService();
    private final UserRatingService ratingService = new UserRatingService();

    public MyPageScreen(int userId) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("마이페이지"));

        // 1. 상단: 내 정보 + 평점
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        Map<String, Object> userInfo = userService.getUserInfo(userId);

        if (!userInfo.isEmpty()) {
            infoPanel.add(new JLabel("닉네임: " + userInfo.get("nickname")));
            infoPanel.add(new JLabel("이메일: " + userInfo.get("email")));
            infoPanel.add(new JLabel("출생연도: " + userInfo.get("birthyear")));
            infoPanel.add(new JLabel("성별: " + userInfo.get("gender")));
            infoPanel.add(new JLabel("지역: " + userInfo.get("region")));
            infoPanel.add(new JLabel("가입일: " + userInfo.get("registered_at")));
            infoPanel.add(Box.createVerticalStrut(10));

            // 별점 + "나에 대한 리뷰 보기" 버튼
            double avgRating = ratingService.getAverageRating(userId);
            JPanel starPanel = makeStarPanel(avgRating);

            JButton myReviewBtn = new JButton("나에 대한 리뷰 보기");
            starPanel.add(Box.createHorizontalStrut(10));
            starPanel.add(myReviewBtn);

            infoPanel.add(starPanel);
            infoPanel.add(Box.createVerticalStrut(10));

            // 리뷰 보기 버튼 이벤트
            myReviewBtn.addActionListener(e -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                // UserReviewScreen을 JDialog로 사용
                UserReviewScreen dialog = new UserReviewScreen(topFrame, userId);
                dialog.setVisible(true);
            });

        } else {
            infoPanel.add(new JLabel("사용자 정보를 불러올 수 없습니다."));
            infoPanel.add(Box.createVerticalStrut(20));
        }

        // 2. 하단: 내가 참여중인 소모임 목록
        CurrentGroupScreen currentGroupPanel = new CurrentGroupScreen(userId);

        // 3. 개인정보 수정, 비밀번호 변경, 회원탈퇴, 뒤로가기 버튼
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

        // 4. 배치
        add(infoPanel, BorderLayout.NORTH);
        add(currentGroupPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 5. 이벤트
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


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(new JLabel("참여 중인 소모임"));
        centerPanel.add(new CurrentGroupScreen(userId));
        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(new JLabel("스크랩한 소모임"));
        centerPanel.add(new ScrapListPanel(userId));
        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(new JLabel("내가 수강한 소모임"));
        centerPanel.add(new PreviousClassesPanel(userId));

        JScrollPane scrollPane = new JScrollPane(centerPanel);

        add(infoPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }
    // 별점 패널 생성
    private JPanel makeStarPanel(double avgRating) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("나의 평점: ");
        panel.add(label);

        int fullStars = (int) avgRating;
        boolean halfStar = (avgRating - fullStars) >= 0.5;
        int emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

        for (int i = 0; i < fullStars; i++) panel.add(new JLabel("★"));
        if (halfStar) panel.add(new JLabel("☆"));
        for (int i = 0; i < emptyStars; i++) panel.add(new JLabel("☆"));
        panel.add(new JLabel(String.format("(%.2f/5)", avgRating)));
        return panel;
    }

}
