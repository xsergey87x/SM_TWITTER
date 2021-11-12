package sm.diploma.persistance.jdbc;

import sm.diploma.model.User;
import sm.diploma.persistance.TweetDao;
import sm.diploma.persistance.UserDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbcImpl implements UserDao {

    @Override
    public Long save(User user) throws SQLException {

        String preparedQuery = "INSERT INTO users (nickName, dataRegistered, dateOfBirth,about) "
                + "VALUES ('" + user.getNickName() + "','" + user.getDateRegister()
                + "','" + user.getDateOfBirth() + "','" + user.getAbout() + "');";

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(preparedQuery);

        DbUtils.closeConnection(connection, statement);
        return getUserIDFromNickName(user.getNickName());
    }

    public Long getUserIDFromNickName(String nickName) throws SQLException {
        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        String preparedQuery = "SELECT * FROM users WHERE nickName = '" + nickName + "';";

        ResultSet resultSet = statement.executeQuery(preparedQuery);
        resultSet.next();
        Long resultUserId = resultSet.getLong("userId");

        DbUtils.closeConnection(connection, statement);
        return resultUserId;
    }

    @Override
    public Optional<User> findUserById(long userId) throws SQLException {
        String preparedQuery = " SELECT * FROM users WHERE userId = " + userId + ";";

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(preparedQuery);
        resultSet.next();
        String dateOfBirth = resultSet.getString("dateOfBirth");
        String dateRegister = resultSet.getString("dataRegistered");
        LocalDate resultDateBirth = LocalDate.parse(dateOfBirth);
        LocalDate resultDateRegistered = LocalDate.parse(dateRegister);

        User user = new User(userId, resultSet.getString("nickName"), resultDateBirth, resultDateRegistered, resultSet.getString("about"));
        DbUtils.closeConnection(connection, statement);
        return Optional.of(user);
    }

    @Override
    public List<User> getAll() throws SQLException {

        String preparedQuery = "SELECT * FROM users";
        String dateBirth, dateRegistered;
        List<User> userList = new ArrayList();

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(preparedQuery);

        while (resultSet.next()) {
            dateBirth = resultSet.getString("dateOfBirth");
            dateRegistered = resultSet.getString("dataRegistered");
            User user = new User(resultSet.getLong("userId"), resultSet.getString("nickName"),
                    LocalDate.parse(dateRegistered), LocalDate.parse(dateBirth), resultSet.getString("about"));

            userList.add(user);
        }

        DbUtils.closeConnection(connection, statement);
        return userList;
    }

    @Override
    public void updateUser(User user) throws SQLException {

        String preparedQuery = "UPDATE  users SET dateOfBirth = '" + user.getDateOfBirth() +
                "',about = '" + user.getAbout() + "'WHERE nickName = '" + user.getNickName() + "';";

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        if (statement.execute(preparedQuery)) {
            ResultSet resultSet = statement.executeQuery(preparedQuery);
        }
        DbUtils.closeConnection(connection, statement);
    }

    @Override
    public boolean deleteUserById(long userId) throws SQLException {

        String preparedQuery = "DELETE FROM users WHERE userId = " + userId + ";";

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        if (statement.execute(preparedQuery)) {
            TweetDao tweetDao = new TweetDaoJdbcImpl();
            tweetDao.deleteTweetById(userId);
            ResultSet resultSet = statement.executeQuery(preparedQuery);
            DbUtils.closeConnection(connection, statement);
            return true;
        }
        DbUtils.closeConnection(connection, statement);
        return false;
    }
}
