package gotcha.ui;

import gotcha.Main;
import gotcha.common.Session;
import gotcha.dao.HostedClassDAO.HostedClass;
import gotcha.service.ManageGroupService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class ManageGroupScreen extends JPanel {
    private final ManageGroupService service = new ManageGroupService();
    private JTable groupTable;
    private JComboBox<String> statusFilter;
    private int userId;
    private int classId;
    
    private List<HostedClass> hostedClasses;

    public ManageGroupScreen(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("내가 만든 소모임 관리"));

        // 상태 필터 드롭다운
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel statusLabel = new JLabel("상태 필터:");
        String[] statuses = {"전체", "모집중", "진행중", "진행완료"};
        statusFilter = new JComboBox<>(statuses);
        filterPanel.add(statusLabel);
        filterPanel.add(statusFilter);
        add(filterPanel, BorderLayout.NORTH);

        // 테이블
        groupTable = new JTable();
        JScrollPane tableScroll = new JScrollPane(groupTable);
        add(tableScroll, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton viewBtn = new JButton("상세 보기");
        JButton backBtn = new JButton("뒤로가기");
        buttonPanel.add(viewBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // 이벤트 처리
        statusFilter.addActionListener(e -> loadGroupTable(userId));
        viewBtn.addActionListener(e -> handleView(classId));
        backBtn.addActionListener(e -> Main.setScreen(new gotcha.ui.home.HomeScreen()));

        loadGroupTable(userId);
    }

    private void loadGroupTable(int userId) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"클래스명", "카테고리", "지역", "요일", "정원", "상태"});

        String selectedStatus = (String) statusFilter.getSelectedItem();
        hostedClasses = service.getMyGroups(userId);

        if (hostedClasses != null) {
            for (HostedClass row : hostedClasses) {
                if (!selectedStatus.equals("전체") && !row.getStatus().equals(selectedStatus)) continue;

                Vector<String> displayRow = new Vector<>();
                displayRow.add(row.getTitle());
                displayRow.add(row.getCategory());
                displayRow.add(row.getRegion());
                displayRow.add(row.getDays());
                displayRow.add(String.valueOf(row.getUserCount()));
                displayRow.add(row.getStatus());
                model.addRow(displayRow);
            }
        }
        groupTable.setModel(model);
    }


    private void handleView(int classId) {
        int selected = groupTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "상세 조회할 소모임을 선택해주세요.");
            return;
        }

        String selectedTitle = (String) groupTable.getValueAt(selected, 0);
        classId = -1;

        for (HostedClass cls : hostedClasses) {
            if (cls.getTitle().equals(selectedTitle)) {
                classId = cls.getClassId();
                break;
            }
        }

        if (classId != -1) {
            Main.setScreen(new ViewGroupScreen(classId));
        } else {
            JOptionPane.showMessageDialog(this, "소모임 ID를 찾을 수 없습니다.");
        }
    }


    private void handleDelete() {
        int selected = groupTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 소모임을 선택해주세요.");
            return;
        }
    }
}


