package sm.diploma.persistance;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;

import java.util.List;
import java.util.Optional;

public interface TweetDao {

    Long saveTweet(Tweet tweet);

    Optional<Tweet> findTweetById(long tweetId);

    void updateTweet(Tweet tweet);

    boolean deleteTweetById(long userId);

}
