package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcToolKit {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn != null) return conn;
        else {
            try {
                String url = "jdbc:mysql://localhost:3306/quan_ly_chi_tieu";
                String username = "root";
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                        url, username, "");
                return conn;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return conn;
    }
}
