package gotcha.ui;

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
        setBorder(BorderFactory.createTitledBorder("내가 참여중인 소모임 목록"));

        String[] cols = {"클래스ID", "이름", "카테고리", "지역", "주최자", "운영 요일"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultTable = new JTable(tableModel);
        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        loadCurrentGroups(userId);
    }

    private void loadCurrentGroups(int userId) {
        List<CurrentGroupDAO.CurrentGroup> groupList = service.getCurrentGroups(userId);
        tableModel.setRowCount(0);
        for (CurrentGroupDAO.CurrentGroup g : groupList) {
            Object[] row = {
                g.getClassId(),
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
