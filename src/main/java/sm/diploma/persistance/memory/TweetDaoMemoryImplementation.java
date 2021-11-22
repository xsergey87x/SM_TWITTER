package sm.diploma.persistance.memory;

import sm.diploma.model.Tweet;
import sm.diploma.persistance.TweetDao;
import sm.diploma.persistance.TweetDoesNotExistException;
import sm.diploma.storage.Storage;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TweetDaoMemoryImplementation implements TweetDao {
    @Override
    public Long saveTweet(Tweet tweet) {

        long newTweeId = ++Storage.tweetIdSequence;
        tweet.setTweetId(newTweeId);
        Storage.getInstance().getTweetStorage().put(tweet.getUserId(), createTweetState(tweet));
        return newTweeId;
    }

    @Override
    public Optional<Tweet> findTweetById(long tweetId) {
        final Tweet persistedTweet = Storage.getInstance().getTweetStorage().get(tweetId);
        if (persistedTweet != null) {
            Tweet resultTweet = createTweetState(persistedTweet);
            return Optional.of(resultTweet);
        }
        return Optional.empty();
    }

    @Override
    public Set<Tweet> getAll() {
        return null;
    }

    @Override
    public void updateTweet(Tweet tweet) {
        Optional<Tweet> tweetOptional = findTweetById(tweet.getTweetId());
        if (tweetOptional.isPresent()) {
            Storage.getInstance().getTweetStorage().put(tweet.getTweetId(), createTweetState(tweet));
        } else {
            throw new TweetDoesNotExistException(" Updated tweet does not exist " + tweet);
        }
    }

    @Override
    public boolean deleteTweetById(long tweetId) {
        Map<Long, Tweet> tweetStorage = Storage.getInstance().getTweetStorage();
        if (tweetStorage.containsKey(tweetId)) {
            tweetStorage.remove(tweetId);
            return true;
        } else {
            return false;
        }
    }

    private Tweet createTweetState(Tweet tweetOriginal) {
        return new Tweet(tweetOriginal);
    }
}
