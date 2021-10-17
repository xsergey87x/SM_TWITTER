package sm.diploma.persistance;

public class TweetDoesNotExistException extends RuntimeException {
    public TweetDoesNotExistException(String s) {
        super(s);
    }
}
