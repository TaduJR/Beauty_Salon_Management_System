import java.sql.*;

public class ConnectionManager {
    public final static Connection connection = getConnection();
    private static final String urlString = "jdbc:mysql://localhost:3306/beauty_salon";
    private static final String username = "root";
    private static final String password = "";
    private static Connection getConnection() {
        Connection con = null;
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(urlString, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }
}