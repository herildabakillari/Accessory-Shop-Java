package repository;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/moda?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Ndrysho në userin tënd
    private static final String PASSWORD = ""; // Ndrysho në passwordin tënd

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

