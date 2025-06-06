package gotcha.ui.review;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.dao.ParticipantReviewDAO;
import gotcha.dto.ParticipantReview;
import gotcha.ui.home.OtherGroupDetailScreen;

import javax.swing.*;
import java.awt.*;

public class ParticipantReviewWriteScreen extends JPanel {
    public ParticipantReviewWriteScreen(int classId, int writerId, int targetId) {
        setLayout(new BorderLayout());

        // 상단 제목
        JLabel title = new JLabel("리뷰 작성 / 수정 / 삭제", SwingConstants.CENTER);
        title.setFont(FontLoader.loadCustomFont(18f).deriveFont(Font.BOLD));
        add(title, BorderLayout.NORTH);

        // DAO 호출
        ParticipantReviewDAO dao = new ParticipantReviewDAO();
        ParticipantReview review = dao.getReview(classId, writerId, targetId);

        // 입력 폼 (가운데)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Font font = FontLoader.loadCustomFont(14f);

        // 평점
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("평점 (0.0 ~ 5.0):"), gbc);

        JTextField ratingField = new JTextField();
        ratingField.setFont(font);
        if (review != null) {
            ratingField.setText(String.valueOf(review.getRating()));
        }
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(ratingField, gbc);

        // 리뷰 내용
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("리뷰 내용:"), gbc);

        JTextArea contentArea = new JTextArea(5, 30);
        contentArea.setFont(font);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        if (review != null) {
            contentArea.setText(review.getContent());
        }
        JScrollPane scroll = new JScrollPane(contentArea);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        formPanel.add(scroll, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 버튼 패널 (오른쪽 하단)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton saveBtn = new JButton(review == null ? "작성" : "수정");
        JButton backBtn = new JButton("뒤로가기");
        buttonPanel.add(saveBtn);
        
        if (review != null) {
            JButton deleteBtn = new JButton("삭제");
            deleteBtn.addActionListener(e -> {
                dao.deleteReview(review.getReviewId());
                JOptionPane.showMessageDialog(this, "리뷰가 삭제되었습니다!");
                Main.setScreen(new OtherGroupDetailScreen(classId, writerId));
            });
            buttonPanel.add(deleteBtn);
        }

        saveBtn.addActionListener(e -> {
            try {
                float rating = Float.parseFloat(ratingField.getText());
                String content = contentArea.getText().trim();

                if (content.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "리뷰 내용을 입력해주세요.");
                    return;
                }

                if (rating < 0.0 || rating > 5.0) {
                    JOptionPane.showMessageDialog(this, "평점은 0.0 ~ 5.0 사이여야 합니다.");
                    return;
                }
                
                if (((int)(rating * 10)) % 5 != 0) {
                    JOptionPane.showMessageDialog(this, "평점은 0.5 단위로 입력해주세요.");
                    return;
                }

                if (review == null) {
                    dao.writeReview(classId, writerId, targetId, rating, content);
                    JOptionPane.showMessageDialog(this, "리뷰가 등록되었습니다!");
                } else {
                    dao.updateReview(review.getReviewId(), rating, content);
                    JOptionPane.showMessageDialog(this, "리뷰가 수정되었습니다!");
                }
                Main.setScreen(new OtherGroupDetailScreen(classId, writerId));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "평점은 숫자 형식으로 입력해주세요.");
            }
        });

        buttonPanel.add(backBtn);
        backBtn.addActionListener(e -> Main.setScreen(new OtherGroupDetailScreen(classId, writerId)));

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
