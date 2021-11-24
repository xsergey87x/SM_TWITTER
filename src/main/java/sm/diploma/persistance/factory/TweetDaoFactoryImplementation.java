package sm.diploma.persistance.factory;

import sm.diploma.commanOption.DaoType;
import sm.diploma.persistance.TweetDao;
import sm.diploma.persistance.jdbc.TweetDaoJdbcImpl;
import sm.diploma.persistance.memory.TweetDaoMemoryImplementation;

public class TweetDaoFactoryImplementation implements TweetDaoFactory {

    @Override
    public TweetDao createTweetDao(DaoType type) {
        if (DaoType.IN_MEM_Base.equals(type)) {
            return createTweetsMemDao();
        } else if (DaoType.JDBC.equals(type)) {
            return createTweetsJdbc();
        }
        throw new IllegalArgumentException("Incorrect DAO Type");
    }

    @Override
    public TweetDao createTweetsMemDao() {
        return new TweetDaoMemoryImplementation();
    }

    @Override
    public TweetDao createTweetsJdbc() {
        return new TweetDaoJdbcImpl();
    }
}
