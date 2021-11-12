package sm.diploma.persistance.jdbc;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;
import sm.diploma.persistance.TweetDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class TweetDaoJdbcImpl implements TweetDao {

    @Override
    public Long saveTweet(Tweet tweet) throws SQLException {

        String preparedQuery = "INSERT INTO tweets (userID, dataPoster, content) "
                + "VALUES ('" + tweet.getUserId() + "','" + tweet.getDatePoster() + "','" +
                tweet.getContent() + "');";

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        if (statement.execute(preparedQuery)) {
            ResultSet resultSet = statement.executeQuery(preparedQuery);
        }
        DbUtils.closeConnection(connection, statement);
        return getTweetIDFromUser(tweet.getUserId(), tweet.getDatePoster(), tweet.getContent());
    }

    public Long getTweetIDFromUser(Long userId, LocalDate dataPoster, String content) throws SQLException {

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        String preparedQuery = "SELECT * FROM tweets WHERE userId = " +
                userId + " and dataPoster = '" + dataPoster + "' and content = '"+content+"';";
        ResultSet resultSet = statement.executeQuery(preparedQuery);
        resultSet.next();
        Long result = resultSet.getLong("tweetId");
        DbUtils.closeConnection(connection, statement);
        return result;
    }

    @Override
    public Optional<Tweet> findTweetById(long tweetId) throws SQLException {

        String preparedQuery = "SELECT * FROM tweets WHERE tweetId = " + tweetId + ";";
        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(preparedQuery);
        resultSet.next();
        String datePoster = resultSet.getString("dataPoster");
        Long userId = Long.valueOf(resultSet.getInt("userId"));

        Tweet tweet = new Tweet(userId, tweetId, resultSet.getLong("referenceTweetId"),
                LocalDate.parse(datePoster), resultSet.getString("content"));

        DbUtils.closeConnection(connection, statement);
        return Optional.of(tweet);
    }

    @Override
    public Set<Tweet> getAll() throws SQLException {

        String preparedQuery = "SELECT * FROM tweets;";
        Set<Tweet> tweets = new HashSet<Tweet>();
        String datePoster;

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(preparedQuery);

        while (resultSet.next()) {
            datePoster = resultSet.getString("dataPoster");

            Tweet tweet = new Tweet(resultSet.getLong("userId"), resultSet.getLong("tweetId"),
                    resultSet.getLong("referenceTweet"),
                    LocalDate.parse(datePoster), resultSet.getString("content"));
            tweets.add(tweet);
        }

        DbUtils.closeConnection(connection, statement);
        return tweets;
    }

    @Override
    public void updateTweet(Tweet tweet) throws SQLException {

        String preparedQuery = "UPDATE  tweets SET dataPoster = '" + tweet.getDatePoster() +
                "',content = '" + tweet.getContent() +
                "'WHERE userId = '" + tweet.getUserId() + "';";

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        if (statement.execute(preparedQuery)) {
            ResultSet resultSet = statement.executeQuery(preparedQuery);
        }
        DbUtils.closeConnection(connection, statement);
    }

    @Override
    public boolean deleteTweetById(long userId) throws SQLException {

        String preparedQuery = "DELETE FROM tweets WHERE userId = " + userId + ";";

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        if (statement.execute(preparedQuery)) {
            ResultSet resultSet = statement.executeQuery(preparedQuery);
            DbUtils.closeConnection(connection, statement);
            return true;
        }
        DbUtils.closeConnection(connection, statement);

        return false;
    }
}
