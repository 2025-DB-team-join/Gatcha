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
		setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JLabel titleLabel = new JLabel("게시글 작성");
		titleLabel.setFont(FontLoader.loadCustomFont(24f));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		// 폼 구성 (제목 + 내용)
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		// 제목 라벨
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(new JLabel("제목:"), gbc);

		// 제목 입력칸
		JTextField titleField = new JTextField(30);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		formPanel.add(titleField, gbc);

		// 내용 라벨
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		formPanel.add(new JLabel("내용:"), gbc);

		// 내용 입력칸
		JTextArea contextArea = new JTextArea(10, 30);
		contextArea.setLineWrap(true);
		contextArea.setWrapStyleWord(true);
		JScrollPane contextScrollPane = new JScrollPane(contextArea);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		formPanel.add(contextScrollPane, gbc);

		add(formPanel, BorderLayout.CENTER);

		// 등록 버튼
		JButton submitButton = new JButton("등록");
		submitButton.setFont(FontLoader.loadCustomFont(18f));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(submitButton);
		add(buttonPanel, BorderLayout.SOUTH);

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
				if (window != null) {
					window.dispose(); // 현재 팝업 창 닫기
				}
			} else {
				JOptionPane.showMessageDialog(this, "게시글 등록에 실패하였습니다.");
			}
		});
	}
}
