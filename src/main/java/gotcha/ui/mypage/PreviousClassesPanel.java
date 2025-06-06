package gotcha.ui.mypage;

import gotcha.service.PreviousClassesService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class PreviousClassesPanel extends JPanel {
	private JTable previousClassesTable;
	private PreviousClassesService previousClassesService;
	private int userId;
	
	public PreviousClassesPanel(int userId) {
		this.userId = userId;
		this.previousClassesService = new PreviousClassesService();
		
		setLayout(new BorderLayout());
		
		previousClassesTable = new JTable();
		previousClassesTable.setRowHeight(32);

		JScrollPane tableScroll = new JScrollPane(previousClassesTable);
		tableScroll.setPreferredSize(new Dimension(800, 150));
		add(tableScroll, BorderLayout.CENTER);

		refreshTable();
	}
	
	private void refreshTable() {
	    DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(new String[] {"소모임 이름", "설명"});

	    List<Vector<String>> previousClasses = previousClassesService.getPreviousClasses(userId);

	    if (previousClasses != null) {
	        for (Vector<String> row : previousClasses) {
	            if (row.size() >= 2) {
	                model.addRow(new String[] {row.get(0), row.get(1)}); // ✅ 수정
	            } else {
	                System.out.println("🚨 잘못된 row: " + row);
	            }
	        }
	    }

	    previousClassesTable.setModel(model);
	    previousClassesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}


}