package sm.diploma.persistance.jdbc;

import sm.diploma.model.User;
import sm.diploma.persistance.TweetDao;
import sm.diploma.persistance.UserDao;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbcImpl implements UserDao {

    private static final String INSERT_USER_QUERY = "INSERT INTO users (login, nickName, dateRegistered, dateOfBirth, about) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_USER_QUERY = "UPDATE  users SET nickName = ?, dateRegistered = ?, dateOfBirth = ?, about = ? WHERE login = ?;";
    private static final String SELECT_USER_QUERY = "SELECT MAX(userId) FROM users;";
    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE userId = ?;";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE userId = ?;";
    private TweetDao tweetDao = new TweetDaoJdbcImpl();

    @Override
    public Long save(User user) {

        Long resultId = 0L;
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statementInsert = connection.prepareStatement(INSERT_USER_QUERY);
             Statement statementSelect = connection.createStatement();) {
            statementInsert.setString(1, user.getUserLogin());
            statementInsert.setString(2, user.getNickName());
            statementInsert.setString(3, String.valueOf(user.getDateRegister()));
            statementInsert.setString(4, String.valueOf(user.getDateOfBirth()));
            statementInsert.setString(5, user.getAbout());
            Integer resultSetAdd = statementInsert.executeUpdate();
            ResultSet resultSetFind = statementSelect.executeQuery(SELECT_USER_QUERY);
            while (resultSetFind.next()) {
                resultId = resultSetFind.getLong("MAX(userId)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultId;
    }

    @Override
    public Optional<User> findUserById(long userId) throws SQLException {

        Connection connection = DbUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_USER_QUERY);
        statement.setLong(1, userId);
        ResultSet resultSetFindQuery = statement.executeQuery();

        if (resultSetFindQuery.next()) {
            String userLogin = resultSetFindQuery.getString("login");
            String userNickName = resultSetFindQuery.getString("nickName");
            String aboutUser = resultSetFindQuery.getString("about");
            LocalDate dateBirth = LocalDate.parse(resultSetFindQuery.getString("dateOfBirth"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate dateRegister = LocalDate.parse(resultSetFindQuery.getString("dateRegistered"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DbUtils.closeConnection(connection, statement);
            return Optional.of(createUser(userId, userLogin, userNickName, dateBirth, dateRegister, aboutUser));
        } else {
            DbUtils.closeConnection(connection, statement);
        }
        return Optional.empty();
    }

    User createUser(Long userId, String userLogin, String nickName, LocalDate dateOfBirth, LocalDate dateRegister, String about) {
        return new User(userId, userLogin, nickName, dateOfBirth, dateRegister, about);
    }

    @Override
    public List<User> getAll() throws SQLException {

        List<User> userList = new ArrayList();
        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()) {
            LocalDate dateBirth = LocalDate.parse(resultSet.getString("dateOfBirth"));
            LocalDate dateRegistered = LocalDate.parse(resultSet.getString("dataRegistered"));
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
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);
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

        Connection connection = DbUtils.getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement(DELETE_USER_QUERY);
        deleteStatement.setLong(1, userId);

        if (deleteStatement.execute(DELETE_USER_QUERY)) {
            tweetDao.deleteTweetById(userId);
            int deletedRows = deleteStatement.executeUpdate();
            DbUtils.closeConnection(connection, deleteStatement);
            return deletedRows == 1;
        }
        DbUtils.closeConnection(connection, deleteStatement);
        return false;
    }

}
