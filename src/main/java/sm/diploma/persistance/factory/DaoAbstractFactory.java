package sm.diploma.persistance.factory;

import sm.diploma.commanOption.DaoType;
import sm.diploma.persistance.TweetDao;
import sm.diploma.persistance.UserDao;

public class DaoAbstractFactory {

    private DaoType type;

    private final UserDaoFactory userDaoFactory;
    private final TweetDaoFactory tweetDaoFactory;

    public DaoAbstractFactory(UserDaoFactory userDaoFactory, TweetDaoFactory tweetDaoFactory) {
        this.userDaoFactory = userDaoFactory;
        this.tweetDaoFactory = tweetDaoFactory;
    }

    public UserDao createUserDao() {
        return userDaoFactory.createUserDao(type);
    }

    public TweetDao createTweetDao() {
        return tweetDaoFactory.createTweetDao(type);
    }

    public void setDaoType(DaoType type) {
        this.type = type;
    }
}
