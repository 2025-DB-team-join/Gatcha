package gatcha.ui;

import gatcha.common.FontLoader;
import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
    public HomeScreen() {
        setLayout(new GridLayout(2, 2, 20, 20));

        JButton groupBtn = new JButton("소모임 탐색");
        JButton createBtn = new JButton("소모임 생성");
        JButton myPageBtn = new JButton("마이페이지");

        groupBtn.setFont(FontLoader.loadCustomFont(16f));
        createBtn.setFont(FontLoader.loadCustomFont(16f));
        myPageBtn.setFont(FontLoader.loadCustomFont(16f));

        add(groupBtn);
        add(createBtn);
        add(myPageBtn);
    }
}
