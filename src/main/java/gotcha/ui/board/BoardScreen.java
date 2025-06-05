package gotcha.ui.board;

import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.dao.UserDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class BoardScreen extends JPanel {
    private final JTable classTable;
    private final DefaultTableModel tableModel;

    public BoardScreen() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        FontLoader.applyGlobalFont(14f);

        int userId = Session.loggedInUserId;

        JLabel titleLabel = new JLabel("참여 중인 소모임");
        titleLabel.setFont(FontLoader.loadCustomFont(20f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"소모임 ID", "소모임 이름"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        classTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(classTable);
        add(scrollPane, BorderLayout.CENTER);

        // 더블 클릭 시 새 창으로 게시판 열기
        classTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = classTable.getSelectedRow();
                    if (row != -1) {
                        int classId = (int) tableModel.getValueAt(row, 0);
                        openClassBoardWindow(classId);
                    }
                }
            }
        });

        loadParticipatedClasses(userId);
    }

    private void loadParticipatedClasses(int userId) {
        UserDAO userDAO = new UserDAO();
        List<Map<String, Object>> classList = userDAO.getParticipatedClasses(userId);

        tableModel.setRowCount(0); // 초기화
        for (Map<String, Object> row : classList) {
            tableModel.addRow(new Object[]{
                    row.get("class_id"),
                    row.get("title")
            });
        }
    }

    // 별도 JFrame으로 ClassBoardScreen 열기
    private void openClassBoardWindow(int classId) {
        JFrame frame = new JFrame("소모임 게시판 - ID: " + classId);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(new ClassBoardScreen(classId));

        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.setAlwaysOnTop(false);
    }
}

