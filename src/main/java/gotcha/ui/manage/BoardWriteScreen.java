<<<<<<<< HEAD:src/main/java/gotcha/ui/board/BoardWriteScreen.java
package gotcha.ui.board;
========
package gotcha.ui.manage;
>>>>>>>> origin:src/main/java/gotcha/ui/manage/BoardWriteScreen.java

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

		JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));

		JTextField titleField = new JTextField();
		JTextArea contextArea = new JTextArea(10, 20);
		contextArea.setLineWrap(true);
		contextArea.setWrapStyleWord(true);
		JScrollPane contextScrollPane = new JScrollPane(contextArea);

		formPanel.add(new JLabel("제목:"));
		formPanel.add(titleField);
		formPanel.add(new JLabel("내용:"));
		formPanel.add(contextScrollPane);

		add(formPanel, BorderLayout.CENTER);

		JButton submitButton = new JButton("확인");
		submitButton.setFont(FontLoader.loadCustomFont(20f));

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

		add(submitButton, BorderLayout.SOUTH);
	}
}
