package sm.diploma.persistance.jdbc;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;
import sm.diploma.persistance.TweetDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class TweetDaoJdbcImpl implements TweetDao {

    private static final String insertTweetQuery = "INSERT INTO tweets (userId, referenceTweetId, datePosted, content) VALUES (?, ?, ?, ?);";
    private static final String updateTweetQuery = "UPDATE  tweets SET datePosted = ?, content = ? WHERE tweetId = ?;";

    @Override
    public Long saveTweet(Tweet tweet) throws SQLException {

        String preparedQueryFind = "SELECT MAX(tweetId) FROM tweets;";
        Connection connection = DbUtils.getConnection();
        Statement findIdStatement = connection.createStatement();
        PreparedStatement statement = connection.prepareStatement(insertTweetQuery);
        statement.setLong(1, tweet.getUserId());
        statement.setString(2, String.valueOf(tweet.getReferenceTweetId()));
        statement.setString(3, String.valueOf(tweet.getDatePoster()));
        statement.setString(4, tweet.getContent());
        Integer resultSetAdd = statement.executeUpdate();

        ResultSet resultSetFind = findIdStatement.executeQuery(preparedQueryFind);
        resultSetFind.next();
        Long result = resultSetFind.getLong("MAX(tweetId)");
        DbUtils.closeConnection(connection, statement, findIdStatement);
        return result;
    }

    @Override
    public Optional<Tweet> findTweetById(long tweetId) throws SQLException {

        String preparedQuery = "SELECT * FROM tweets WHERE tweetId = " + tweetId + ";";
        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(preparedQuery);
        resultSet.next();
        Long referenceTweetId = resultSet.getLong("referenceTweetId");
        String datePoster = resultSet.getString("datePosted");
        Long userId = resultSet.getLong("userId");
        String content = resultSet.getString("content");

        DbUtils.closeConnection(connection, statement);
        return Optional.of(createTweet(userId, tweetId, referenceTweetId, datePoster, content));
    }

    Tweet createTweet(Long userId, Long tweetId, Long referenceTweetId, String datePosted, String content) {
        return new Tweet(userId, tweetId, referenceTweetId, LocalDate.parse(datePosted), content);
    }

    @Override
    public Set<Tweet> getAll() throws SQLException {

        String preparedQuery = "SELECT * FROM tweets;";
        Set<Tweet> tweets = new HashSet<Tweet>();
        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(preparedQuery);
        while (resultSet.next()) {
            Long referenceTweetId = resultSet.getLong("referenceTweetId");
            String datePoster = resultSet.getString("datePosted");
            Long userId = resultSet.getLong("userId");
            Long tweetId = resultSet.getLong("tweetId");
            String content = resultSet.getString("content");
            tweets.add(createTweet(userId, tweetId, referenceTweetId, datePoster, content));
        }
        DbUtils.closeConnection(connection, statement);
        return tweets;
    }

    @Override
    public void updateTweet(Tweet tweet) throws SQLException {

        Connection connection = DbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateTweetQuery);
        statement.setString(1, String.valueOf(LocalDate.now()));
        statement.setString(2, tweet.getContent());
        statement.setLong(3, tweet.getTweetId());
        Integer resultSetAdd = statement.executeUpdate();

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
