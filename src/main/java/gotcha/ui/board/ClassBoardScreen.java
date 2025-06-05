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

    private final int POSTS_PER_PAGE = 5;
    private int currentPage = 0;
    private List<Map<String, Object>> postList;

    public ClassBoardScreen(int classId) {
        this.classId = classId;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        FontLoader.applyGlobalFont(14f);

        String classTitle = boardService.getClassTitleById(classId);

        // 상단
        JButton backButton = new JButton("← 뒤로가기");
        backButton.setFont(FontLoader.loadCustomFont(14f));
        backButton.addActionListener(e -> Main.setScreen(new BoardScreen()));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("📋 " + classTitle + " 게시판");
        titleLabel.setFont(FontLoader.loadCustomFont(22f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

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

        // 테이블
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

        // 게시글 더블클릭
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

        // 하단 페이지네이션 버튼
        JButton prevButton = new JButton("< 이전");
        JButton nextButton = new JButton("다음 >");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updatePosts();
            }
        });

        nextButton.addActionListener(e -> {
            if ((currentPage + 1) * POSTS_PER_PAGE < postList.size()) {
                currentPage++;
                updatePosts();
            }
        });

        loadAllPosts();  // 전체 불러오고 첫 페이지 로드
    }

    private void loadAllPosts() {
        postList = boardService.getPostsByClassId(classId);
        updatePosts();
    }

    private void updatePosts() {
        tableModel.setRowCount(0);
        int start = currentPage * POSTS_PER_PAGE;
        int end = Math.min(start + POSTS_PER_PAGE, postList.size());

        for (int i = start; i < end; i++) {
            Map<String, Object> post = postList.get(i);
            tableModel.addRow(new Object[]{
                    post.get("board_id"),
                    post.get("title"),
                    post.get("writer_nickname"),
                    post.get("created_at")
            });
        }

        tableModel.fireTableDataChanged();
    }

    private void openDetailWindow(int boardId) {
        Main.setScreen(new BoardDetailScreen(boardId));
    }
}
