package gotcha.ui;

import gotcha.Main;
import gotcha.common.DateLabelFormatter;
import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.dao.GroupDAO;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.sql.Timestamp;
import org.jdatepicker.impl.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

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

        UtilDateModel dateModel = new UtilDateModel();

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1); // 내일 날짜를 기본으로 함.

        dateModel.setDate(tomorrow.get(Calendar.YEAR), tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DAY_OF_MONTH));
        dateModel.setSelected(true);

        Properties dateProps = new Properties();
        dateProps.put("text.today", "오늘");
        dateProps.put("text.month", "월");
        dateProps.put("text.year", "년");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProps);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        SpinnerDateModel timeModel = new SpinnerDateModel();
        JSpinner timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("모집 마감일 (날짜):", datePicker));

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(labeledField("모집 마감일 (시간):", timeSpinner));


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

            Date selectedDate = (Date) datePicker.getModel().getValue();
            Date selectedTime = (Date) timeSpinner.getValue();

            Calendar selectedCal = Calendar.getInstance();
            selectedCal.setTime(selectedDate);

            Calendar tomorrowCal = Calendar.getInstance();
            tomorrowCal.set(Calendar.HOUR_OF_DAY, 0);
            tomorrowCal.set(Calendar.MINUTE, 0);
            tomorrowCal.set(Calendar.SECOND, 0);
            tomorrowCal.set(Calendar.MILLISECOND, 0);
            tomorrowCal.add(Calendar.DAY_OF_MONTH, 1);

            if (selectedCal.before(tomorrowCal)) {
                JOptionPane.showMessageDialog(this, "마감일은 최소 '내일'이어야 합니다.");
                return;
            }

            Timestamp deadline = null;
            if (selectedDate != null && selectedTime != null) {
                Calendar finalCal = Calendar.getInstance();
                finalCal.setTime(selectedDate);

                Calendar timeCal = Calendar.getInstance();
                timeCal.setTime(selectedTime);

                finalCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
                finalCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
                finalCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));

                deadline = new Timestamp(finalCal.getTimeInMillis());
            }

            int hostId = Session.loggedInUserId;

            GroupDAO dao = new GroupDAO();
            boolean success = dao.insertGroup(
                hostId, title, context, max, region, category, deadline, status
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "소모임 생성 성공!");
                Main.setScreen(new HomeScreen());
            } else {
                JOptionPane.showMessageDialog(this, "소모임 생성 실패, 다시 시도해주세요!");
                Main.setScreen(new HomeScreen());
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

