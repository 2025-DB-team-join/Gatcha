package gotcha.ui.manage;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.dao.HostedClassDAO;
import gotcha.dao.HostedClassDAO.HostedClass;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MyGroupDetailScreen extends JPanel {
    private HostedClass hostedClass;
    private int userId;

    public MyGroupDetailScreen(int classId, int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());

        // 데이터 조회
        HostedClassDAO dao = new HostedClassDAO();
        hostedClass = dao.getHostedClassById(classId);

        if (hostedClass == null) {
            JOptionPane.showMessageDialog(this, "소모임 정보를 불러올 수 없습니다.");
            return;
        }

        // 제목
        JLabel titleLabel = new JLabel(hostedClass.getTitle());
        titleLabel.setFont(FontLoader.loadCustomFont(24f).deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 카드 패널
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        cardPanel.setBackground(Color.WHITE);

        // 카드 만들기
        cardPanel.add(createInfoCard("카테고리", hostedClass.getCategory()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("소개", hostedClass.getContext()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("지역", hostedClass.getRegion()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("요일", hostedClass.getDays()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("인원 현황", hostedClass.getUserCount() + " / " + hostedClass.getMax()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("상태", hostedClass.getStatus()));

        add(cardPanel, BorderLayout.CENTER);

        // 버튼 패널
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
        JButton editBtn = new JButton("수정");
        JButton deleteBtn = new JButton("삭제");
        JButton backBtn = new JButton("뒤로가기");

        backBtn.addActionListener(e -> Main.setScreen(new ManageGroupScreen(userId)));

        panel.add(editBtn);
        panel.add(deleteBtn);
        panel.add(backBtn);
        return panel;
    }
}
