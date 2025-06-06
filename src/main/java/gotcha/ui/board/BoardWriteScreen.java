package gotcha.ui.board;

import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.service.BoardService;

import javax.swing.*;
import java.awt.*;

public class BoardWriteScreen extends JPanel {
	private final BoardService boardService = new BoardService();

	public BoardWriteScreen(int userId, int classId) {
		setLayout(new BorderLayout(20, 20));
		setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // 더 넉넉한 여백

		// 제목 라벨
		JLabel titleLabel = new JLabel("게시글 작성");
		titleLabel.setFont(FontLoader.loadCustomFont(26f));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		// form 패널: 제목 + 내용
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;

		JLabel titleTextLabel = new JLabel("제목:");
		titleTextLabel.setFont(FontLoader.loadCustomFont(16f));
		formPanel.add(titleTextLabel, gbc);

		gbc.gridy++;
		JTextField titleField = new JTextField(30);
		titleField.setFont(FontLoader.loadCustomFont(15f));
		formPanel.add(titleField, gbc);

		gbc.gridy++;
		JLabel contextTextLabel = new JLabel("내용:");
		contextTextLabel.setFont(FontLoader.loadCustomFont(16f));
		formPanel.add(contextTextLabel, gbc);

		gbc.gridy++;
		JTextArea contextArea = new JTextArea(10, 30);
		contextArea.setFont(FontLoader.loadCustomFont(14f));
		contextArea.setLineWrap(true);
		contextArea.setWrapStyleWord(true);
		JScrollPane contextScrollPane = new JScrollPane(contextArea);
		formPanel.add(contextScrollPane, gbc);

		add(formPanel, BorderLayout.CENTER);

		// 버튼 패널
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		JButton submitButton = new JButton("등록");
		submitButton.setFont(FontLoader.loadCustomFont(16f));
		submitButton.setPreferredSize(new Dimension(100, 40));
		buttonPanel.add(submitButton);
		add(buttonPanel, BorderLayout.SOUTH);

		// 버튼 동작
		submitButton.addActionListener(e -> {
			String title = titleField.getText().trim();
			String context = contextArea.getText().trim();

			int hostId = boardService.getHostId(classId);
			if (hostId == -1) {
				JOptionPane.showMessageDialog(this, "존재하지 않는 소모임입니다.");
				return;
			}

			if (userId != hostId) {
				JOptionPane.showMessageDialog(this, "호스트만 게시글을 작성할 수 있습니다.");
				return;
			}

			if (title.isEmpty() || context.isEmpty()) {
				JOptionPane.showMessageDialog(this, "게시글 제목과 내용을 모두 입력해주세요.");
				return;
			}

			boolean success = boardService.createPostAndReturnStatus(userId, classId, title, context);
			if (success) {
				JOptionPane.showMessageDialog(this, "게시글을 등록하였습니다.");
				Window window = SwingUtilities.getWindowAncestor(this);
				if (window != null) window.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "게시글 등록에 실패하였습니다.");
			}
		});
	}
}
