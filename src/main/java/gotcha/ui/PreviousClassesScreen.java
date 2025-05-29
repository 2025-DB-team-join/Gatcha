package gotcha.ui;

import gotcha.service.PreviousClassesService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class PreviousClassesScreen extends JPanel {
	private JTable previousClassesTable;
	private PreviousClassesService previousClassesService;
	private int userId;
	
	public PreviousClassesScreen(int userId) {
		this.userId = userId;
		this.previousClassesService = new PreviousClassesService();
		
		setLayout(new BorderLayout());
		
		previousClassesTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(previousClassesTable);
		scrollPane.setBorder(BorderFactory.createTitledBorder("이전에 참여한 소모임"));
		add(scrollPane, BorderLayout.CENTER);
		refreshTable();
	}
	
	private void refreshTable() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {"소모임 이름", "설명"});
		List<Vector<String>> previousClasses = previousClassesService.getPreviousClasses(userId);
		
		if (previousClasses != null) {
			for (Vector<String> row : previousClasses) {
				model.addRow(new String[] {row.get(1), row.get(2)});
			}
		}
		previousClassesTable.setModel(model);
		previousClassesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
}