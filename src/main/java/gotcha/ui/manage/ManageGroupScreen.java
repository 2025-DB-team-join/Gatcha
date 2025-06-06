package gotcha.ui.manage;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.dao.CurrentGroupDAO;
import gotcha.dao.HostedClassDAO.HostedClass;
import gotcha.dto.PublicGroup;
import gotcha.service.CurrentGroupService;
import gotcha.service.ManageGroupService;
import gotcha.ui.home.OtherGroupDetailScreen;
import gotcha.ui.manage.MyGroupDetailScreen;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class ManageGroupScreen extends JPanel {
    private final ManageGroupService manageService = new ManageGroupService();
    private final CurrentGroupService currentService = new CurrentGroupService();

    private JTable hostGroupTable, participantGroupTable;
    private JComboBox<String> statusFilter;
    private DefaultTableModel participantModel;

    private List<HostedClass> hostedClasses;
    private List<CurrentGroupDAO.CurrentGroup> currentGroups;

    private int userId;

    public ManageGroupScreen(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("소모임 관리"));

        JPanel hostPanel = new JPanel(new BorderLayout());
        hostPanel.setBorder(BorderFactory.createTitledBorder("주최중인 소모임"));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("상태 필터:");
        String[] statuses = {"전체", "모집중", "진행중", "진행완료"};
        statusFilter = new JComboBox<>(statuses);
        filterPanel.add(statusLabel);
        filterPanel.add(statusFilter);
        hostPanel.add(filterPanel, BorderLayout.NORTH);

        hostGroupTable = new JTable();
        hostGroupTable.setRowHeight(32);
        JScrollPane hostScroll = new JScrollPane(hostGroupTable);
        hostPanel.add(hostScroll, BorderLayout.CENTER);

        JButton viewHostBtn = new JButton("상세 보기");
        viewHostBtn.addActionListener(e -> handleHostView());
        hostPanel.add(viewHostBtn, BorderLayout.SOUTH);

        JPanel participantPanel = new JPanel(new BorderLayout());
        participantPanel.setBorder(BorderFactory.createTitledBorder("참여중인 소모임"));

        String[] cols = {"이름", "카테고리", "지역", "주최자", "운영 요일"};
        participantModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        participantGroupTable = new JTable(participantModel);
        participantGroupTable.setRowHeight(32);
        JScrollPane participantScroll = new JScrollPane(participantGroupTable);
        participantPanel.add(participantScroll, BorderLayout.CENTER);

        JButton viewParticipantBtn = new JButton("상세 보기");
        viewParticipantBtn.addActionListener(e -> handleParticipantView());
        participantPanel.add(viewParticipantBtn, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(hostPanel);
        centerPanel.add(participantPanel);
        add(centerPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> Main.setScreen(new gotcha.ui.home.HomeScreen()));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        statusFilter.addActionListener(e -> loadHostGroups());

        loadHostGroups();
        loadParticipantGroups();
    }

    private void loadHostGroups() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"클래스명", "카테고리", "지역", "요일", "인원 현황", "상태"});

        hostedClasses = manageService.getMyGroups(userId);
        String selectedStatus = (String) statusFilter.getSelectedItem();

        for (HostedClass row : hostedClasses) {
            if (!"전체".equals(selectedStatus) && !row.getStatus().equals(selectedStatus)) continue;
            Vector<String> displayRow = new Vector<>();
            displayRow.add(row.getTitle());
            displayRow.add(row.getCategory());
            displayRow.add(row.getRegion());
            displayRow.add(row.getDays());
            displayRow.add(row.getUserCount() + " / " + row.getMax());
            displayRow.add(row.getStatus());
            model.addRow(displayRow);
        }
        hostGroupTable.setModel(model);
        hostGroupTable.setRowHeight(32);
    }

    private void loadParticipantGroups() {
        participantModel.setRowCount(0);
        currentGroups = currentService.getCurrentGroups(userId);

        for (CurrentGroupDAO.CurrentGroup g : currentGroups) {
            participantModel.addRow(new Object[]{
                g.getTitle(), g.getCategory(), g.getRegion(), g.getHostNickname(), g.getDays()
            });
        }
    }

    private void handleHostView() {
        int selected = hostGroupTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "상세 조회할 소모임을 선택해주세요.");
            return;
        }
        String selectedTitle = (String) hostGroupTable.getValueAt(selected, 0);
        int selectedClassId = -1;

        for (HostedClass cls : hostedClasses) {
            if (cls.getTitle().equals(selectedTitle)) {
                selectedClassId = cls.getClassId();
                break;
            }
        }

        if (selectedClassId != -1) {
            Main.setScreen(new MyGroupDetailScreen(selectedClassId, userId));
        } else {
            JOptionPane.showMessageDialog(this, "소모임 ID를 찾을 수 없습니다.");
        }
    }

    private void handleParticipantView() {
        int selected = participantGroupTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "상세 조회할 소모임을 선택해주세요.");
            return;
        }
        String selectedTitle = (String) participantGroupTable.getValueAt(selected, 0);

        for (CurrentGroupDAO.CurrentGroup g : currentGroups) {
            if (g.getTitle().equals(selectedTitle)) {
                Main.setScreen(new OtherGroupDetailScreen(g.getClassId(), userId));
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "소모임 ID를 찾을 수 없습니다.");
    }
}

