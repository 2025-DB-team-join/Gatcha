package gotcha.ui.mypage;

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

		JScrollPane scrollPane = new JScrollPane(scrapTable);
		add(scrollPane, BorderLayout.CENTER);

		scrapTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = scrapTable.rowAtPoint(evt.getPoint());
				if (row >= 0 && evt.getClickCount() == 2) {  // 더블클릭 시
					String title = (String) scrapTable.getValueAt(row, 1);
					int confirm = JOptionPane.showConfirmDialog(
							ScrapListPanel.this,
							"'" + title + "' 스크랩을 취소하시겠습니까?",
							"스크랩 취소 확인",
							JOptionPane.YES_NO_OPTION
					);

					if (confirm == JOptionPane.YES_OPTION) {
						int classId = Integer.parseInt((String) scrapTable.getValueAt(row, 0));
						boolean success = scrapService.cancelScrap(userId, classId);
						if (success) {
							JOptionPane.showMessageDialog(ScrapListPanel.this, "스크랩이 취소되었습니다.");
							refreshTable();
						} else {
							JOptionPane.showMessageDialog(ScrapListPanel.this, "스크랩 취소에 실패했습니다.");
						}
					}
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
