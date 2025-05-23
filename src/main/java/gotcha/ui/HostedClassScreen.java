package gotcha.ui;

import gotcha.service.HostedClassService;
import gotcha.dao.HostedClassDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;



public class HostedClassScreen extends JPanel {
    private HostedClassService service = new HostedClassService();

    public HostedClassScreen(int hostId) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("내가 만든 클래스 관리"));

        List<HostedClassDAO.HostedClass> classList = service.getMyHostedClasses(hostId);

        add(makeSectionTable("진행중인 클래스", classList, "진행중"));
        add(makeSectionTable("모집중인 클래스", classList, "모집중"));
        add(makeSectionTable("진행완료 클래스", classList, "진행완료"));
    }

    private JPanel makeSectionTable(String title, List<HostedClassDAO.HostedClass> all, String status) {
        List<HostedClassDAO.HostedClass> filtered = all.stream()
                .filter(c -> status.equals(c.getStatus()))
                .collect(Collectors.toList());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        String[] cols = {"클래스명", "카테고리", "지역", "요일", "참가 멤버 수"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        for (HostedClassDAO.HostedClass c : filtered) {
            model.addRow(new Object[]{
                c.getTitle(), c.getCategory(), c.getRegion(), c.getDays(), c.getUserCount()
            });
        }
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
