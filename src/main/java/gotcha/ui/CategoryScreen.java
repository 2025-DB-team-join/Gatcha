package gotcha.ui;

import gotcha.service.CategoryService;
import gotcha.dao.CategoryDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryScreen extends JPanel {

	String[] categories = {
		    "사교/인맥", "문화/공연/축제", "인문학/책/글", "공예/만들기",
		    "댄스/무용", "운동/스포츠", "외국/언어", "아웃도어/여행", "음악/악기",
		    "사진/영상", "게임/오락", "요리/제조", "반려동물", "자기계발", "테크/프로그래밍"
		};


    JComboBox<String> categoryCombo;
    JTable resultTable;
    DefaultTableModel tableModel;
    CategoryService service = new CategoryService();

    public CategoryScreen() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("카테고리별 클래스 및 요일 조회"));

        JPanel topPanel = new JPanel();
        categoryCombo = new JComboBox<>(categories);
        JButton searchBtn = new JButton("조회");

        topPanel.add(new JLabel("카테고리: "));
        topPanel.add(categoryCombo);
        topPanel.add(searchBtn);

        String[] cols = {"클래스명", "설명", "요일"};
        tableModel = new DefaultTableModel(cols, 0);
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(32);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> searchClasses());
    }

    private void searchClasses() {
        String category = (String) categoryCombo.getSelectedItem();
        List<CategoryDAO.CategoryClassDays> classList = service.searchClassesWithDaysByCategory(category);

        tableModel.setRowCount(0); // 기존 데이터 초기화
        if (classList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "해당 카테고리에 해당하는 class가 없습니다.");
        } else {
            for (CategoryDAO.CategoryClassDays c : classList) {
                Object[] row = {
                    c.getTitle(),
                    c.getContext(),
                    c.getDays()
                };
                tableModel.addRow(row);
            }
        }
    }
}
