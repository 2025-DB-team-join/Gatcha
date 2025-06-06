package gotcha.ui.manage;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.dao.GroupDAO;
import gotcha.dao.HostedClassDAO;
import gotcha.dao.HostedClassDAO.HostedClass;
import gotcha.dao.UserDAO;

import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyGroupDetailScreen extends JPanel {
    private HostedClass hostedClass;
    private int userId;

    public MyGroupDetailScreen(int classId, int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());

        // 데이터 조회
        HostedClassDAO dao = new HostedClassDAO();
        hostedClass = dao.getHostedClassById(classId);

        if (hostedClass == null) {
            JOptionPane.showMessageDialog(this, "소모임 정보를 불러올 수 없습니다.");
            return;
        }

        // 제목
        JLabel titleLabel = new JLabel(hostedClass.getTitle());
        titleLabel.setFont(FontLoader.loadCustomFont(24f).deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 카드 패널
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        cardPanel.setBackground(Color.WHITE);

        // 카드 만들기
        cardPanel.add(createInfoCard("카테고리", hostedClass.getCategory()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("소개", hostedClass.getContext()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("지역", hostedClass.getRegion()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("요일", hostedClass.getDays()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("인원 현황", hostedClass.getUserCount() + " / " + hostedClass.getMax()));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(createInfoCard("상태", hostedClass.getStatus()));

        add(cardPanel, BorderLayout.CENTER);
        add(createMemberAttendancePanel(classId), BorderLayout.EAST);

        // 버튼 패널
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createInfoCard(String label, String value) {
        Font font = FontLoader.loadCustomFont(14f);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(Color.WHITE);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(font.deriveFont(Font.BOLD));
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(font);

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueComponent, BorderLayout.EAST);

        return panel;
    }


    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editBtn = new JButton("수정");
        JButton deleteBtn = new JButton("삭제");
        JButton backBtn = new JButton("뒤로가기");

        editBtn.addActionListener(e -> {
            Main.setScreen(new gotcha.ui.GroupFormScreen(hostedClass.getClassId(), userId));
        });

        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = new GroupDAO().markGroupAsDeleted(hostedClass.getClassId());
                if (success) {
                    JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.");
                    Main.setScreen(new ManageGroupScreen(userId));
                } else {
                    JOptionPane.showMessageDialog(this, "삭제에 실패했습니다.");
                }
            }
        });

        backBtn.addActionListener(e -> Main.setScreen(new ManageGroupScreen(userId)));

        panel.add(editBtn);
        panel.add(deleteBtn);
        panel.add(backBtn);
        return panel;
    }


    private JTable memberTable;

    private JPanel createMemberAttendancePanel(int classId) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("참여자 출결 관리"));

        String[] cols = {"이름", "이메일", "가입일", "결석 횟수"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        memberTable = new JTable(model);
        memberTable.setRowHeight(32);

        // 참여자 데이터 불러오기
        List<Map<String, Object>> members = new UserDAO().getParticipantsByClassId(classId);
        for (Map<String, Object> user : members) {
            model.addRow(new Object[]{
                    user.get("nickname"),
                    user.get("email"),
                    user.get("joined_at"),
                    user.get("absent")
            });
        }

        // 버튼 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton increaseAbsentBtn = new JButton("결석 횟수 +1");
        JButton kickBtn = new JButton("강퇴");

        increaseAbsentBtn.addActionListener(e -> handleIncreaseAbsent(classId));
        kickBtn.addActionListener(e -> handleKickMember(classId));

        buttonPanel.add(increaseAbsentBtn);
        buttonPanel.add(kickBtn);

        panel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void handleIncreaseAbsent(int classId) {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "결석 처리할 멤버를 선택해주세요.");
            return;
        }

        String email = (String) memberTable.getValueAt(selectedRow, 1);
        boolean success = new UserDAO().increaseAbsentByEmail(classId, email);

        if (success) {
            JOptionPane.showMessageDialog(this, "결석 횟수를 1 증가시켰습니다.");
            ((DefaultTableModel) memberTable.getModel()).setValueAt(
                    (int) memberTable.getValueAt(selectedRow, 3) + 1,
                    selectedRow,
                    3
            );
        } else {
            JOptionPane.showMessageDialog(this, "결석 처리에 실패했습니다.");
        }
    }

    private void handleKickMember(int classId) {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "강퇴할 멤버를 선택해주세요.");
            return;
        }

        String email = (String) memberTable.getValueAt(selectedRow, 1);

        // 주최자 이메일과 비교해서 강퇴 차단
        if (email.equals(hostedClass.getHostEmail())) {
            JOptionPane.showMessageDialog(this, "주최자는 강퇴할 수 없습니다.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, email + "님을 정말 강퇴하시겠습니까?", "강퇴 확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = new HostedClassDAO().removeParticipant(classId, email);
            if (success) {
                ((DefaultTableModel) memberTable.getModel()).removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "강퇴가 완료되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "강퇴에 실패했습니다.");
            }
        }
    }

}


