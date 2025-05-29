package gotcha.ui;

import gotcha.service.RegionGenderService;
import gotcha.dao.RegionGenderDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RegionGenderScreen extends JPanel {

    String[] regions = {
        "전체", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
        "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
        "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
    };
    String[] genders = {"전체", "남성", "여성"};

    JComboBox<String> regionCombo;
    JComboBox<String> genderCombo;
    JTable resultTable;
    DefaultTableModel tableModel;
    RegionGenderService service = new RegionGenderService();

    public RegionGenderScreen() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("지역/성별별 클래스 순위"));

        JPanel topPanel = new JPanel();
        regionCombo = new JComboBox<>(regions);
        genderCombo = new JComboBox<>(genders);
        JButton searchBtn = new JButton("검색");

        topPanel.add(new JLabel("지역:"));
        topPanel.add(regionCombo);
        topPanel.add(new JLabel("성별:"));
        topPanel.add(genderCombo);
        topPanel.add(searchBtn);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton backBtn = new JButton("뒤로가기");
        bottomPanel.add(backBtn);

        String[] cols = {"순위", "클래스명", "설명", "카테고리", "요일", "가입자수"};
        tableModel = new DefaultTableModel(cols, 0);
        resultTable = new JTable(tableModel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultTable), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> searchRank());
        backBtn.addActionListener(e -> gotcha.Main.setScreen(new HomeScreen()));

        setVisible(true);
    }

    // 한글 → DB gender 값 매핑 ('남성'→'M', '여성'→'F')
    private String genderToDbValue(String genderKor) {
        switch (genderKor) {
            case "남성": return "M";
            case "여성": return "F";
            default: return null; // "전체"
        }
    }

    private void searchRank() {
        String region = (String) regionCombo.getSelectedItem();
        String genderKor = (String) genderCombo.getSelectedItem();
        String gender = genderToDbValue(genderKor);

        List<RegionGenderDAO.ClassRank> classList = service.getRankedClassesWithDays(region, gender);

        tableModel.setRowCount(0);

        if (classList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "해당 조건에 해당하는 class가 없습니다.");
        } else {
            for (RegionGenderDAO.ClassRank c : classList) {
                Object[] row = {
                    c.getRank() + "위",
                    c.getTitle(),
                    c.getContext(),
                    c.getCategory(),
                    c.getDays(),
                    c.getUserCount()
                };
                tableModel.addRow(row);
            }
        }
    }
}
