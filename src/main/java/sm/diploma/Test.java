package sm.diploma;

import sm.diploma.model.Tweet;
import sm.diploma.model.TweetFeed;
import sm.diploma.model.User;
import sm.diploma.model.UserFeed;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        User firstUser = new User("SM", LocalDate.of(1987, 5, 5), "Java students");
        User secondUser = new User("Friends", LocalDate.of(1990, 3, 3), "Just students");

        Tweet tweet = new Tweet(firstUser.getUserId(), null, "Nothing else matters");
        Tweet tweetReply1 = new Tweet(secondUser.getUserId(), tweet, "Do something");
        Tweet tweetReply2 = new Tweet(firstUser.getUserId(), tweetReply1, "I do");

        UserFeed firstUserFeed = new UserFeed(Arrays.asList(tweet, tweetReply2), firstUser.getUserId(), true);
        TweetFeed firstUserTweetFeed = new TweetFeed(Arrays.asList(tweetReply1, tweetReply2), tweet.getTweetId());

        System.out.println(firstUserFeed);
        System.out.println(firstUserTweetFeed);

    }
}
