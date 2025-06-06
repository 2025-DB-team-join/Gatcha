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
    private final JLabel selectedTagsLabel = new JLabel("선택한 태그: ");

    public TagSelectionScreen(int userId) {
        FontLoader.applyGlobalFont(14f);
        this.userId = userId;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // 상단 제목
        JLabel title = new JLabel("당신을 표현할 태그를 선택하세요", SwingConstants.CENTER);
        title.setFont(FontLoader.loadCustomFont(22f));
        add(title, BorderLayout.NORTH);

        // 전체 태그 영역 (MBTI + 성향)
        JPanel tagPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // 2열
        Map<Integer, JCheckBox> checkBoxes = new LinkedHashMap<>();
        List<String> tagList = tagService.getAllTags();

        JPanel mbtiPanel = createTagSubPanel("MBTI");
        JPanel traitPanel = createTagSubPanel("선호하는 모임 유형");

        for (int i = 0; i < tagList.size(); i++) {
            int tagId = i + 1;
            String tag = tagList.get(i);
            JCheckBox box = new JCheckBox(tag);
            box.setFont(box.getFont().deriveFont(15f));
            box.setPreferredSize(new Dimension(150, 30));

            // 태그 선택 시 하단에 실시간 반영
            box.addActionListener(e -> updateSelectedTags(checkBoxes));

            checkBoxes.put(tagId, box);

            if (tagId >= 1 && tagId <= 16) {
                mbtiPanel.add(box);
            } else {
                traitPanel.add(box);
            }
        }

        JScrollPane mbtiScroll = new JScrollPane(mbtiPanel);
        mbtiScroll.setBorder(BorderFactory.createTitledBorder("MBTI"));
        JScrollPane traitScroll = new JScrollPane(traitPanel);
        traitScroll.setBorder(BorderFactory.createTitledBorder("선호하는 모임 유형"));

        tagPanel.add(mbtiScroll);
        tagPanel.add(traitScroll);
        add(tagPanel, BorderLayout.CENTER);

        // 하단: 선택된 태그 라벨 + 제출 버튼
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        selectedTagsLabel.setFont(FontLoader.loadCustomFont(14f));
        bottomPanel.add(selectedTagsLabel, BorderLayout.NORTH);

        JButton submitBtn = new JButton("선택 완료");
        submitBtn.setFont(FontLoader.loadCustomFont(16f));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(submitBtn);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // 제출 버튼 이벤트
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
    }

    private JPanel createTagSubPanel(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    private void updateSelectedTags(Map<Integer, JCheckBox> checkBoxes) {
        List<String> selected = new ArrayList<>();
        for (JCheckBox box : checkBoxes.values()) {
            if (box.isSelected()) selected.add(box.getText());
        }
        selectedTagsLabel.setText("선택한 태그: " + String.join(", ", selected));
    }
}
