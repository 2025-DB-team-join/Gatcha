package gotcha.ui.board;

import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.service.BoardService;
import gotcha.service.CommentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        JLabel titleLabel = new JLabel("제목: " + post.get("title"));
        titleLabel.setFont(FontLoader.loadCustomFont(18f));
        add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(new JLabel("작성자: " + post.get("writer")));
        infoPanel.add(new JLabel("작성일: " + post.get("created_at")));
        add(infoPanel, BorderLayout.BEFORE_FIRST_LINE);

        JTextArea contextArea = new JTextArea(post.get("context").toString());
        contextArea.setLineWrap(true);
        contextArea.setWrapStyleWord(true);
        contextArea.setEditable(false);
        contextArea.setFont(FontLoader.loadCustomFont(14f));
        JScrollPane contentScroll = new JScrollPane(contextArea);
        contentScroll.setPreferredSize(new Dimension(500, 250));
        add(contentScroll, BorderLayout.CENTER);

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

        // 부모 댓글 먼저 필터링
        List<Map<String, Object>> parentComments = comments.stream()
                .filter(c -> c.get("parent_id") == null)
                .collect(Collectors.toList());

        for (Map<String, Object> parent : parentComments) {
            int parentId = (int) parent.get("comment_id");
            commentListPanel.add(buildCommentPanel(parent, false));

            // 자식 댓글(대댓글) 필터링 및 추가
            List<Map<String, Object>> replies = comments.stream()
                    .filter(c -> c.get("parent_id") != null && parentId == ((Integer) c.get("parent_id")).intValue())
                    .collect(Collectors.toList());
            for (Map<String, Object> reply : replies) {
                JPanel replyPanel = buildCommentPanel(reply, true);
                replyPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
                commentListPanel.add(replyPanel);
            }
        }

        commentListPanel.revalidate();
        commentListPanel.repaint();
    }

    private JPanel buildCommentPanel(Map<String, Object> comment, boolean isReply) {
        int commentId = (int) comment.get("comment_id");
        String writer = (String) comment.get("writer_nickname");
        String createdAt = comment.get("created_at").toString();
        String content = (String) comment.get("content");

        JPanel commentPanel = new JPanel(new BorderLayout());
        JTextArea commentArea = new JTextArea();
        String prefix = isReply ? "\u2514 " : ""; // └
        commentArea.setText(prefix + writer + " | " + createdAt + "\n" + prefix + content);
        commentArea.setEditable(false);
        commentArea.setBackground(new Color(245, 245, 245));
        commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel replyContainer = new JPanel(new BorderLayout());
        JButton replyBtn = new JButton("답글");
        JPanel replyInputPanel = new JPanel(new BorderLayout());
        JTextArea replyInput = new JTextArea(2, 30);
        replyInput.setLineWrap(true);
        replyInput.setWrapStyleWord(true);
        JButton replySubmit = new JButton("등록");
        replyInputPanel.add(replyInput, BorderLayout.CENTER);
        replyInputPanel.add(replySubmit, BorderLayout.EAST);
        replyInputPanel.setVisible(false);

        replyBtn.addActionListener(e -> replyInputPanel.setVisible(!replyInputPanel.isVisible()));
        replySubmit.addActionListener(e -> {
            String replyContent = replyInput.getText().trim();
            if (!replyContent.isEmpty()) {
                boolean success = commentService.addReply(boardId, userId, replyContent, commentId);
                if (success) {
                    replyInput.setText("");
                    replyInputPanel.setVisible(false);
                    refreshComments();
                } else {
                    JOptionPane.showMessageDialog(this, "대댓글 등록에 실패했습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "내용을 입력하세요.");
            }
        });

        replyContainer.add(replyBtn, BorderLayout.WEST);
        replyContainer.add(replyInputPanel, BorderLayout.SOUTH);

        commentPanel.add(commentArea, BorderLayout.CENTER);
        commentPanel.add(replyContainer, BorderLayout.SOUTH);

        return commentPanel;
    }
}
