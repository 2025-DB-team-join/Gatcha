package gotcha.ui.board;

import gotcha.Main;
import gotcha.common.FontLoader;
import gotcha.common.Session;
import gotcha.service.BoardService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ClassBoardScreen extends JPanel {

    private final int classId;
    private final int userId = Session.loggedInUserId;
    private final BoardService boardService = new BoardService();
    private final JTable postTable;
    private final DefaultTableModel tableModel;

    public ClassBoardScreen(int classId) {
        this.classId = classId;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        FontLoader.applyGlobalFont(14f);

        // 소모임 이름 조회
        String classTitle = boardService.getClassTitleById(classId);

        // 상단: 제목 + 작성 버튼
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("📋 " + classTitle + " 게시판");
        titleLabel.setFont(FontLoader.loadCustomFont(22f));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton writeButton = new JButton("게시글 작성");
        writeButton.setFont(FontLoader.loadCustomFont(14f));

        int hostId = boardService.getHostId(classId);
        if (hostId != userId) {
            writeButton.setEnabled(false);
            writeButton.setToolTipText("주최자만 게시글을 작성할 수 있습니다.");
        }

        writeButton.addActionListener(e -> {
            JFrame frame = new JFrame("게시글 작성");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new BoardWriteScreen(userId, classId));
            frame.setVisible(true);
        });

        topPanel.add(writeButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 테이블 (편집 불가)
        tableModel = new DefaultTableModel(new String[]{"ID", "제목", "작성자", "작성일"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        postTable = new JTable(tableModel);
        postTable.getColumnModel().getColumn(0).setMinWidth(0);
        postTable.getColumnModel().getColumn(0).setMaxWidth(0);
        postTable.getColumnModel().getColumn(0).setWidth(0);
        JScrollPane scrollPane = new JScrollPane(postTable);
        add(scrollPane, BorderLayout.CENTER);

        // 더블 클릭 시 상세 보기
        postTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = postTable.getSelectedRow();
                    if (row != -1) {
                        Object idObj = tableModel.getValueAt(row, 0);
                        if (idObj == null) {
                            JOptionPane.showMessageDialog(null, "게시글 정보를 불러올 수 없습니다.");
                            return;
                        }
                        int boardId = Integer.parseInt(idObj.toString());
                        openDetailWindow(boardId);
                    }
                }
            }
        });

        loadPosts();
    }

    private void loadPosts() {
        tableModel.setRowCount(0);
        List<Map<String, Object>> posts = boardService.getPostsByClassId(classId);
        for (Map<String, Object> post : posts) {
            tableModel.addRow(new Object[]{
                    post.get("board_id"),
                    post.get("title"),
                    post.get("writer_nickname"),
                    post.get("created_at")
            });
        }
    }

    private void openDetailWindow(int boardId) {
        JFrame detailFrame = new JFrame("게시글 상세 보기");
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setSize(600, 500);
        detailFrame.setLocationRelativeTo(null);
        detailFrame.setContentPane(new BoardDetailScreen(boardId));
        detailFrame.setVisible(true);
    }
}
