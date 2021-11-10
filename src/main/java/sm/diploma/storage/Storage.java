package sm.diploma.storage;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    public static long userIdSequence = 0;
    public static long tweetIdSequence = 0;
    private final Map<Long, User> userStorage = new HashMap<>();
    private final Map<Long, Tweet> tweetStorage = new HashMap<>();

    private Storage() {
    }

    public static Storage getInstance() {
        return Holder.instance;
    }

    public Map<Long, User> getUserStorage() {
        return userStorage;
    }

    public Map<Long, Tweet> getTweetStorage() {
        return tweetStorage;
    }

    public static class Holder {
        private static final Storage instance = new Storage();
    }
}
