package gotcha;

import gotcha.ui.ScrapListScreen;

import javax.swing.JFrame;

public class ScrapListScreenTest {
    public static void main(String[] args) {

        int testUserId = 4;

        JFrame frame = new JFrame("스크랩 리스트 테스트");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ScrapListScreen scrapListScreen = new ScrapListScreen(testUserId);

        frame.add(scrapListScreen);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}