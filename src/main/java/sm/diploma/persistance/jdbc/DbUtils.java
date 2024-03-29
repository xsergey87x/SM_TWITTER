package sm.diploma.persistance.jdbc;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;
import sm.diploma.persistance.memory.TweetDaoMemoryImplementation;
import sm.diploma.persistance.memory.UserDaoImplementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DbUtils {

    public static final String TWITTER_DB_URL = "jdbc:sqlite:D:\\JAVA_Hillel\\SM_Diploma\\src\\main\\java\\sm\\diploma\\storage\\myBase";

    public static Connection getConnection() {
        return getConnection(TWITTER_DB_URL);
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

    public static void initDb(String daoType) {
        System.out.println(daoType);

        if (daoType.equals("jdbc")) {
            try (Connection connection = DbUtils.getConnection();
                 Statement createTableStatement = connection.createStatement();) {
                Integer createTableUsers = createTableStatement.executeUpdate("CREATE TABLE users\n" +
                        "(\n" +
                        "    userId         INTEGER PRIMARY KEY ASC\n" +
                        "    , login         VARCHAR(25) NOT NULL\n" +
                        "    , nickName        VARCHAR(25) NOT NULL\n" +
                        "    , dateRegistered    DATE\n" +
                        "    , dateOfBirth       DATE\n" +
                        "    , about       VARCHAR(25)\n" +
                        ");");

                Integer createTableTweets = createTableStatement.executeUpdate("CREATE TABLE tweets\n" +
                        "(\n" +
                        "    tweetId         INTEGER PRIMARY KEY ASC\n" +
                        "    , referenceTweetID BIGINT\n" +
                        "    , userId          INTEGER NOT NULL\n" +
                        "    , datePosted      DATE\n" +
                        "    , content         VARCHAR(255) NOT NULL\n" +
                        "    , FOREIGN KEY(userId) REFERENCES users(userId)\n" +
                        ");");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void populateDb(String daoType) {
        if (daoType.equals("jdbc")) {
            try (Connection connection = DbUtils.getConnection();
                 Statement createTableStatement = connection.createStatement();) {

                createTableStatement.addBatch("INSERT INTO users (nickName, login, dateRegistered, dateOfBirth,about)\n" +
                        "VALUES ('STUDENT', 'Jack', '2011-02-01', '1998-01-21','lol');");
                createTableStatement.addBatch("INSERT INTO users (nickName, login, dateRegistered, dateOfBirth,about)\n" +
                        "VALUES ('TEACHER', 'Bill', '2008-03-04', '1990-04-15','kek');");
                createTableStatement.addBatch("INSERT INTO users (nickName, login, dateRegistered, dateOfBirth,about)\n" +
                        "VALUES ('MENTOR', 'John', '2003-05-09', '1989-06-18','simple guy');");

                createTableStatement.addBatch("INSERT INTO tweets (userId, referenceTweetId, datePosted, content)\n" +
                        "VALUES (1, null , '2015-07-22', 'Nothing else matters');");
                createTableStatement.addBatch("INSERT INTO tweets (userId, referenceTweetId, datePosted, content)\n" +
                        "VALUES (2, null , '2013-05-11', 'How are your');");
                createTableStatement.addBatch("INSERT INTO tweets (userId, referenceTweetId, datePosted, content)\n" +
                        "VALUES (3, null , '2011-03-04', 'Hello anybody');");

                int[] rows = createTableStatement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            User firstUser = new User("LOL", "Jack", LocalDate.of(1987, 5, 5), "Teacher");
            User secondUser = new User("KEK", "John", LocalDate.of(1990, 3, 3), "Just students");
            User thirdUser = new User("USER", "David", LocalDate.of(1990, 3, 3), "Mentor");

            Tweet firstTweet = new Tweet(firstUser.getUserId(), null, LocalDate.of(2016, 5, 5), "Nothing else matters");
            Tweet secondTweet = new Tweet(secondUser.getUserId(), null, LocalDate.of(2015, 5, 5), "Simple text");
            Tweet thirdTweet = new Tweet(thirdUser.getUserId(), null, LocalDate.of(2013, 5, 5), "Knowledge is a power");

            UserDaoImplementation userDao = new UserDaoImplementation();
            TweetDaoMemoryImplementation tweetDao = new TweetDaoMemoryImplementation();

            userDao.save(firstUser);
            userDao.save(secondUser);
            userDao.save(thirdUser);

            tweetDao.saveTweet(firstTweet);
            tweetDao.saveTweet(secondTweet);
            tweetDao.saveTweet(thirdTweet);
        }
    }

}
