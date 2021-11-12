package sm.diploma.persistance;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TweetDao {

    Long saveTweet(Tweet tweet) throws SQLException;

    Optional<Tweet> findTweetById(long tweetId) throws SQLException;

    public Set<Tweet> getAll() throws SQLException;

    void updateTweet(Tweet tweet) throws SQLException;

    boolean deleteTweetById(long userId) throws SQLException;

}
