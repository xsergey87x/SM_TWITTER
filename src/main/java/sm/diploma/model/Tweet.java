package sm.diploma.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Tweet {

    private static long tweetSequence = 0;

    private Long tweetId;
    private Long userId;
    private Long referenceTweetId;
    private final LocalDate datePoster;
    private final String content;
    private List<User> mentionedUsers;
    private List<User> likes;
    private List<Tweet> retweets;

    public Tweet(Long userId, Long referenceTweetId, String content) {
        this.tweetId = ++tweetSequence;
        this.userId = userId;
        this.referenceTweetId = referenceTweetId;
        this.datePoster = LocalDate.now();
        this.content = content;
        this.mentionedUsers = parseContentForMentions(content);
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }

    public Tweet(Long userId, Long referenceTweetId, LocalDate dataPoster, String content) {
        this.userId = userId;
        this.referenceTweetId = referenceTweetId;
        this.datePoster = dataPoster;
        this.content = content;
        this.mentionedUsers = parseContentForMentions(content);
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }

    public Tweet(Long userId, Long tweetId, Long referenceTweetId, LocalDate dataPoster, String content) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.referenceTweetId = referenceTweetId;
        this.datePoster = dataPoster;
        this.content = content;
        this.mentionedUsers = parseContentForMentions(content);
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }

    public Tweet(Tweet other) {
        this.tweetId = other.tweetId;
        this.userId = other.userId;
        this.datePoster = other.datePoster;
        this.referenceTweetId = other.referenceTweetId;
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

    public Long getReferenceTweetId() {
        return referenceTweetId;
    }

    public LocalDate getDatePosted() {
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
        return Objects.equals(tweetId, tweet.tweetId) && Objects.equals(userId, tweet.userId) && Objects.equals(referenceTweetId, tweet.referenceTweetId) && Objects.equals(datePoster, tweet.datePoster) && Objects.equals(content, tweet.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweetId, userId, referenceTweetId, datePoster, content);
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetId=" + tweetId +
                ", userId=" + userId +
                ", referenceTweetId=" + referenceTweetId +
                ", datePoster=" + datePoster +
                ", content='" + content + '\'' +
                '}';
    }
}

