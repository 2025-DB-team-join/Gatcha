package gotcha.ui.board;

import gotcha.Main;
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

        // 상단 패널 (제목만 중앙)
        JLabel titleLabel = new JLabel("참여 중인 소모임");
        titleLabel.setFont(FontLoader.loadCustomFont(20f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 표 생성
        tableModel = new DefaultTableModel(new String[]{
                "소모임 ID", "소모임 이름", "요일", "시작 시간", "진행 시간(분)"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        classTable = new JTable(tableModel);
        classTable.setRowHeight(32);

        // 컬럼 폭 지정
        classTable.getColumnModel().getColumn(0).setMinWidth(0);
        classTable.getColumnModel().getColumn(0).setMaxWidth(0);
        classTable.getColumnModel().getColumn(0).setWidth(0); // 소모임 ID 숨김
        classTable.getColumnModel().getColumn(1).setPreferredWidth(300); // 소모임 이름
        classTable.getColumnModel().getColumn(2).setPreferredWidth(100);  // 요일
        classTable.getColumnModel().getColumn(3).setPreferredWidth(150); // 시작 시간
        classTable.getColumnModel().getColumn(4).setPreferredWidth(100); // 진행 시간

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

        // 우측 하단 버튼 패널
        JButton backButton = new JButton("← 뒤로가기");
        backButton.setFont(FontLoader.loadCustomFont(14f));
        backButton.addActionListener(e -> Main.setScreen(new gotcha.ui.home.HomeScreen()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadParticipatedClasses(userId);

        
    }

    private void loadParticipatedClasses(int userId) {
        UserDAO userDAO = new UserDAO();
        List<Map<String, Object>> classList = userDAO.getParticipatedClasses(userId);

        tableModel.setRowCount(0); // 초기화
        for (Map<String, Object> row : classList) {
            tableModel.addRow(new Object[]{
                    row.get("class_id"),
                    row.get("title"),
                    row.get("day_of_week") != null ? row.get("day_of_week") : "-",
                    row.get("start_time") != null ? row.get("start_time").toString() : "-",
                    row.get("duration") != null ? row.get("duration").toString() : "-"
            });
        }
    }

    private void openClassBoardWindow(int classId) {
        Main.setScreen(new ClassBoardScreen(classId));
    }
}
