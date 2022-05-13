import java.sql.*;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-20 14:05
 **/
public class JdbcTest {
    public static void main(String[] args) throws SQLException {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {

            try (ResultSet resultSet = s.executeQuery("SELECT  * from sys_user")) {
                while ( resultSet.next()) {
                    System.out.println(resultSet.getString("user_name"));
                }
            }
        }


    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/fire_control?serverTimezone=GMT%2B8";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);

    }
}
