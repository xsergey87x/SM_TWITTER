package sm.diploma.persistance.factory;

import sm.diploma.commanOption.DaoType;
import sm.diploma.persistance.UserDao;
import sm.diploma.persistance.jdbc.UserDaoJdbcImpl;
import sm.diploma.persistance.memory.UserDaoImplementation;

public class UserDaoFactoryImplement implements UserDaoFactory {

    @Override
    public UserDao createUserDao(DaoType type) {
        if (DaoType.IN_MEM_Base.equals(type)) {
            return createUserMemDao();
        } else if (DaoType.JDBC.equals(type)) {
            return createUserJdbc();
        }
        throw new IllegalArgumentException("Incorrect DAO Type");
    }

    @Override
    public UserDao createUserMemDao() { return new UserDaoImplementation();}

    @Override
    public UserDao createUserJdbc() {
        return new UserDaoJdbcImpl();
    }
}
