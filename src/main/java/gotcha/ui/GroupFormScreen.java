package gotcha.ui;

import gotcha.Main;
import gotcha.common.DateLabelFormatter;
import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.dao.GroupDAO;
import gotcha.dao.HostedClassDAO;
import gotcha.dao.HostedClassDAO.HostedClass;
import gotcha.dao.ScheduleDAO;
import gotcha.ui.home.HomeScreen;
import gotcha.ui.manage.ManageGroupScreen;
import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class GroupFormScreen extends JPanel {
    private JTextField titleField;
    private JTextArea contextArea;
    private JTextField maxField;
    private JComboBox<String> regionBox;
    private JComboBox<String> categoryBox;
    private JComboBox<String> statusBox;
    private UtilDateModel dateModel;
    private JSpinner timeSpinner;
    private Map<String, JCheckBox> dayCheckBoxes;
    private JSpinner startTimeSpinner;
    private JTextField durationField;

    private boolean isEditMode;
    private int classId;
    private int userId;

    public GroupFormScreen() {
        this(-1, Session.loggedInUserId, false);
    }

    public GroupFormScreen(int classId, int userId) {
        this(classId, userId, true);
    }

    private GroupFormScreen(int classId, int userId, boolean isEditMode) {
        this.isEditMode = isEditMode;
        this.classId = classId;
        this.userId = userId;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel titleLabel = new JLabel(isEditMode ? "소모임 수정" : "소모임 생성");
        titleLabel.setFont(FontLoader.loadCustomFont(24f).deriveFont(Font.BOLD));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        initFields();
        JPanel formPanel = createFormPanel(titleLabel);
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        if (isEditMode) {
            loadExistingData();
        }
    }

    private void initFields() {
        titleField = new JTextField(20);
        titleField.setDocument(new LengthRestrictedDocument(40));
        contextArea = new JTextArea(5, 20);
        maxField = new JTextField(5);

        regionBox = new JComboBox<>(new String[]{"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
                "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구",
                "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구",
                "종로구", "중구", "중랑구"});

        categoryBox = new JComboBox<>(new String[]{"봉사활동", "사교/인맥", "문화/공연/축제", "인문학/책/글", "공예/만들기",
                "댄스/무용", "운동/스포츠", "외국/언어", "아웃도어/여행", "음악/악기",
                "차/바이크", "사진/영상", "스포츠관람", "게임/오락", "요리/제조",
                "반려동물", "자기계발", "테크/프로그래밍", "패션/뷰티", "수집/덕질"});

        statusBox = new JComboBox<>(new String[]{"모집중", "진행중", "진행완료"});
        statusBox.setSelectedItem("모집중");

        dateModel = new UtilDateModel();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        dateModel.setDate(tomorrow.get(Calendar.YEAR), tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DAY_OF_MONTH));
        dateModel.setSelected(true);

        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm:ss"));

        dayCheckBoxes = new LinkedHashMap<>();
        String[] days = {"Mon", "Tues", "Wed", "Thur", "Fri", "Sat", "Sun"};
        for (String day : days) {
            JCheckBox checkBox = new JCheckBox(day);
            dayCheckBoxes.put(day, checkBox);
        }

        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        startTimeSpinner.setEditor(new JSpinner.DateEditor(startTimeSpinner, "HH:mm"));

        durationField = new JTextField(5);
    }

    private JPanel createFormPanel(JLabel titleLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(labeledField("제목:", titleField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("내용:", new JScrollPane(contextArea)));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("최대 인원:", maxField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("지역:", regionBox));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("카테고리:", categoryBox));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("상태:", statusBox));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("모집 마감일 (날짜):", new JDatePickerImpl(new JDatePanelImpl(dateModel, new Properties()), new DateLabelFormatter())));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("모집 마감일 (시간):", timeSpinner));
        panel.add(Box.createVerticalStrut(10));
        JPanel dayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dayCheckBoxes.values().forEach(dayPanel::add);
        panel.add(labeledField("요일 선택:", dayPanel));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("시작 시간:", startTimeSpinner));
        panel.add(Box.createVerticalStrut(10));
        panel.add(labeledField("진행 시간 (분):", durationField));
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    private JPanel createButtonPanel() {
        JButton submitBtn = new JButton(isEditMode ? "수정하기" : "생성하기");
        submitBtn.setFont(FontLoader.loadCustomFont(16f));
        submitBtn.addActionListener(e -> handleSubmit());

        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> Main.setScreen(isEditMode ? new ManageGroupScreen(userId) : new HomeScreen()));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(submitBtn);
        panel.add(backBtn);
        return panel;
    }

    private void handleSubmit() {
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

        Date selectedDate = (Date) dateModel.getValue();
        Date selectedTime = (Date) timeSpinner.getValue();
        if (!isValidDeadline(selectedDate)) return;

        Timestamp deadline = combineDateTime(selectedDate, selectedTime);

        GroupDAO groupDAO = new GroupDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        boolean success = false;
        int newClassId = -1;

        // 시간 및 duration 처리
        Date startTimeDate = (Date) startTimeSpinner.getValue();
        Time startTime = new Time(startTimeDate.getTime());

        int duration;
        try {
            duration = Integer.parseInt(durationField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "진행 시간은 숫자여야 합니다.");
            return;
        }

        if (isEditMode) {
            // 1. 소모임 정보 수정
            success = groupDAO.updateGroup(classId, title, context, max, region, category, deadline, status);

            if (success) {
                // 2. 기존 스케줄 삭제
                scheduleDAO.deleteSchedulesByClassId(classId);

                // 3. 새 스케줄 삽입
                for (Map.Entry<String, JCheckBox> entry : dayCheckBoxes.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        boolean inserted = scheduleDAO.insertSchedule(classId, entry.getKey(), startTime, duration);
                        if (!inserted) {
                            JOptionPane.showMessageDialog(this, "일정 저장 실패: " + entry.getKey());
                            return;
                        }
                    }
                }
            }

        } else {
            // 1. 소모임 새로 생성 → class_id 반환
            newClassId = groupDAO.insertGroup(userId, title, context, max, region, category, deadline, status);
            success = newClassId != -1;

            if (success) {
                // 2. 스케줄 삽입
                for (Map.Entry<String, JCheckBox> entry : dayCheckBoxes.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        boolean inserted = scheduleDAO.insertSchedule(newClassId, entry.getKey(), startTime, duration);
                        if (!inserted) {
                            JOptionPane.showMessageDialog(this, "일정 저장 실패: " + entry.getKey());
                            return;
                        }
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, success
                ? (isEditMode ? "소모임 수정 성공!" : "소모임 생성 성공!")
                : (isEditMode ? "소모임 수정 실패!" : "소모임 생성 실패!"));

        Main.setScreen(isEditMode ? new ManageGroupScreen(userId) : new HomeScreen());
    }


    private boolean isValidDeadline(Date selectedDate) {
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
            return false;
        }
        return true;
    }

    private Timestamp combineDateTime(Date date, Date time) {
        Calendar finalCal = Calendar.getInstance();
        finalCal.setTime(date);

        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);

        finalCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        finalCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        finalCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));

        return new Timestamp(finalCal.getTimeInMillis());
    }

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

    static class LengthRestrictedDocument extends PlainDocument {
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

    private void loadExistingData() {
        HostedClassDAO dao = new HostedClassDAO();
        HostedClass existing = dao.getHostedClassById(classId);

        if (existing == null) {
            JOptionPane.showMessageDialog(this, "소모임 정보를 불러올 수 없습니다.");
            Main.setScreen(new ManageGroupScreen(userId));
            return;
        }

        titleField.setText(existing.getTitle());
        contextArea.setText(existing.getContext());
        maxField.setText(String.valueOf(existing.getMax()));
        regionBox.setSelectedItem(existing.getRegion());
        categoryBox.setSelectedItem(existing.getCategory());
        statusBox.setSelectedItem(existing.getStatus());

        Timestamp deadline = existing.getDeadline();
        if (deadline != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(deadline);

            dateModel.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dateModel.setSelected(true);
            timeSpinner.setValue(deadline);
        }

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        List<ScheduleDAO.ScheduleInfo> schedules = scheduleDAO.getSchedulesByClassId(classId);

        if (!schedules.isEmpty()) {
            // 요일 체크
            for (ScheduleDAO.ScheduleInfo s : schedules) {
                JCheckBox cb = dayCheckBoxes.get(s.dayOfWeek);
                if (cb != null) cb.setSelected(true);
            }

            // 대표 일정 정보 세팅 (첫 항목 기준)
            ScheduleDAO.ScheduleInfo first = schedules.get(0);
            startTimeSpinner.setValue(new java.util.Date(first.startTime.getTime()));
            durationField.setText(String.valueOf(first.duration));
        }

    }
}
