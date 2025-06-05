package gotcha.ui.home;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.service.HomeService;
import gotcha.ui.GroupFormScreen;
import gotcha.ui.board.BoardScreen;
import gotcha.ui.mypage.MyPageScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HomeScreen extends JPanel {

    private JTable groupTable, top5Table;
    private JComboBox<String> categoryToggle;
    private JTextField searchField;
    private final HomeService service = new HomeService();

    public HomeScreen() {
        FontLoader.applyGlobalFont(14f);
        groupTable = new JTable();
        top5Table = new JTable();
        int userId = Session.loggedInUserId;

        // 검색 + 카테고리 패널
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

        // topPanel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton createBtn = new JButton("소모임 생성");
        JButton boardBtn = new JButton("게시판");
        JButton myPageBtn = new JButton("마이페이지");
        buttonPanel.add(createBtn);
        buttonPanel.add(boardBtn);
        buttonPanel.add(myPageBtn);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(searchCategoryPanel);
        topPanel.add(buttonPanel);

        // centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JScrollPane tableScroll = new JScrollPane(groupTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("소모임 목록"));
        tableScroll.setPreferredSize(new Dimension(780, 200));
        centerPanel.add(tableScroll);

        centerPanel.add(Box.createVerticalStrut(20));

        JScrollPane top5Scroll = new JScrollPane(top5Table);
        top5Scroll.setBorder(BorderFactory.createTitledBorder("\nTop 5 활발한 소모임"));
        top5Scroll.setPreferredSize(new Dimension(780, 200));
        centerPanel.add(top5Scroll);

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton regionGenderBtn = new JButton("지역/성별로 조회");
        bottomButtonPanel.add(regionGenderBtn);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        // 이벤트 처리
        createBtn.addActionListener(e -> Main.setScreen(new GroupFormScreen()));
        boardBtn.addActionListener(e -> {
            JFrame boardFrame = new JFrame("게시판");
            boardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            boardFrame.setSize(800, 550);
            boardFrame.setLocationRelativeTo(null);

            boardFrame.add(new BoardScreen());
            boardFrame.setVisible(true);
        });

        myPageBtn.addActionListener(e -> Main.setScreen(new MyPageScreen(userId)));
        searchBtn.addActionListener(e -> refreshTables());
        categoryToggle.addActionListener(e -> refreshTables());
        regionGenderBtn.addActionListener(e -> Main.setScreen(new RegionGenderScreen()));

        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        myPageBtn.addActionListener(e -> {
            if (userId != -1) {
                Main.setScreen(new MyPageScreen(userId));
            } else {
                JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 다시 로그인해주세요.");
            }
        });

        // 초기 로드
        refreshTables();
    }

    private void refreshTables() {
        DefaultTableModel mainModel = new DefaultTableModel();
        mainModel.setColumnIdentifiers(new String[]{"소모임 이름", "소개", "상태", "지역"});
        service.loadGroupDetails(mainModel, searchField.getText(), (String) categoryToggle.getSelectedItem());

        DefaultTableModel top5Model = new DefaultTableModel();
        top5Model.setColumnIdentifiers(new String[]{"소모임 이름", "카테고리", "출석률", "횟수"});
        service.loadGroupAttendance(top5Model, searchField.getText(), (String) categoryToggle.getSelectedItem());

        groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        top5Table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        groupTable.setModel(mainModel);
        top5Table.setModel(top5Model);
    }
}