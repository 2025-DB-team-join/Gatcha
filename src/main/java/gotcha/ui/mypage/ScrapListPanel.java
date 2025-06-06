package gotcha.ui.mypage;

import gotcha.service.JoinService;
import gotcha.service.ScrapService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class ScrapListPanel extends JPanel {
	private JTable scrapTable;
	private ScrapService scrapService;
	private int userId;

	public ScrapListPanel(int userId) {
		this.userId = userId;
		this.scrapService = new ScrapService();

		setLayout(new BorderLayout());

		scrapTable = new JTable();
		scrapTable.setRowHeight(32);

		JScrollPane tableScroll = new JScrollPane(scrapTable);
		tableScroll.setPreferredSize(new Dimension(800, 150));
		add(tableScroll, BorderLayout.CENTER);

		scrapTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = scrapTable.rowAtPoint(evt.getPoint());
		        if (row >= 0 && evt.getClickCount() == 2) {
		            int classId = Integer.parseInt((String) scrapTable.getValueAt(row, 0));
		            String title = (String) scrapTable.getValueAt(row, 1);

		            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(ScrapListPanel.this), "스크랩 관리", true);
		            dialog.setLayout(new BorderLayout());

		            JLabel msgLabel = new JLabel("'" + title + "' 소모임에 대해 다음 작업을 선택하세요:");
		            msgLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		            dialog.add(msgLabel, BorderLayout.NORTH);

		            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		            JButton cancelScrapBtn = new JButton("스크랩 취소");
		            JButton joinBtn = new JButton("가입하기");
		            JButton closeBtn = new JButton("닫기");

		            cancelScrapBtn.addActionListener(e -> {
		                int confirm = JOptionPane.showConfirmDialog(
		                        dialog,
		                        "정말 스크랩을 취소하시겠습니까?",
		                        "확인",
		                        JOptionPane.YES_NO_OPTION
		                );
		                if (confirm == JOptionPane.YES_OPTION) {
		                    boolean success = scrapService.cancelScrap(userId, classId);
		                    if (success) {
		                        JOptionPane.showMessageDialog(dialog, "스크랩이 취소되었습니다.");
		                        dialog.dispose();
		                        refreshTable();
		                    } else {
		                        JOptionPane.showMessageDialog(dialog, "스크랩 취소에 실패했습니다.");
		                    }
		                }
		            });

		            joinBtn.addActionListener(e -> {
		                int confirm = JOptionPane.showConfirmDialog(
		                        dialog,
		                        "정말 가입하시겠습니까?",
		                        "가입 확인",
		                        JOptionPane.YES_NO_OPTION
		                );
		                if (confirm == JOptionPane.YES_OPTION) {
		                	JoinService joinService = new JoinService();
							int result = joinService.joinClass(userId, classId);
		                	if (result > 0) {
		                	    JOptionPane.showMessageDialog(dialog, "가입 요청이 완료되었습니다.");
		                	    dialog.dispose();
		                	} else {
		                	    JOptionPane.showMessageDialog(dialog, "이미 가입했거나 가입할 수 없습니다.");
		                	}
		                }});

		            closeBtn.addActionListener(e -> dialog.dispose());

		            btnPanel.add(cancelScrapBtn);
		            btnPanel.add(joinBtn);
		            btnPanel.add(closeBtn);
		            dialog.add(btnPanel, BorderLayout.CENTER);

		            dialog.pack();
		            dialog.setLocationRelativeTo(ScrapListPanel.this);
		            dialog.setVisible(true);
		        }
		    }
		});


		refreshTable();
	}

	private void refreshTable() {
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.setColumnIdentifiers(new String[]{"class_id", "소모임 이름", "설명", "활동 장소", "상태", "마감일"});
		List<Vector<String>> scrappedClass = scrapService.getScrappedClasses(userId);

		if (scrappedClass != null) {
			for (Vector<String> row : scrappedClass) {
				model.addRow(row);
			}
		}

		scrapTable.setModel(model);
		scrapTable.getColumnModel().getColumn(0).setMinWidth(0);
		scrapTable.getColumnModel().getColumn(0).setMaxWidth(0);
		scrapTable.getColumnModel().getColumn(0).setWidth(0);
		scrapTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
}
