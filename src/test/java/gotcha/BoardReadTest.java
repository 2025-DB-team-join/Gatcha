package gotcha;
import javax.swing.*;
import gotcha.ui.BoardReadScreen;

public class BoardReadTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int userId = 12;
            new BoardReadScreen(userId);
        });
    }
}
