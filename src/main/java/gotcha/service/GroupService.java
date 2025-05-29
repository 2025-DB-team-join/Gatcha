package gotcha.service;

import gotcha.dao.GroupDAO;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class GroupService {

    private final GroupDAO dao = new GroupDAO();

    public void loadGroupAttendance(DefaultTableModel top5Model, String keyword, String category) {
        List<Vector<String>> groups = dao.getGroupsForAttendance(keyword, category);

        int rank = 1;
        for (Vector<String> row : groups) {
            if (rank <= 5) {
                Vector<String> topRow = new Vector<>();
                topRow.add(rank + "위");
                topRow.add(row.get(0)); // 소모임 이름
                topRow.add(row.get(2)); // 출석률
                top5Model.addRow(topRow);
                rank++;
            }
        }
    }


    public void loadGroupDetails(DefaultTableModel mainModel, String keyword, String category) {
        List<Vector<String>> groups = dao.getGroupDetails(keyword, category);
        for (Vector<String> row : groups) {
            mainModel.addRow(row);
        }
    }
}
