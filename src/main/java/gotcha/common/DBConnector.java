package gotcha.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/community?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ DB 연결 성공");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ 드라이버 로딩 실패: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ DB 연결 실패: " + e.getMessage());
        }
        return null;
    }
}
