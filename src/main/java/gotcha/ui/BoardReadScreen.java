package gotcha.ui;
import gotcha.service.BoardReadService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class BoardReadScreen extends JFrame {
	private int userId;
	private JPanel contentPanel;
	private int currentPage = 0;
	private final int POSTS_PER_PAGE = 5;
	private List<Map<String, Object>> postList;
	
	public BoardReadScreen(int userId) {
		this.userId = userId;
		setTitle("게시판");
		setSize(400, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		BoardReadService service = new BoardReadService();
		postList = service.getBoardList(userId);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		
		JButton prevButton = new JButton("< 이전");
		JButton nextButton = new JButton("다음 >");
		
		prevButton.addActionListener(e -> {
			if (currentPage>0) {
				currentPage--;
				updatePosts();
			}
		});
		
		nextButton.addActionListener(e -> {
			currentPage++;
			updatePosts();
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(prevButton);
		buttonPanel.add(nextButton);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.CENTER);
		
		updatePosts();
		setVisible(true);
	}
	
	private void updatePosts() {
		contentPanel.removeAll();
		int start = currentPage*POSTS_PER_PAGE;
		int end = Math.min(start+POSTS_PER_PAGE, postList.size());
		
		for (int i = start; i < end; i++) {
			Map<String, Object> post = postList.get(i);
			String title = (String) post.get("title");
			String context = (String) post.get("context");
			
			JTextArea postArea = new JTextArea(title + "\n\n" + context);
			postArea.setLineWrap(true);
			postArea.setWrapStyleWord(true);
			postArea.setEditable(false);
			postArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));;
			
			contentPanel.add(postArea);
			contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}
		
	}
}