package gotcha.ui;

import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.dao.GroupDAO;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.sql.Timestamp;

public class GroupFormScreen extends JPanel {

	private static final long serialVersionUID = 1L; //default

	static class LengthRestrictedDocument extends PlainDocument {
		
		private static final long serialVersionUID = 1L; //default
		private final int maxLength;

        public LengthRestrictedDocument(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;
            if ((getLength() + str.length()) <= maxLength) {
                super.insertString(offset, str, attr);
            }
        }
    }

    public GroupFormScreen() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // 제목
        JLabel titleLabel = new JLabel("소모임 생성");
        titleLabel.setFont(FontLoader.loadCustomFont(24f).deriveFont(Font.BOLD));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 입력 필드들
        JTextField titleField = new JTextField(20);
        titleField.setDocument(new LengthRestrictedDocument(40));

        JTextArea contextArea = new JTextArea(5, 20);

        JTextField maxField = new JTextField(5);

        String[] regions = {
            "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
            "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구",
            "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구",
            "종로구", "중구", "중랑구"
        };
        JComboBox<String> regionBox = new JComboBox<>(regions);

        String[] categories = {
            "봉사활동", "사교/인맥", "문화/공연/축제", "인문학/책/글", "공예/만들기",
            "댄스/무용", "운동/스포츠", "외국/언어", "아웃도어/여행", "음악/악기",
            "차/바이크", "사진/영상", "스포츠관람", "게임/오락", "요리/제조",
            "반려동물", "자기계발", "테크/프로그래밍", "패션/뷰티", "수집/덕질"
        };
        JComboBox<String> categoryBox = new JComboBox<>(categories);

        String[] statuses = { "모집중", "진행중", "진행완료" };
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        statusBox.setSelectedItem("모집중");

        JTextField deadlineField = new JTextField(20);

        // 입력 폼 구성
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(labeledField("제목:", titleField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("내용:", new JScrollPane(contextArea)));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("최대 인원:", maxField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("지역:", regionBox));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("카테고리:", categoryBox));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("상태:", statusBox));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("모집 마감일 (yyyy-MM-dd HH:mm:ss):", deadlineField));

        add(formPanel, BorderLayout.CENTER);

        // 제출 버튼
        JButton submitBtn = new JButton("생성하기");
        submitBtn.setFont(FontLoader.loadCustomFont(16f));
        submitBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String context = contextArea.getText().trim();

            if (title.length() > 40) {
                JOptionPane.showMessageDialog(this, "제목은 최대 40자까지 입력할 수 있습니다.");
                return;
            }

            int max;
            try {
                max = Integer.parseInt(maxField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "최대 인원은 숫자여야 합니다.");
                return;
            }

            String region = (String) regionBox.getSelectedItem();
            String category = (String) categoryBox.getSelectedItem();
            String status = (String) statusBox.getSelectedItem();
            String deadlineText = deadlineField.getText().trim();

            Timestamp deadline = null;
            if (!deadlineText.isEmpty()) {
                try {
                    deadline = Timestamp.valueOf(deadlineText);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "마감일 형식이 잘못되었습니다.");
                    return;
                }
            }

            int hostId = Session.loggedInUserId; //기본: -1로 DB 삽입 안됨(오류)
            //login 안된 상태에서 DB insert 실패 handling 하는 로직
            //int hostId = Session.loggedInUserId != -1 ? Session.loggedInUserId : 1;

            GroupDAO dao = new GroupDAO();
            boolean success = dao.insertGroup(
                hostId, title, context, max, region, category, deadline, status
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "소모임 생성 성공!");
            } else {
                JOptionPane.showMessageDialog(this, "소모임 생성 실패...");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // 라벨 + 필드를 수직으로 묶어주는 헬퍼
    private JPanel labeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(FontLoader.loadCustomFont(14f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(4));
        panel.add(field);

        return panel;
    }
}

