package gotcha.ui.home;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.service.TagService;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TagSelectionScreen extends JPanel {
    private final TagService tagService = new TagService();
    private final int userId;

    public TagSelectionScreen(int userId) {
        this.userId = userId;

        setLayout(new BorderLayout());
        JLabel title = new JLabel("당신을 표현할 태그를 선택하세요", SwingConstants.CENTER);
        title.setFont(FontLoader.loadCustomFont(20f));
        add(title, BorderLayout.NORTH);

        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new GridLayout(0, 4, 10, 10)); // 태그 버튼 배열

        Map<Integer, JCheckBox> checkBoxes = new LinkedHashMap<>();
        List<String> tagList = tagService.getAllTags();

        for (int i = 0; i < tagList.size(); i++) {
            int tagId = i + 1;
            JCheckBox box = new JCheckBox(tagList.get(i));
            checkBoxes.put(tagId, box);
            tagPanel.add(box);
        }

        add(tagPanel, BorderLayout.CENTER);

        JButton submitBtn = new JButton("완료");
        submitBtn.setFont(FontLoader.loadCustomFont(16f));
        submitBtn.addActionListener(e -> {
            List<Integer> selectedTagIds = new ArrayList<>();
            for (Map.Entry<Integer, JCheckBox> entry : checkBoxes.entrySet()) {
                if (entry.getValue().isSelected()) {
                    selectedTagIds.add(entry.getKey());
                }
            }

            long mbtiCount = selectedTagIds.stream().filter(id -> id >= 1 && id <= 16).count();
            long traitCount = selectedTagIds.stream().filter(id -> id >= 17 && id <= 20).count();

            if (mbtiCount != 1 || traitCount < 1) {
                JOptionPane.showMessageDialog(this, "MBTI는 1개, 성향은 최소 1개 선택해주세요!");
                return;
            }

            boolean success = tagService.saveUserTags(userId, selectedTagIds);
            if (success) {
                JOptionPane.showMessageDialog(this, "태그 선택 완료!");
                Main.setScreen(new HomeScreen());
            } else {
                JOptionPane.showMessageDialog(this, "태그 저장 실패. 다시 시도해주세요.");
            }
        });

        add(submitBtn, BorderLayout.SOUTH);
    }
}
