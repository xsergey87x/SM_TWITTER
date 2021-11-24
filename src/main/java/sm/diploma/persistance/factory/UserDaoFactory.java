package sm.diploma.persistance.factory;

import sm.diploma.commanOption.DaoType;
import sm.diploma.persistance.UserDao;

public interface UserDaoFactory {

    UserDao createUserDao(DaoType type);

    UserDao createUserMemDao();

    UserDao createUserJdbc();

}
