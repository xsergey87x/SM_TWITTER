package sm.diploma.storage;

import sm.diploma.model.Tweet;
import sm.diploma.model.User;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    public static long userIdSequence = 0;
    public static long tweetIdSequence = 0;


    private Storage() {
    }

    public static Map<Long, User> getUserStorage() {
        return Holder.userStorage;
    }

    public static Map<Long, Tweet> getTweeStorage() {
        return Holder.tweetStorage;
    }

    public static class Holder {
        private static final Map<Long, User> userStorage = new HashMap<>();
        private static final Map<Long, Tweet> tweetStorage = new HashMap<>();
    }
}
