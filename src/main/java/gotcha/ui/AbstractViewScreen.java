package gotcha.ui;

import gotcha.dao.HostedClassDAO;
import gotcha.dao.HostedClassDAO.HostedClass;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractViewScreen extends JPanel {
    protected HostedClass hostedClass;

    public AbstractViewScreen(int classId) {
        setLayout(new BorderLayout());

        // 데이터 조회
        HostedClassDAO dao = new HostedClassDAO();
        hostedClass = dao.getHostedClassById(classId);

        if (hostedClass == null) {
            JOptionPane.showMessageDialog(this, "소모임 정보를 불러올 수 없습니다.");
            return;
        }

        // 정보 패널
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        infoPanel.add(new JLabel("제목:"));
        infoPanel.add(new JLabel(hostedClass.getTitle()));

        infoPanel.add(new JLabel("카테고리:"));
        infoPanel.add(new JLabel(hostedClass.getCategory()));

        infoPanel.add(new JLabel("지역:"));
        infoPanel.add(new JLabel(hostedClass.getRegion()));

        infoPanel.add(new JLabel("요일:"));
        infoPanel.add(new JLabel(hostedClass.getDays()));

        infoPanel.add(new JLabel("정원:"));
        infoPanel.add(new JLabel(String.valueOf(hostedClass.getUserCount())));

        infoPanel.add(new JLabel("상태:"));
        infoPanel.add(new JLabel(hostedClass.getStatus()));

        add(infoPanel, BorderLayout.CENTER);

        // 버튼 (하위 클래스에서 정의)
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    protected abstract JPanel createButtonPanel(); // 각 화면에서 버튼은 다르게 정의
}
