package sm.diploma.persistance;

import sm.diploma.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Long save(User user);

    Optional<User> findUserById(long userId);

    List<User> getAll();

    void updateUser(User user);

    boolean deleteUserById(long userId);

}
