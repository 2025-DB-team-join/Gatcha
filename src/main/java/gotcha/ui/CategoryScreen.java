package gotcha.ui;

import gotcha.service.CategoryService;
import gotcha.dao.CategoryDAO.CategoryClass;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryScreen extends JPanel {

    String[] categories = {
        "봉사활동", "사교/인맥", "문화/공연/축제", "인문학/책/글", "공예/만들기",
        "댄스/무용", "운동/스포츠", "외국/언어", "아웃도어/여행", "음악/악기",
        "차/바이크", "사진/영상", "스포츠관람", "게임/오락", "요리/제조",
        "반려동물", "자기계발", "테크/프로그래밍", "패션/뷰티", "수집/덕질"
    };

    JComboBox<String> categoryCombo;
    JTextArea resultArea;
    CategoryService service = new CategoryService();

    public CategoryScreen() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("카테고리별 클래스 조회"));

        JPanel topPanel = new JPanel();
        categoryCombo = new JComboBox<>(categories);
        JButton searchBtn = new JButton("조회");

        topPanel.add(new JLabel("카테고리 선택: "));
        topPanel.add(categoryCombo);
        topPanel.add(searchBtn);

        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> searchClasses());
    }

    private void searchClasses() {
        String category = (String) categoryCombo.getSelectedItem();
        List<CategoryClass> classList = service.searchClassesByCategory(category);

        resultArea.setText("");
        if (classList.isEmpty()) {
            resultArea.setText("해당 카테고리에 해당하는 class가 없습니다.");
        } else {
            for (CategoryClass c : classList) {
                resultArea.append(String.format("ID: %d | 제목: %s | 설명: %s | 카테고리: %s\n",
                    c.getClassId(), c.getTitle(), c.getContext(), c.getCategory()));
            }
        }
    }
}
