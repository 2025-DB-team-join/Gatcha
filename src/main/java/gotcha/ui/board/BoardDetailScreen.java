package gotcha.ui.board;

import gotcha.Main;
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

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        JButton backButton = new JButton("← 뒤로가기");
        backButton.setFont(FontLoader.loadCustomFont(14f));
        backButton.addActionListener(e -> {
            int classId = (int) post.get("class_id");
            Main.setScreen(new ClassBoardScreen(classId));
        });
        headerPanel.add(backButton, BorderLayout.EAST);

        JLabel titleLabel = new JLabel("제목: " + post.get("title"), SwingConstants.CENTER);
        titleLabel.setFont(FontLoader.loadCustomFont(18f));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JTextArea infoArea = new JTextArea(
                "작성자: " + post.get("writer") + "\n작성일: " + post.get("created_at")
        );
        infoArea.setEditable(false);
        infoArea.setFont(FontLoader.loadCustomFont(14f));
        infoArea.setOpaque(false);
        infoArea.setFocusable(false);
        infoArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        headerPanel.add(infoArea, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

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
        commentScroll.setBorder(BorderFactory.createTitledBorder("댓글 목록"));
        commentScroll.setPreferredSize(new Dimension(500, 250));
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

    private JPanel createReplyInputPanel(int parentId) {
        JPanel replyInputPanel = new JPanel(new BorderLayout());
        JTextArea replyInput = new JTextArea(2, 30);
        JButton replySubmit = new JButton("등록");

        replyInput.setLineWrap(true);
        replyInput.setWrapStyleWord(true);

        replyInputPanel.add(new JScrollPane(replyInput), BorderLayout.CENTER);
        replyInputPanel.add(replySubmit, BorderLayout.EAST);
        replyInputPanel.setVisible(false);

        replySubmit.addActionListener(e -> {
            String content = replyInput.getText().trim();
            if (!content.isEmpty()) {
                boolean success = commentService.addReply(boardId, userId, content, parentId);
                if (success) {
                    replyInput.setText("");
                    replyInputPanel.setVisible(false);
                    refreshComments();
                } else {
                    JOptionPane.showMessageDialog(this, "대댓글 등록 실패");
                }
            } else {
                JOptionPane.showMessageDialog(this, "내용을 입력하세요.");
            }
        });

        return replyInputPanel;
    }

    private void refreshComments() {
        commentListPanel.removeAll();

        List<Map<String, Object>> comments = commentService.getCommentsByBoardId(boardId);

        comments.sort((a, b) -> {
            Object pa = a.get("parent_id");
            Object pb = b.get("parent_id");

            int pidA = pa == null ? 0 : ((Number) pa).intValue();
            int pidB = pb == null ? 0 : ((Number) pb).intValue();

            if (pidA != pidB) return Integer.compare(pidA, pidB);
            return Integer.compare((int) a.get("comment_id"), (int) b.get("comment_id"));
        });

        List<Map<String, Object>> parentComments = comments.stream()
                .filter(c -> c.get("parent_id") == null)
                .collect(Collectors.toList());

        for (Map<String, Object> parent : parentComments) {
            JPanel parentPanel = buildCommentWithReplies(parent, comments, 0);
            commentListPanel.add(parentPanel);
        }

        commentListPanel.revalidate();
        commentListPanel.repaint();
    }

    private JPanel buildCommentWithReplies(Map<String, Object> comment, List<Map<String, Object>> allComments, int depth) {
        int commentId = (int) comment.get("comment_id");

        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ㄴ + 댓글 + 답글 버튼 패널
        JPanel commentRow = new JPanel();
        commentRow.setLayout(new BoxLayout(commentRow, BoxLayout.X_AXIS));
        commentRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel arrowLabel = new JLabel(depth > 0 ? "↳ " : "  ");
        arrowLabel.setFont(FontLoader.loadCustomFont(16f));
        arrowLabel.setPreferredSize(new Dimension(20, 20));
        commentRow.add(arrowLabel);

        JPanel commentPanel = buildCommentPanel(comment);
        commentRow.add(commentPanel);

        JButton replyBtn = new JButton("답글");
        commentRow.add(Box.createRigidArea(new Dimension(10, 0)));
        commentRow.add(replyBtn);

        // 들여쓰기 wrapper
        JPanel indentWrapper = new JPanel();
        indentWrapper.setLayout(new BoxLayout(indentWrapper, BoxLayout.Y_AXIS));
        indentWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20 * depth, 10, 0));
        indentWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        indentWrapper.add(commentRow);

        block.add(indentWrapper);

        // 대댓글 입력창은 block 바깥에 depth+1로 추가되어야 함
        JPanel replyInputPanel = createReplyInputPanel(commentId);
        replyInputPanel.setVisible(false);

        JPanel replyInputWrapper = new JPanel();
        replyInputWrapper.setLayout(new BoxLayout(replyInputWrapper, BoxLayout.Y_AXIS));
        replyInputWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20 * (depth + 1), 10, 0));
        replyInputWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        replyInputWrapper.add(replyInputPanel);

        block.add(replyInputWrapper);

        replyBtn.addActionListener(e -> {
            replyInputPanel.setVisible(!replyInputPanel.isVisible());
            replyInputPanel.revalidate();
            replyInputPanel.repaint();
        });

        // 대댓글 추가
        List<Map<String, Object>> replies = allComments.stream()
                .filter(c -> {
                    Object parent = c.get("parent_id");
                    return parent != null && ((Number) parent).intValue() == commentId;
                })
                .collect(Collectors.toList());

        for (Map<String, Object> reply : replies) {
            JPanel replyBlock = buildCommentWithReplies(reply, allComments, depth + 1);
            block.add(replyBlock);
        }

        return block;
    }

    private JPanel buildCommentPanel(Map<String, Object> comment) {
        int commentId = (int) comment.get("comment_id");
        Number writerNum = (Number) comment.get("user_id");
        int writerId = (writerNum != null) ? writerNum.intValue() : -1;
        String writer = (String) comment.get("nickname");
        String createdAt = comment.get("created_at").toString();
        String content = (String) comment.get("content");

        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea commentArea = new JTextArea(writer + " | " + createdAt + "\n" + content);
        commentArea.setEditable(false);
        commentArea.setBackground(new Color(245, 245, 245));
        commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        commentPanel.add(commentArea);

        if (writerId == userId) {
            JButton editBtn = new JButton("수정");
            JButton deleteBtn = new JButton("삭제");

            editBtn.addActionListener(e -> {
                String newContent = JOptionPane.showInputDialog(this, "댓글 수정", content);
                if (newContent != null && !newContent.trim().isEmpty()) {
                    if (commentService.updateComment(commentId, newContent.trim())) {
                        refreshComments();
                    } else {
                        JOptionPane.showMessageDialog(this, "댓글 수정 실패");
                    }
                }
            });

            deleteBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (commentService.deleteComment(commentId)) {
                        refreshComments();
                    } else {
                        JOptionPane.showMessageDialog(this, "댓글 삭제 실패");
                    }
                }
            });

            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionPanel.add(editBtn);
            actionPanel.add(deleteBtn);
            commentPanel.add(actionPanel);
        }

        return commentPanel;
    }

}
