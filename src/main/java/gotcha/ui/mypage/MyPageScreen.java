package gotcha.ui.mypage;

import gotcha.service.UserService;
import gotcha.service.UserRatingService;
import gotcha.ui.home.HomeScreen;
import gotcha.ui.mypage.PreviousClassesPanel;
import gotcha.ui.mypage.ScrapListPanel;
import gotcha.ui.review.UserReviewScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MyPageScreen extends JPanel {
    private final UserService userService = new UserService();
    private final UserRatingService ratingService = new UserRatingService();

    public MyPageScreen(int userId) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("마이페이지"));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Map<String, Object> userInfo = userService.getUserInfo(userId);
        if (!userInfo.isEmpty()) {
            JLabel nickname = new JLabel("닉네임: " + userInfo.get("nickname"));
            JLabel email = new JLabel("이메일: " + userInfo.get("email"));
            JLabel birthyear = new JLabel("출생연도: " + userInfo.get("birthyear"));
            JLabel gender = new JLabel("성별: " + userInfo.get("gender"));
            JLabel region = new JLabel("지역: " + userInfo.get("region"));
            JLabel registeredAt = new JLabel("가입일: " + userInfo.get("registered_at"));

            for (JLabel label : new JLabel[]{nickname, email, birthyear, gender, region, registeredAt}) {
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                infoPanel.add(label);
            }

            infoPanel.add(Box.createVerticalStrut(10));

            double avgRating = ratingService.getAverageRating(userId);
            JPanel starPanel = makeStarPanel(avgRating);
            starPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            starPanel.setBackground(Color.WHITE);
            infoPanel.add(starPanel);

            JButton myReviewBtn = new JButton("나에 대한 리뷰 보기");
            myReviewBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(myReviewBtn);

            myReviewBtn.addActionListener(e -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                UserReviewScreen dialog = new UserReviewScreen(topFrame, userId);
                dialog.setVisible(true);
            });

            infoPanel.add(Box.createVerticalStrut(10));
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // 참여 중인 소모임
        JLabel title1 = new JLabel("참여 중인 소모임(주최 제외)");
        title1.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(title1);

        JLabel hintLabel1 = new JLabel("※ 소모임 이름을 더블클릭하면 참여를 취소할 수 있습니다.");
        hintLabel1.setForeground(Color.GRAY);
        hintLabel1.setFont(hintLabel1.getFont().deriveFont(Font.ITALIC, 12f));
        centerPanel.add(hintLabel1);

        JScrollPane scroll1 = new JScrollPane(new CurrentGroupScreen(userId));
        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll1.getViewport().setPreferredSize(new Dimension(800, 150));
        centerPanel.add(scroll1);
        centerPanel.add(Box.createVerticalStrut(5));

        // 스크랩한 소모임
        JLabel title2 = new JLabel("스크랩한 소모임");
        title2.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(title2);

        JLabel hintLabel2 = new JLabel("※ 더블클릭 시 스크랩을 취소할 수 있습니다.");
        hintLabel2.setForeground(Color.GRAY);
        hintLabel2.setFont(hintLabel2.getFont().deriveFont(Font.ITALIC, 12f));
        centerPanel.add(hintLabel2);

        JScrollPane scroll2 = new JScrollPane(new ScrapListPanel(userId));
        scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll2.getViewport().setPreferredSize(new Dimension(800, 150));
        centerPanel.add(scroll2);
        centerPanel.add(Box.createVerticalStrut(5));

        // 내가 수강한 소모임
        JLabel title3 = new JLabel("이전 수강한 소모임");
        title3.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(title3);

        JScrollPane scroll3 = new JScrollPane(new PreviousClassesPanel(userId));
        scroll3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll3.getViewport().setPreferredSize(new Dimension(800, 150));
        centerPanel.add(scroll3);

        // 하단 버튼 panel
        JPanel buttonPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton editInfoBtn = new JButton("개인정보 수정");
        JButton changePwBtn = new JButton("비밀번호 변경");
        JButton deleteAccountBtn = new JButton("회원탈퇴");
        leftPanel.add(editInfoBtn);
        leftPanel.add(changePwBtn);
        leftPanel.add(deleteAccountBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton backBtn = new JButton("← 뒤로가기");
        rightPanel.add(backBtn);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        add(infoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        editInfoBtn.addActionListener(e -> new EditUserInfoDialog(userId).setVisible(true));
        changePwBtn.addActionListener(e -> new ChangePasswordDialog(userId).setVisible(true));
        deleteAccountBtn.addActionListener(e -> new DeleteAccountDialog(userId).setVisible(true));
        backBtn.addActionListener(e -> gotcha.Main.setScreen(new HomeScreen()));
    }

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
