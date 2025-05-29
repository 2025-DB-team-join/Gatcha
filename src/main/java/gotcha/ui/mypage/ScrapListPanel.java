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
		refreshTable();
	}
	
	private void refreshTable() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[]{"소모임 이름", "설명", "활동 장소", "상태", "마감일"});
		List<Vector<String>> scrappedClass = scrapService.getScrappedClasses(userId);
		
		if (scrappedClass != null) {
			for (Vector<String> row : scrappedClass) {
				model.addRow(row);
			}
		}
		
		scrapTable.setModel(model);
		scrapTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
}