package gotcha;

import gotcha.ui.mypage.PreviousClassesPanel;
import gotcha.ui.mypage.ScrapListPanel;

import javax.swing.*;
import java.awt.*;

public class ScrapListScreenTest {
    public static void main(String[] args) {

        int testUserId = 4;

        JFrame frame = new JFrame("스크랩 리스트 테스트");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        ScrapListPanel scrapListScreen = new ScrapListPanel(testUserId);
        PreviousClassesPanel previousClassesScreen = new PreviousClassesPanel(testUserId);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(scrapListScreen);
        mainPanel.add(previousClassesScreen);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}