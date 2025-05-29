package gotcha.ui;

import gotcha.Main;

import java.awt.FlowLayout;

import javax.swing.*;

public class ViewGroupScreen extends AbstractViewScreen {
    public ViewGroupScreen(int classId) {
        super(classId);
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editBtn = new JButton("수정");
        JButton deleteBtn = new JButton("삭제");
        JButton backBtn = new JButton("뒤로가기");

        // TODO: 각 버튼에 이벤트 붙이기
        backBtn.addActionListener(e -> gotcha.Main.setScreen(new ManageGroupScreen(hostedClass.getClassId())));

        panel.add(editBtn);
        panel.add(deleteBtn);
        panel.add(backBtn);
        return panel;
    }
}