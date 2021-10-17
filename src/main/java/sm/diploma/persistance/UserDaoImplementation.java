package sm.diploma.persistance;

import sm.diploma.model.User;
import sm.diploma.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDaoImplementation implements UserDao {
    @Override
    public Long save(User user) {
        long newUserId = ++Storage.userIdSequence;
        user.setUserId(newUserId);
        Storage.getUserStorage().put(user.getUserId(), createUserState(user));
        return newUserId;
    }

    @Override
    public Optional<User> findUserById(long userId) {
        final User persistedUser = Storage.getUserStorage().get(userId);
        if (persistedUser != null) {
            User resultUser = createUserState(persistedUser);
            return Optional.of(resultUser);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        for (final User user : Storage.getUserStorage().values()) {
            userList.add(createUserState(user));
        }
        return userList;
    }

    @Override
    public void updateUser(User user) {
        Optional<User> userOptional = findUserById(user.getUserId());
        if (userOptional.isPresent()) {
            Storage.getUserStorage().put(user.getUserId(), createUserState(user));
        } else {
            throw new UserDoesNotExistException(" Updated user does not exist " + user);
        }
    }

    @Override
    public boolean deleteUserById(long userId) {
        Map<Long, User> userStorage = Storage.getUserStorage();
        if (userStorage.containsKey(userId)) {
            userStorage.remove(userId);
            return true;
        } else {
            return false;
        }
    }

    private User createUserState(User userOriginal) {
        return new User(userOriginal);
    }

}
