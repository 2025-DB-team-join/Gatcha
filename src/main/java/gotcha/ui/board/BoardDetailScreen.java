package gotcha.ui.board;

import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.service.BoardService;
import gotcha.service.CommentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class BoardDetailScreen extends JPanel {
    private final CommentService commentService = new CommentService();
    private final int boardId;
    private final int userId = Session.loggedInUserId;
    private final JPanel commentListPanel = new JPanel();

    public BoardDetailScreen(int boardId) {
        this.boardId = boardId;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        FontLoader.applyGlobalFont(14f);

        BoardService boardService = new BoardService();
        Map<String, Object> post = boardService.getPostById(boardId);

        // 제목
        JLabel titleLabel = new JLabel("제목: " + post.get("title"));
        titleLabel.setFont(FontLoader.loadCustomFont(18f));
        add(titleLabel, BorderLayout.NORTH);

        // 작성자 + 작성일
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(new JLabel("작성자: " + post.get("writer")));
        infoPanel.add(new JLabel("작성일: " + post.get("created_at")));
        add(infoPanel, BorderLayout.BEFORE_FIRST_LINE);

        // 글 내용
        JTextArea contextArea = new JTextArea(post.get("context").toString());
        contextArea.setLineWrap(true);
        contextArea.setWrapStyleWord(true);
        contextArea.setEditable(false);
        contextArea.setFont(FontLoader.loadCustomFont(14f));
        JScrollPane contentScroll = new JScrollPane(contextArea);
        contentScroll.setPreferredSize(new Dimension(500, 250));
        add(contentScroll, BorderLayout.CENTER);

        // 댓글 목록 + 입력 패널
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        commentListPanel.setLayout(new BoxLayout(commentListPanel, BoxLayout.Y_AXIS));
        JScrollPane commentScroll = new JScrollPane(commentListPanel);
        commentScroll.setBorder(BorderFactory.createTitledBorder("💬 댓글"));
        bottomPanel.add(commentScroll, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JTextArea commentInput = new JTextArea(3, 50);
        commentInput.setLineWrap(true);
        commentInput.setWrapStyleWord(true);
        JScrollPane inputScroll = new JScrollPane(commentInput);

        JButton submitButton = new JButton("댓글 등록");
        submitButton.addActionListener(e -> {
            String content = commentInput.getText().trim();
            if (!content.isEmpty()) {
                boolean success = commentService.addComment(boardId, userId, content);
                if (success) {
                    commentInput.setText("");
                    refreshComments();
                } else {
                    JOptionPane.showMessageDialog(this, "댓글 등록에 실패했습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "댓글을 입력하세요.");
            }
        });

        inputPanel.add(inputScroll, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        bottomPanel.add(inputPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        refreshComments();
    }

    private void refreshComments() {
        commentListPanel.removeAll();
        List<Map<String, Object>> comments = commentService.getCommentsByBoardId(boardId);

        for (Map<String, Object> comment : comments) {
            String writer = (String) comment.get("writer_nickname");
            String createdAt = comment.get("created_at").toString();
            String content = (String) comment.get("content");

            JTextArea commentArea = new JTextArea(writer + " | " + createdAt + "\n" + content);
            commentArea.setEditable(false);
            commentArea.setBackground(new Color(245, 245, 245));
            commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            commentListPanel.add(commentArea);
        }

        commentListPanel.revalidate();
        commentListPanel.repaint();
    }
}
