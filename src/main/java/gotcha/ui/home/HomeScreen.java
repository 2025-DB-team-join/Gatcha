package gotcha.ui.home;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.dto.PublicGroup;
import gotcha.service.HomeService;
import gotcha.service.ScrapService;
import gotcha.ui.GroupFormScreen;
import gotcha.ui.board.BoardScreen;
import gotcha.ui.manage.ManageGroupScreen;
import gotcha.ui.mypage.MyPageScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class HomeScreen extends JPanel {

    private JTable groupTable, top5Table;
    private JComboBox<String> categoryToggle;
    private JTextField searchField;
    private final HomeService service = new HomeService();
<<<<<<< HEAD
    private List<Vector<String>> currentGroupData;
=======
    private final ScrapService scrapService = new ScrapService();
>>>>>>> origin

    public HomeScreen() {
        FontLoader.applyGlobalFont(14f);
        top5Table = new JTable();
        int userId = Session.loggedInUserId;

        JPanel searchCategoryPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("검색");
        searchPanel.add(new JLabel("소모임 검색:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        categoryPanel.add(new JLabel("카테고리별 조회:"));
        String[] categories = {"전체", "봉사활동", "사교/인맥", "문화/공연/축제", "인문학/책/글", "공예/만들기", "댄스/무용", "운동/스포츠", "외국/언어", "아웃도어/여행", "음악/악기", "차/바이크", "사진/영상", "스포츠관람", "게임/오락", "요리/제조", "반려동물", "자기계발", "테크/프로그래밍", "패션/뷰티", "수집/덕질"};
        categoryToggle = new JComboBox<>(categories);
        categoryPanel.add(categoryToggle);

        searchCategoryPanel.add(searchPanel, BorderLayout.WEST);
        searchCategoryPanel.add(categoryPanel, BorderLayout.EAST);

<<<<<<< HEAD
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
=======
        // 하단 버튼 왼쪽 (create, manage, board, region/gender)
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
>>>>>>> origin
        JButton createBtn = new JButton("소모임 생성");
        JButton manageBtn = new JButton("소모임 관리");
        JButton boardBtn = new JButton("게시판");
        JButton regionGenderBtn = new JButton("지역/성별로 조회");
        leftButtonPanel.add(createBtn);
        leftButtonPanel.add(manageBtn);
        leftButtonPanel.add(boardBtn);
        leftButtonPanel.add(regionGenderBtn);

        // 하단 버튼 오른쪽 (mypage)
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton myPageBtn = new JButton("마이페이지");
        rightButtonPanel.add(myPageBtn);

        // 하단 전체 패널
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        // topPanel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(searchCategoryPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

<<<<<<< HEAD
        DefaultTableModel initialMainModel = new DefaultTableModel();
        initialMainModel.setColumnIdentifiers(new String[]{"소모임 이름", "소개", "상태", "지역"});
        groupTable = new JTable(initialMainModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
=======
        JLabel hintLabel = new JLabel("※ 소모임을 더블클릭하면 스크랩할 수 있습니다.");
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setFont(hintLabel.getFont().deriveFont(Font.ITALIC, 12f));
        centerPanel.add(hintLabel);
>>>>>>> origin

        JScrollPane tableScroll = new JScrollPane(groupTable);

        centerPanel.add(Box.createVerticalStrut(20));
        tableScroll.setBorder(BorderFactory.createTitledBorder("소모임 목록"));
        tableScroll.setPreferredSize(new Dimension(780, 200));
        centerPanel.add(tableScroll);

        centerPanel.add(Box.createVerticalStrut(20));

        JScrollPane top5Scroll = new JScrollPane(top5Table);
        top5Scroll.setBorder(BorderFactory.createTitledBorder("\nTop 5 활발한 소모임"));
        top5Scroll.setPreferredSize(new Dimension(780, 200));
        centerPanel.add(top5Scroll);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        createBtn.addActionListener(e -> Main.setScreen(new GroupFormScreen()));
        boardBtn.addActionListener(e -> Main.setScreen(new BoardScreen()));

        myPageBtn.addActionListener(e -> Main.setScreen(new MyPageScreen(userId)));
        manageBtn.addActionListener(e -> Main.setScreen(new ManageGroupScreen(userId)));
        searchBtn.addActionListener(e -> refreshTables());
        categoryToggle.addActionListener(e -> refreshTables());
        regionGenderBtn.addActionListener(e -> Main.setScreen(new RegionGenderScreen()));

<<<<<<< HEAD
=======
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

>>>>>>> origin
        myPageBtn.addActionListener(e -> {
            if (userId != -1) {
                Main.setScreen(new MyPageScreen(userId));
            } else {
                JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 다시 로그인해주세요.");
            }
        });

        groupTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = groupTable.getSelectedRow();
                if (row != -1 && e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    e.consume();
                    int classId = Integer.parseInt(currentGroupData.get(row).get(0));
                    PublicGroup group = service.getGroupDetailScreen(classId);
                    Main.setScreen(new OtherGroupDetailScreen(group, userId));
                }
            }
        });


        refreshTables();

        groupTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = groupTable.rowAtPoint(evt.getPoint());
                if (row >= 0 && evt.getClickCount() == 2) {
                    String title = (String) groupTable.getValueAt(row, 1);
                    int confirm = JOptionPane.showConfirmDialog(
                            HomeScreen.this,
                            "'" + title + "' 소모임을 스크랩하시겠습니까?",
                            "스크랩 확인",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        int classId = Integer.parseInt((String) groupTable.getValueAt(row, 0));
                        boolean success = scrapService.addScrap(Session.loggedInUserId, classId);
                        if (success) {
                            JOptionPane.showMessageDialog(HomeScreen.this, "스크랩이 완료되었습니다.");
                        } else {
                            JOptionPane.showMessageDialog(HomeScreen.this, "이미 스크랩했거나 실패했습니다.");
                        }
                    }
                }
            }
        });


    }

    private void refreshTables() {
<<<<<<< HEAD
        DefaultTableModel mainModel = new DefaultTableModel();
        mainModel.setColumnIdentifiers(new String[]{"ID", "소모임 이름", "소개", "상태", "지역"});
        currentGroupData = service.loadGroupDetails(mainModel, searchField.getText(), (String) categoryToggle.getSelectedItem());
=======
        DefaultTableModel mainModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        mainModel.setColumnIdentifiers(new String[]{"class_id", "소모임 이름", "소개", "상태", "지역"}); // class_id 포함

        service.loadGroupDetails(mainModel, searchField.getText(), (String) categoryToggle.getSelectedItem());

        groupTable.setModel(mainModel);
        groupTable.getColumnModel().getColumn(0).setMinWidth(0);
        groupTable.getColumnModel().getColumn(0).setMaxWidth(0);
        groupTable.getColumnModel().getColumn(0).setWidth(0);

        service.loadGroupDetails(mainModel, searchField.getText(), (String) categoryToggle.getSelectedItem());
>>>>>>> origin

        groupTable.setModel(mainModel);
        groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // ID 컬럼 숨기기
        groupTable.getColumnModel().getColumn(0).setMinWidth(0);
        groupTable.getColumnModel().getColumn(0).setMaxWidth(0);
        groupTable.getColumnModel().getColumn(0).setWidth(0);
        
        DefaultTableModel top5Model = new DefaultTableModel();
        top5Model.setColumnIdentifiers(new String[]{"순위", "소모임 이름", "출석률", "모임 설명"});
        service.loadGroupAttendance(top5Model, searchField.getText(), (String) categoryToggle.getSelectedItem());

<<<<<<< HEAD
        top5Table.setModel(top5Model);
        top5Table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
=======
        // groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // top5Table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        groupTable.setModel(mainModel);
        groupTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        groupTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        groupTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        groupTable.getColumnModel().getColumn(4).setPreferredWidth(60);

        top5Table.setModel(top5Model);
        top5Table.getColumnModel().getColumn(0).setPreferredWidth(40);
        top5Table.getColumnModel().getColumn(1).setPreferredWidth(150);
        top5Table.getColumnModel().getColumn(2).setPreferredWidth(80);
        top5Table.getColumnModel().getColumn(3).setPreferredWidth(400);
>>>>>>> origin
    }
}
