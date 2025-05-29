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
		
		JButton cancelButton = new JButton("스크랩 취소하기");
        cancelButton.addActionListener(e -> cancelSelectedScrap());
        add(cancelButton, BorderLayout.SOUTH);
        
		refreshTable();
	}
	
	private void refreshTable() {
		DefaultTableModel model = new DefaultTableModel();
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
	
	private void cancelSelectedScrap() {
        int selectedRow = scrapTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "스크랩 취소할 소모임을 선택하세요.");
            return;
        }

        int classId = Integer.parseInt((String) scrapTable.getValueAt(selectedRow, 0));

        boolean success = scrapService.cancelScrap(userId, classId);
        if (success) {
            JOptionPane.showMessageDialog(this, "스크랩이 취소되었습니다.");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "스크랩 취소에 실패했습니다.");
        }
    }
	
}