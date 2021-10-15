package sm.diploma.storage;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    private static final Map<Long, User> userStorage = new HashMap<>();
    private static final Map<Long, Tweet> tweeStorage = new HashMap<>();

    public static long userIdSequence = 0;
    public static long tweetIdSequence = 0;

    private Storage() {

    }

    public static Map<Long, User> getUserStorage() {
        return userStorage;
    }

    public static Map<Long, Tweet> getTweeStorage() {
        return tweeStorage;
    }
}
