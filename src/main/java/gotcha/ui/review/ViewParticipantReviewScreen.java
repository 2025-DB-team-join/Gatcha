// ✅ ViewParticipantReviewScreen.java (Updated)
package gotcha.ui.review;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.dao.ParticipantDAO;
import gotcha.dto.Participant;
import gotcha.ui.home.OtherGroupDetailScreen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewParticipantReviewScreen extends JPanel {
    private int classId;
    private int writerId;

    public ViewParticipantReviewScreen(int classId, int writerId) {
        this.classId = classId;
        this.writerId = writerId;

        setLayout(new BorderLayout());

        // 제목
        JLabel title = new JLabel("리뷰할 참여자 선택", SwingConstants.CENTER);
        title.setFont(FontLoader.loadCustomFont(16f).deriveFont(Font.BOLD));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // 참여자 리스트
        ParticipantDAO dao = new ParticipantDAO();
        List<Participant> participants = dao.getParticipants(classId, writerId);

        DefaultListModel<Participant> model = new DefaultListModel<>();
        for (Participant p : participants) model.addElement(p);

        JList<Participant> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(FontLoader.loadCustomFont(14f));
        list.setCellRenderer((list1, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getNickname());
            label.setFont(FontLoader.loadCustomFont(14f));
            label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            if (isSelected) {
                label.setBackground(new Color(240, 240, 255));
                label.setOpaque(true);
            } else {
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
            }
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton reviewBtn = new JButton("리뷰 쓰러 가기");
        reviewBtn.addActionListener(e -> {
            Participant selected = list.getSelectedValue();
            if (selected != null) {
                Main.setScreen(new ParticipantReviewWriteScreen(classId, writerId, selected.getUserId()));
            } else {
                JOptionPane.showMessageDialog(this, "참여자를 선택해주세요.");
            }
        });

        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> Main.setScreen(new OtherGroupDetailScreen(classId, writerId)));

        buttonPanel.add(reviewBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
