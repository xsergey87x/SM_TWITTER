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

    private static final String insertUserQuery = "INSERT INTO users (login, nickName, dateRegistered, dateOfBirth, about) VALUES (?, ?, ?, ?, ?);";
    private static final String updateUserQuery = "UPDATE  users SET nickName = ?, dateRegistered = ?, dateOfBirth = ?, about = ? WHERE login = ?;";

    @Override
    public Long save(User user) throws SQLException {

        String preparedQueryFind = " SELECT * FROM users WHERE login = '" + user.getUserLogin() + "';";
        Long resultId = 0L;
        Connection connection = DbUtils.getConnection();
        Statement findStatement = connection.createStatement();
        PreparedStatement statement = connection.prepareStatement(insertUserQuery);
        statement.setString(1, user.getUserLogin());
        statement.setString(2, user.getNickName());
        statement.setString(3, String.valueOf(user.getDateRegister()));
        statement.setString(4, String.valueOf(user.getDateOfBirth()));
        statement.setString(5, user.getAbout());
        Integer resultSetAdd = statement.executeUpdate();
        ResultSet resultSetFind = findStatement.executeQuery(preparedQueryFind);
        while (resultSetFind.next()) {
            resultId = resultSetFind.getLong("userId");
        }
        DbUtils.closeConnection(connection, statement, findStatement);
        return resultId;
    }

    @Override
    public Optional<User> findUserById(long userId) throws SQLException {
        String preparedQuery = " SELECT * FROM users WHERE userId = " + userId + ";";
        String userLogin = null, userNickName = null, aboutUser = null, dateBirth = null, dateRegister = null;

        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(preparedQuery);
        while (resultSet.next()) {
            userLogin = resultSet.getString("login");
            userNickName = resultSet.getString("nickName");
            aboutUser = resultSet.getString("about");
            dateBirth = resultSet.getString("dateOfBirth");
            dateRegister = resultSet.getString("dateRegistered");
        }

        DbUtils.closeConnection(connection, statement);
        return Optional.of(createUser(userId, userLogin, userNickName, dateBirth, dateRegister, aboutUser));
    }

    User createUser(Long userId, String userLogin, String nickName, String dateOfBirth, String dateRegister, String about) {
        return new User(userId, userLogin, nickName, LocalDate.parse(dateOfBirth), LocalDate.parse(dateRegister), about);
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
            Long userId = resultSet.getLong("userId");
            String login = resultSet.getString("login");
            String nickName = resultSet.getString("nickName");
            String aboutUser = resultSet.getString("about");

            userList.add(createUser(userId, login, nickName, dateBirth, dateRegistered, aboutUser));
        }

        DbUtils.closeConnection(connection, statement);
        return userList;
    }

    @Override
    public void updateUser(User user) throws SQLException {

        Connection connection = DbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateUserQuery);
        statement.setString(1, user.getNickName());
        statement.setString(2, String.valueOf(user.getDateRegister()));
        statement.setString(3, String.valueOf(user.getDateOfBirth()));
        statement.setString(4, user.getAbout());
        statement.setString(5, user.getUserLogin());
        Integer resultSetAdd = statement.executeUpdate();

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
