// ✅ OtherGroupDetailScreen.java (로컬 기준 충돌 해결 버전)
package gotcha.ui.home;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.dao.PublicGroupDAO;
import gotcha.dto.PublicGroup;
import gotcha.service.JoinService;
import gotcha.service.ScrapService;
import gotcha.ui.review.HostReviewScreen;
import gotcha.ui.review.ParticipantReviewWriteScreen;
import gotcha.ui.review.ViewParticipantReviewScreen;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class OtherGroupDetailScreen extends JPanel {
    private PublicGroup group;
    private int userId;
    private ScrapService scrapService = new ScrapService();
    private boolean isScrapped = false;
    private JButton scrapBtn;

    public OtherGroupDetailScreen(int classId, int userId) {
        this(new PublicGroupDAO().getPublicGroupById(classId), userId);
    }

    public OtherGroupDetailScreen(PublicGroup group, int userId) {
        this.group = group;
        this.userId = userId;
        setLayout(new BorderLayout());

        if (group == null) {
            JOptionPane.showMessageDialog(this, "소모임 정보를 불러올 수 없습니다.");
            return;
        }

        isScrapped = scrapService.isScrapped(userId, group.getClassId());

        JLabel titleLabel = new JLabel(group.getTitle());
        titleLabel.setFont(FontLoader.loadCustomFont(24f).deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        cardPanel.setBackground(Color.WHITE);

        cardPanel.add(createInfoCard("카테고리", group.getCategory()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("소개", group.getContext()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("지역", group.getRegion()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("요일", group.getDays()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("인원 현황", group.getUserCount() + " / " + group.getMax()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("상태", group.getStatus()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("주최자", group.getHostNickname()));

        add(cardPanel, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createInfoCard(String label, String value) {
        Font font = FontLoader.loadCustomFont(14f);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(Color.WHITE);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(font.deriveFont(Font.BOLD));
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(font);

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueComponent, BorderLayout.EAST);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton hostReviewBtn = new JButton("주최자 리뷰 조회");
        scrapBtn = new JButton(isScrapped ? "스크랩 취소" : "스크랩하기");
        JButton joinBtn = new JButton("가입하기");
        JButton backBtn = new JButton("뒤로가기");

        hostReviewBtn.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new HostReviewScreen(parentFrame, group.getClassId()).setVisible(true);
        });

        scrapBtn.addActionListener(e -> {
            boolean success;
            if (isScrapped) {
                success = scrapService.cancelScrap(userId, group.getClassId());
                if (success) {
                    isScrapped = false;
                    scrapBtn.setText("스크랩하기");
                    JOptionPane.showMessageDialog(this, "스크랩이 취소되었습니다.");
                }
            } else {
                success = scrapService.scrapClass(userId, group.getClassId());
                if (success) {
                    isScrapped = true;
                    scrapBtn.setText("스크랩 취소");
                    JOptionPane.showMessageDialog(this, "스크랩 되었습니다!");
                }
            }
        });

        JoinService joinService = new JoinService();
        boolean alreadyJoined = joinService.isAlreadyJoined(userId, group.getClassId());

        if (alreadyJoined) {
            joinBtn.setText("이미 가입됨");
            joinBtn.setEnabled(false);

            JButton reviewBtn = new JButton("참여자 리뷰 쓰기");
            reviewBtn.addActionListener(e -> {
                Main.setScreen(new ViewParticipantReviewScreen(group.getClassId(), userId));
            });
            panel.add(reviewBtn);
        } else {
            joinBtn.addActionListener(e -> {
                int result = joinService.joinClass(userId, group.getClassId());
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "가입 신청 완료!");
                    joinBtn.setEnabled(false);
                    joinBtn.setText("가입 완료");
                } else {
                    JOptionPane.showMessageDialog(this, "가입 처리 중 문제가 발생했습니다.");
                }
            });
        }

        backBtn.addActionListener(e -> Main.setScreen(new HomeScreen()));

        panel.add(hostReviewBtn);
        panel.add(scrapBtn);
        panel.add(joinBtn);
        panel.add(backBtn);
        return panel;
    }
}

