package gotcha.ui.review;

import gotcha.dao.ReviewDAO;
import gotcha.dao.ReviewDAO.HostReview;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.util.List;

public class HostReviewScreen extends JDialog {
	 public HostReviewScreen(JFrame parent, int classId) {
	        super(parent, "주최자 리뷰 조회", true);
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
	        table.getColumnModel().getColumn(0).setPreferredWidth(150); // "닉네임" 컬럼
	        table.getColumnModel().getColumn(1).setPreferredWidth(70); // "별점" 컬럼
	        table.getColumnModel().getColumn(2).setPreferredWidth(450); // "리뷰 내용" 컬럼

	        table.setRowHeight(32);

	        JScrollPane scroll = new JScrollPane(table);
	        add(scroll, BorderLayout.CENTER);

	        JButton closeBtn = new JButton("닫기");
	        closeBtn.addActionListener(e -> dispose());
	        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        btnPanel.add(closeBtn);
	        add(btnPanel, BorderLayout.SOUTH);
	        
	        
	        setSize(700,400);
	        setResizable(false);
	        setLocationRelativeTo(parent);
	        
	       
	    }



}


