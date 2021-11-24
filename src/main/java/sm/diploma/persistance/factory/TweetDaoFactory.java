package sm.diploma.persistance.factory;

import sm.diploma.commanOption.DaoType;
import sm.diploma.persistance.TweetDao;
import sm.diploma.persistance.UserDao;

public interface TweetDaoFactory {

    TweetDao createTweetDao(DaoType type);

    TweetDao createTweetsMemDao();

    TweetDao createTweetsJdbc();

}
