package gotcha.ui.home;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.dao.PublicGroupDAO;
import gotcha.dto.PublicGroup;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class OtherGroupDetailScreen extends JPanel {
    private PublicGroup group;
    private int userId;

    public OtherGroupDetailScreen(int classId, int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());

        // 데이터 조회
        PublicGroupDAO dao = new PublicGroupDAO();
        group = dao.getPublicGroupById(classId);

        if (group == null) {
            JOptionPane.showMessageDialog(this, "소모임 정보를 불러올 수 없습니다.");
            return;
        }

        // 제목
        JLabel titleLabel = new JLabel(group.getTitle());
        titleLabel.setFont(FontLoader.loadCustomFont(24f).deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 카드 패널
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        cardPanel.setBackground(Color.WHITE);

        // 카드 내용
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

        // 버튼
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
        JButton scrapBtn = new JButton("스크랩하기");
        JButton joinBtn = new JButton("가입하기");
        JButton backBtn = new JButton("뒤로가기");

        scrapBtn.addActionListener(e -> {
            // TODO: 스크랩 로직 추가
            JOptionPane.showMessageDialog(this, "스크랩 되었습니다!");
        });

        joinBtn.addActionListener(e -> {
            // TODO: 가입 로직 추가
            JOptionPane.showMessageDialog(this, "가입 신청 완료!");
        });

        backBtn.addActionListener(e -> {
            Main.setScreen(new HomeScreen());
        });

        panel.add(scrapBtn);
        panel.add(joinBtn);
        panel.add(backBtn);
        return panel;
    }
}
