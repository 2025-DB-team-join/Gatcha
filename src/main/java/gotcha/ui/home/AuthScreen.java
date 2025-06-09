package gotcha.ui.home;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.service.UserService;

import javax.swing.*;
import java.awt.*;

public class AuthScreen extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final UserService userService = new UserService();

    public AuthScreen() {
        FontLoader.applyGlobalFont(14f);
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
            setLayout(new BorderLayout());

            ImageIcon icon = new ImageIcon(getClass().getResource("/images/onboarding2.png"));
            Image scaledImage = icon.getImage().getScaledInstance(800, 200, Image.SCALE_SMOOTH);

            // 이미지 패널
            JPanel imagePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), this);
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(800, 200);
                }
            };
            add(imagePanel, BorderLayout.NORTH);

            // 로그인 폼 패널 (중앙)
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
            formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 60, 100));

            JLabel title = new JLabel("로그인");
            title.setFont(FontLoader.loadCustomFont(24f));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JTextField usernameField = new JTextField(15);
            usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            JPasswordField passwordField = new JPasswordField(15);
            passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JButton loginBtn = new JButton("로그인");
            JButton goRegisterBtn = new JButton("회원가입");

            loginBtn.setFont(FontLoader.loadCustomFont(14f));
            goRegisterBtn.setFont(FontLoader.loadCustomFont(14f));

            // setPreferredSize는 빼세요!

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            buttonPanel.add(loginBtn);
            buttonPanel.add(goRegisterBtn);
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


            // 로그인 버튼 기능
            loginBtn.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (userService.login(username, password)) {
                    int userId = userService.getUserIdByEmail(username);
                    if (userId != -1) {
                        Session.loggedInUserId = userId;
                        Main.setScreen(new HomeScreen());
                    } else {
                        JOptionPane.showMessageDialog(this, "로그인 오류: 사용자 정보를 찾을 수 없습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다.");
                }
            });

            goRegisterBtn.addActionListener(e -> {
                AuthScreen.this.cardLayout.show(AuthScreen.this.cardPanel, "REGISTER");
            });

            // 폼 패널 구성
            formPanel.add(title);
            formPanel.add(Box.createVerticalStrut(30));
            formPanel.add(labeledField("이메일", usernameField));
            formPanel.add(Box.createVerticalStrut(15));
            formPanel.add(labeledField("비밀번호", passwordField));
            formPanel.add(Box.createVerticalStrut(25));
            formPanel.add(buttonPanel);

            // 중앙에 폼 추가
            add(formPanel, BorderLayout.CENTER);
        }

        private JPanel labeledField(String labelText, JComponent field) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel label = new JLabel(labelText);
            label.setFont(FontLoader.loadCustomFont(13f));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            field.setAlignmentX(Component.LEFT_ALIGNMENT);

            panel.add(label);
            panel.add(Box.createVerticalStrut(4));
            panel.add(field);
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            return panel;
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
            add(labeledField("이름:", usernameField));

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

            Dimension buttonSize = new Dimension(150, 35);
            // 가입 버튼
            JButton registerBtn = new JButton("가입하기");
            registerBtn.setFont(FontLoader.loadCustomFont(16f));
            registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            registerBtn.setMaximumSize(buttonSize);
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
                        Session.loggedInUserId = userId;
                        Main.setScreen(new TagSelectionScreen(userId)); // 태그 선택 화면으로 이동
                    } else {
                        JOptionPane.showMessageDialog(this, "user 정보를 찾을 수 없습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "회원가입 실패: 이미 사용 중인 이메일입니다.");
                }
            });

            JButton goLoginBtn = new JButton("로그인으로 돌아가기");
            goLoginBtn.setMaximumSize(buttonSize);
            goLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            goLoginBtn.addActionListener(e -> AuthScreen.this.cardLayout.show(AuthScreen.this.cardPanel, "LOGIN"));

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
