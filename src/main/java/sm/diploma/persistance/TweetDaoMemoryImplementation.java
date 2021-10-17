package sm.diploma.persistance;

import sm.diploma.model.Tweet;
import sm.diploma.storage.Storage;

import java.util.Map;
import java.util.Optional;

public class TweetDaoMemoryImplementation implements TweetDao {
    @Override
    public Long saveTweet(Tweet tweet) {

        long newTweeId = ++Storage.tweetIdSequence;
        tweet.setTweetId(newTweeId);
        Storage.getTweeStorage().put(tweet.getUserId(), createTweetState(tweet));
        return newTweeId;
    }

    @Override
    public Optional<Tweet> findTweetById(long tweetId) {
        final Tweet persistedTweet = Storage.getTweeStorage().get(tweetId);
        if (persistedTweet != null) {
            Tweet resultTweet = createTweetState(persistedTweet);
            return Optional.of(resultTweet);
        }
        return Optional.empty();
    }

    @Override
    public void updateTweet(Tweet tweet) {
        Optional<Tweet> tweetOptional = findTweetById(tweet.getTweetId());
        if (tweetOptional.isPresent()) {
            Storage.getTweeStorage().put(tweet.getTweetId(), createTweetState(tweet));
        } else {
            throw new TweetDoesNotExistException(" Updated tweet does not exist " + tweet);
        }
    }

    @Override
    public boolean deleteTweetById(long tweetId) {
        Map<Long, Tweet> tweetStorage = Storage.getTweeStorage();
        if (tweetStorage.containsKey(tweetId)) {
            tweetStorage.remove(tweetId);
            return true;
        } else {
            throw new TweetDoesNotExistException(" User with ID " + tweetId + "does not exist ");
        }
    }

    private Tweet createTweetState(Tweet tweetOriginal) {
        return new Tweet(tweetOriginal);
    }
}
