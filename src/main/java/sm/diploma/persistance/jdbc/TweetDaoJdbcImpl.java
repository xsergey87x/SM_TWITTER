package sm.diploma.persistance.jdbc;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;
import sm.diploma.persistance.TweetDao;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TweetDaoJdbcImpl implements TweetDao {

    private static final String INSERT_TWEET_QUERY = "INSERT INTO tweets (userId, referenceTweetId, datePosted, content) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_TWEET_QUERY = "UPDATE  tweets SET content = ? WHERE tweetId = ?;";
    private static final String SELECT_MAX_ID = "SELECT MAX(tweetId) FROM tweets;";
    private static final String FIND_TWEET_BY_ID = "SELECT * FROM tweets WHERE tweetId = ?;";
    private static final String DELETE_TWEET_QUERY = "DELETE FROM tweets WHERE userId = ?;";

    @Override
    public Long saveTweet(Tweet tweet) {

        Long maxID = 0L;
        try (Connection connection = DbUtils.getConnection();
             Statement findIdStatement = connection.createStatement();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_TWEET_QUERY);) {
            insertStatement.setLong(1, tweet.getUserId());
            insertStatement.setString(2, String.valueOf(tweet.getReferenceTweetId()));
            insertStatement.setString(3, String.valueOf(tweet.getDatePosted()));
            insertStatement.setString(4, tweet.getContent());
            Integer affectedRows = insertStatement.executeUpdate();
            ResultSet resultSetFind = findIdStatement.executeQuery(SELECT_MAX_ID);

            while (resultSetFind.next()) {
                maxID = resultSetFind.getLong("MAX(tweetId)");
            }
            return maxID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Tweet> findTweetById(long tweetId) throws SQLException {

        Connection connection = DbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_TWEET_BY_ID);
        statement.setLong(1, tweetId);
        ResultSet resultFindQuery = statement.executeQuery();

        if (resultFindQuery.next()) {
            Long referenceTweetId = resultFindQuery.getLong("referenceTweetId");
            LocalDate datePosted = LocalDate.parse(resultFindQuery.getString("datePosted"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Long userId = resultFindQuery.getLong("userId");
            String content = resultFindQuery.getString("content");
            DbUtils.closeConnection(connection, statement);
            return Optional.of(createTweet(userId, tweetId, referenceTweetId, datePosted, content));
        } else {
            DbUtils.closeConnection(connection, statement);
        }
        return Optional.empty();
    }

    Tweet createTweet(Long userId, Long tweetId, Long referenceTweetId, LocalDate datePosted, String content) {
        return new Tweet(userId, tweetId, referenceTweetId, datePosted, content);
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
            LocalDate datePoster = LocalDate.parse(resultSet.getString("datePosted"));
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
        PreparedStatement statement = connection.prepareStatement(UPDATE_TWEET_QUERY);
        statement.setString(1, tweet.getContent());
        statement.setLong(2, tweet.getTweetId());
        Integer updateTweetSet = statement.executeUpdate();

        DbUtils.closeConnection(connection, statement);
    }

    @Override
    public boolean deleteTweetById(long userId) throws SQLException {

        Connection connection = DbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_TWEET_QUERY);

        int deletedRows = statement.executeUpdate(DELETE_TWEET_QUERY);
        DbUtils.closeConnection(connection, statement);
        return deletedRows == 1;
    }
}
