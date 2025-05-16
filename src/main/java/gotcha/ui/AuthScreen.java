package gotcha.ui;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.service.UserService;

import javax.swing.*;
import java.awt.*;

public class AuthScreen extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final UserService userService = new UserService();

    public AuthScreen() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new LoginPanel(), "LOGIN");
        cardPanel.add(new RegisterPanel(), "REGISTER");

        add(cardPanel, BorderLayout.CENTER);
    }

    // 로그인 화면
    private class LoginPanel extends JPanel {
        public LoginPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(80, 200, 80, 200));

            JLabel title = new JLabel("로그인", SwingConstants.CENTER);
            title.setFont(FontLoader.loadCustomFont(24f));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(title);
            add(Box.createVerticalStrut(30));

            JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel idLabel = new JLabel("이메일:");
            JTextField usernameField = new JTextField(15);
            idPanel.add(idLabel);
            idPanel.add(usernameField);
            add(idPanel);

            JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel pwLabel = new JLabel("비밀번호:");
            JPasswordField passwordField = new JPasswordField(15);
            pwPanel.add(pwLabel);
            pwPanel.add(passwordField);
            add(pwPanel);

            JButton loginBtn = new JButton("로그인");
            loginBtn.setFont(FontLoader.loadCustomFont(16f));
            loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginBtn.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (userService.login(username, password)) {
                    Main.setScreen(new HomeScreen());
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다.");
                }
            });

            JButton goRegisterBtn = new JButton("회원가입");
            goRegisterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            goRegisterBtn.addActionListener(e -> cardLayout.show(cardPanel, "REGISTER"));

            add(Box.createVerticalStrut(20));
            add(loginBtn);
            add(Box.createVerticalStrut(10));
            add(goRegisterBtn);
        }
    }

    // 회원가입 화면
    private class RegisterPanel extends JPanel {
        public RegisterPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));

            JLabel title = new JLabel("회원가입", SwingConstants.CENTER);
            title.setFont(FontLoader.loadCustomFont(24f));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(title);
            add(Box.createVerticalStrut(30));

            // username
            JTextField usernameField = new JTextField(15);
            add(labeledField("사용자명:", usernameField));

            // nickname
            JTextField nicknameField = new JTextField(15);
            add(labeledField("닉네임:", nicknameField));

            // password
            JPasswordField passwordField = new JPasswordField(15);
            add(labeledField("비밀번호:", passwordField));

            // email
            JTextField emailField = new JTextField(15);
            add(labeledField("이메일:", emailField));

            // gender
            String[] genders = {"M", "F", "Other"};
            JComboBox<String> genderBox = new JComboBox<>(genders);
            add(labeledField("성별:", genderBox));

            // region
            String[] regions = {
                    "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
                    "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
                    "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
            };
            JComboBox<String> regionBox = new JComboBox<>(regions);
            add(labeledField("거주 지역:", regionBox));

            // birthyear (선택)
            JTextField birthyearField = new JTextField(15);
            add(labeledField("출생년도(선택):", birthyearField));

            // 가입 버튼
            JButton registerBtn = new JButton("가입하기");
            registerBtn.setFont(FontLoader.loadCustomFont(16f));
            registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerBtn.addActionListener(e -> {
                String username = usernameField.getText().trim();
                String nickname = nicknameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String email = emailField.getText().trim();
                String gender = (String) genderBox.getSelectedItem();
                String region = (String) regionBox.getSelectedItem();
                String birthText = birthyearField.getText().trim();

                Integer birthyear = null;
                if (!birthText.isEmpty()) {
                    try {
                        birthyear = Integer.parseInt(birthText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "출생년도는 숫자여야 합니다.");
                        return;
                    }
                }

                if (username.isEmpty() || nickname.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "모든 필수 항목을 입력해주세요.");
                    return;
                }

                boolean success = userService.register(
                        username, nickname, password, email, gender, region, birthyear
                );

                if (success) {
                    int userId = userService.getUserIdByEmail(email);

                    if (userId != -1) {
                        Main.setScreen(new TagSelectionScreen(userId)); // 태그 선택 화면으로 이동
                    } else {
                        JOptionPane.showMessageDialog(this, "user 정보를 찾을 수 없습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "회원가입 실패: 이미 사용 중인 이메일입니다.");
                }
            });

            JButton goLoginBtn = new JButton("로그인으로 돌아가기");
            goLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            goLoginBtn.addActionListener(e -> cardLayout.show(cardPanel, "LOGIN"));

            add(Box.createVerticalStrut(20));
            add(registerBtn);
            add(Box.createVerticalStrut(10));
            add(goLoginBtn);
        }

        // 입력 필드 + 라벨을 담은 JPanel 생성 유틸
        private JPanel labeledField(String labelText, JComponent field) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel(labelText);
            label.setPreferredSize(new Dimension(90, 25));
            panel.add(label);
            panel.add(field);
            return panel;
        }
    }

}
