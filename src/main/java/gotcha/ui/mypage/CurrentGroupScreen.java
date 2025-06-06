package gotcha.ui.mypage;

import gotcha.service.CurrentGroupService;
import gotcha.dao.CurrentGroupDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CurrentGroupScreen extends JPanel {
    CurrentGroupService service = new CurrentGroupService();
    JTable resultTable;
    DefaultTableModel tableModel;

    public CurrentGroupScreen(int userId) {
        setLayout(new BorderLayout());

        // ↓↓↓ classId(클래스ID) 컬럼을 제거 ↓↓↓
        String[] cols = {"이름", "카테고리", "지역", "주최자", "운영 요일"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(32);
		JScrollPane tableScroll = new JScrollPane(resultTable);
		tableScroll.setPreferredSize(new Dimension(800, 150));
		add(tableScroll, BorderLayout.CENTER);

		setMaximumSize(new Dimension(800, 200));
        
        resultTable.addMouseListener(new java.awt.event.MouseAdapter() {
        	@Override
        	public void mouseClicked(java.awt.event.MouseEvent evt) {
        		int row = resultTable.rowAtPoint(evt.getPoint());
        		if (row>=0) {
        			String title = (String) tableModel.getValueAt(row, 0);
        			
        			int confirm = JOptionPane.showConfirmDialog(
        				CurrentGroupScreen.this,
        				"'" + title + "' 참여를 취소하시겠습니까?",
        				"참여 취소 확인",
        				JOptionPane.YES_NO_OPTION
        			);
        			
        			if (confirm == JOptionPane.YES_OPTION) {
        				List<CurrentGroupDAO.CurrentGroup> groupList = service.getCurrentGroups(userId);
        				for (CurrentGroupDAO.CurrentGroup g : groupList) {
        					if (g.getTitle().equals(title)) {
        						boolean success = service.cancelParticipation(userId, g.getClassId());
        						if (success) {
        							JOptionPane.showMessageDialog(CurrentGroupScreen.this, "참여가 취소되었습니다.");
        							loadCurrentGroups(userId);
        						} else {
        							JOptionPane.showMessageDialog(CurrentGroupScreen.this, "참여 취소를 실패했습니다. 다시 시도해주세요.");
        						}
        						break;
        					}
        				}
        			}
        		}
        	}
        });

        loadCurrentGroups(userId);
    }

    private void loadCurrentGroups(int userId) {
        List<CurrentGroupDAO.CurrentGroup> groupList = service.getCurrentGroups(userId);
        tableModel.setRowCount(0);
        for (CurrentGroupDAO.CurrentGroup g : groupList) {
            // ↓↓↓ classId는 표에 넣지 않음 ↓↓↓
            Object[] row = {
                g.getTitle(),
                g.getCategory(),
                g.getRegion(),
                g.getHostNickname(),
                g.getDays()
            };
            tableModel.addRow(row);
        }
    }
}
