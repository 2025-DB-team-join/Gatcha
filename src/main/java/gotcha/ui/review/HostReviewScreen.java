package gotcha.ui.review;

import gotcha.dao.ReviewDAO;
import gotcha.dao.ReviewDAO.HostReview;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HostReviewScreen extends JDialog {
	 public HostReviewScreen(JFrame parent, int classId) {
	        super(parent, "주최자 리뷰 조회", true);
	        setSize(600, 400);
	        setLocationRelativeTo(parent);

	        ReviewDAO dao = new ReviewDAO();
	        List<ReviewDAO.HostReview> reviews = dao.getHostReviewsByClassId(classId);

	        String[] cols = {"닉네임", "평점", "리뷰 내용"};
	        DefaultTableModel model = new DefaultTableModel(cols, 0) {
	            @Override
	            public boolean isCellEditable(int r, int c) { return false; }
	        };

	        for (ReviewDAO.HostReview r : reviews) {
	            model.addRow(new Object[]{r.getReviewerName(), String.format("%.1f", r.getRating()), r.getContent()});
	        }

	        JTable table = new JTable(model);
	        JScrollPane scroll = new JScrollPane(table);
	        add(scroll, BorderLayout.CENTER);

	        JButton closeBtn = new JButton("닫기");
	        closeBtn.addActionListener(e -> dispose());
	        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        btnPanel.add(closeBtn);
	        add(btnPanel, BorderLayout.SOUTH);
	    }
}

