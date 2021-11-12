package sm.diploma.persistance.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils {

    public static final String TWITTER_DB_URL = "jdbc:sqlite:D:\\JAVA_Hillel\\SM_Diploma\\src\\main\\java\\sm\\diploma\\storage\\myBase";

    public static String getUrl() {
        return TWITTER_DB_URL;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(TWITTER_DB_URL);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static Connection getConnection(String twitterDbUrl) {
        try {
            return DriverManager.getConnection(twitterDbUrl);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection, Statement statement) {
        try {
            statement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
