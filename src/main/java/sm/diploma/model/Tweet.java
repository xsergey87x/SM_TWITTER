package sm.diploma.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tweet {

    private static long tweetSequence = 0;

    private Long tweetId;
    private Long userId;
    private Tweet referenceTweet;
    private Long referenceTweetId;
    private final LocalDate datePoster;
    private final String content;
    private List<User> mentionedUsers;
    private List<User> likes;
    private List<Tweet> retweets;

    public Tweet(Long userId, Tweet referenceTweet, String content) {
        this.tweetId = ++tweetSequence;
        this.userId = userId;
        this.referenceTweet = referenceTweet;
        this.datePoster = LocalDate.now();
        this.content = content;
        this.mentionedUsers = parseContentForMentions(content);
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }

    public Tweet(Long userId, Tweet referenceTweet, Long referenceTweetId, String content) {
        this.tweetId = ++tweetSequence;
        this.userId = userId;
        this.referenceTweet = referenceTweet;
        this.referenceTweetId = referenceTweetId;
        this.datePoster = LocalDate.now();
        this.content = content;
        this.mentionedUsers = parseContentForMentions(content);
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }

    public Tweet(Long userId, Long tweetId, Long referenceTweetId, LocalDate dataPoster, String content) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.referenceTweetId = referenceTweetId;
        this.referenceTweet = null;
        this.datePoster = dataPoster;
        this.content = content;
        this.mentionedUsers = parseContentForMentions(content);
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }

    public Tweet(Tweet other) {
        this.tweetId = other.tweetId;
        this.userId = other.userId;
        this.referenceTweet = other.referenceTweet;
        this.datePoster = other.datePoster;
        this.content = other.content;
        this.mentionedUsers = other.mentionedUsers;
        this.likes = other.likes;
        this.retweets = other.retweets;
    }

    private List<User> parseContentForMentions(String content) {
        return Collections.emptyList();
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Tweet getReferenceTweet() {
        return referenceTweet;
    }

    public Long getReferenceTweetId() {
        return referenceTweetId;
    }

    public void setReferenceTweet(Tweet referenceTweet) {
        this.referenceTweet = referenceTweet;
    }

    public LocalDate getDatePoster() {
        return datePoster;
    }

    public String getContent() {
        return content;
    }

    public List<User> getMentionedUsers() {
        return mentionedUsers;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<Tweet> getRetweets() {
        return retweets;
    }

    public void setRetweets(List<Tweet> retweets) {
        this.retweets = retweets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (tweetId != null ? !tweetId.equals(tweet.tweetId) : tweet.tweetId != null) return false;
        if (!userId.equals(tweet.userId)) return false;
        if (referenceTweet != null ? !referenceTweet.equals(tweet.referenceTweet) : tweet.referenceTweet != null)
            return false;
        if (!datePoster.equals(tweet.datePoster)) return false;
        return content != null ? content.equals(tweet.content) : tweet.content == null;
    }

    @Override
    public int hashCode() {
        int result = tweetId != null ? tweetId.hashCode() : 0;
        result = 31 * result + userId.hashCode();
        result = 31 * result + (referenceTweet != null ? referenceTweet.hashCode() : 0);
        result = 31 * result + datePoster.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetId=" + tweetId +
                ", userId=" + userId +
                ", referenceTweet=" + referenceTweet +
                ", referenceTweetId=" + referenceTweetId +
                ", datePoster=" + datePoster +
                ", content='" + content + '\'' +
                '}';
    }
}

