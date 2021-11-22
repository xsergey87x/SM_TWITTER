package sm.diploma.persistance;

import sm.diploma.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Long save(User user);

    Optional<User> findUserById(long userId) throws SQLException;

    List<User> getAll() throws SQLException;

    void updateUser(User user) throws SQLException;

    boolean deleteUserById(long userId) throws SQLException;

}
