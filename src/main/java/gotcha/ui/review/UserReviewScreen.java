package gotcha.ui.review;

import gotcha.dao.UserReviewDAO;
import gotcha.service.UserReviewService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserReviewScreen extends JDialog {
    public UserReviewScreen(JFrame parent, int userId) {
        super(parent, "나에 대한 리뷰 보기", true);
        setLocationRelativeTo(parent);

        UserReviewService service = new UserReviewService();
        List<UserReviewDAO.ReviewInfo> reviews = service.getReviewsAboutMe(userId);

        String[] cols = {"소모임 이름", "리뷰 내용"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (UserReviewDAO.ReviewInfo r : reviews) {
            model.addRow(new Object[]{r.getClassTitle(), r.getComment()});
        }
        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(450);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);

        JButton closeBtn = new JButton("닫기");
        closeBtn.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(closeBtn);

        add(btnPanel, BorderLayout.SOUTH);
        pack();
        setSize(900, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
    }
}
