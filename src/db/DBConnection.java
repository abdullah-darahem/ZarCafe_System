package db;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DBConnection {

    private static final String URL
            = "jdbc:mysql://localhost:3306/ZAR_CAFE?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";

    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASS);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error connecting to the database:\n" + e.getMessage());
            return null;
        }
    }

}
