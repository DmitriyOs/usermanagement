package dbconnection;

import entities.User;
import exceptions.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide interface with UserManagement Database
 */
public class DBConnection {
    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/testdb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return true, if database is Empty
     * @throws DBException if something happened with database connection
     */
    public static boolean databaseIsEmpty() throws DBException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM user");
            resultSet.next();
            return resultSet.getInt("count") == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    /**
     * @param login
     * @return User
     * @throws DBException           if something happened with database connection
     * @throws UserNotFoundException if user was not found
     */
    public static User getUser(String login) throws DBException, UserNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT login, password, role, fullName, email, mobilePhone FROM user WHERE login='" + login + "'");
            if (resultSet.next()) {
                return new User(resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("fullName"),
                        resultSet.getString("email"),
                        resultSet.getString("mobilePhone"));
            } else throw new UserNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    /**
     * @param login
     * @return Role of the User
     * @throws UserNotFoundException if user was not found
     * @throws DBException           if something happened with database connection
     */
    public static String getUserRole(String login) throws UserNotFoundException, DBException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT role FROM user WHERE login='" + login + "'");
            if (resultSet.next())
                return resultSet.getString("role");
            else throw new UserNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    /**
     * @return List of User
     * @throws DBException if something happened with database connection
     */
    public static List<User> getListUsers() throws DBException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT login, password, role, fullName, email, mobilePhone FROM user");
            ArrayList<User> users = new ArrayList<User>();
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("fullName"),
                        resultSet.getString("email"),
                        resultSet.getString("mobilePhone")));
            }
            if (users.size() == 0) return null;
            else return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    /**
     * @return List of user roles
     * @throws DBException if something happened with database connection
     */
    public static List<String> getListRoles() throws DBException, NullPointerException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT role FROM role");
            ArrayList<String> roles = new ArrayList<String>();
            while (resultSet.next()) {
                roles.add(resultSet.getString("role"));
            }
            if (roles.size() == 0) return null;
            else return roles;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    /**
     * @param user data for adding
     * @throws DBException                if something happened with database connection
     * @throws UserAlreadyExistsException if user with the same user login already in database
     * @throws IncorrectUserException     if user data is incorrect
     */
    public static void addUser(User user) throws DBException, UserAlreadyExistsException, IncorrectUserException {
        verifyUser(user);
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO User(login,password,role,fullName,";
            String values = ") Values("
                    + "'" + user.getLogin() + "',"
                    + "'" + user.getPassword() + "',"
                    + "'" + user.getRole() + "',"
                    + "'" + user.getFullName() + "',";
            if (user.getEmail() != null) {
                query += "email,";
                values += "'" + user.getEmail() + "',";
            }
            if (user.getMobilePhone() != null) {
                query += "mobilePhone,";
                values += "'" + user.getMobilePhone() + "',";
            }
            if (query.endsWith(",")) query = query.substring(0, query.length() - 1);
            if (values.endsWith(",")) values = values.substring(0, values.length() - 1);
            query = query + values + ")";
            int resultCount = statement.executeUpdate(query);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 && e.getSQLState().equals("23000"))
                throw new UserAlreadyExistsException();
            else {
                e.printStackTrace();
                throw new DBException(e);
            }
        }
    }

    /**
     * Update user data by login
     *
     * @param oldLogin
     * @param newUserData for updating
     * @throws DBException                if something happened with database connection
     * @throws UserNotFoundException      if user was not found
     * @throws IncorrectUserException     if user data is incorrect
     * @throws UserAlreadyExistsException if user with the same login as oldLogin already in database
     */
    public static void updateUser(String oldLogin, User newUserData) throws
            DBException, UserNotFoundException, IncorrectUserException, UserAlreadyExistsException {

        getUser(oldLogin);
        if (!newUserData.getLogin().equals(oldLogin)) {
            try {
                getUser(newUserData.getLogin());
                //'newlogin' already exist in database!
                throw new UserAlreadyExistsException();
            } catch (UserNotFoundException e) {
                //It is REALLY do not needed doing anything. All Ok!
            }
        }
        verifyUser(newUserData);
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "UPDATE User SET login='" + newUserData.getLogin() + "',"
                    + "password='" + newUserData.getPassword() + "',"
                    + "role='" + newUserData.getRole() + "',"
                    + "fullName='" + newUserData.getFullName() + "',"
                    + "password='" + newUserData.getPassword() + "',";
            if (newUserData.getEmail() != null) {
                query += "email='" + newUserData.getEmail() + "',";
            } else
                query += "email=null,";
            if (newUserData.getMobilePhone() != null) {
                query += "mobilePhone='" + newUserData.getMobilePhone() + "',";
            } else
                query += "mobilePhone=null,";
            if (query.endsWith(",")) query = query.substring(0, query.length() - 1);
            query += " WHERE login='" + oldLogin + "'";

            int resultCount = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }


    }

    /**
     * @param login
     * @throws DBException           if something happened with database connection
     * @throws UserNotFoundException if user was not found
     */
    public static void deleteUser(String login) throws DBException, UserNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM User WHERE login='" + login + "'";

            int resultCount = statement.executeUpdate(query);
            if (resultCount <= 0) {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    private static boolean isRoleInDatabase(String role) throws DBException {
        for (String x : getListRoles()) {
            if (x.equals(role))
                return true;
        }
        return false;
    }

    private static void verifyUser(User user) throws DBException, IncorrectUserException {
        String reason = "";
        if (user.getLogin() == null || user.getLogin().length() == 0) reason += "'null' for Login is not allowed. ";
        if (user.getPassword() == null || user.getPassword().length() == 0)
            reason += "'null' for Password is not allowed. ";
        if (user.getFullName() == null || user.getFullName().length() == 0)
            reason += "'null' for FullName is not allowed. ";
        if (!isRoleInDatabase(user.getRole())) reason += "Role is incorrect. ";
        if (user.getEmail().length() == 0) user.setEmail(null);
        if (user.getMobilePhone().length() == 0) user.setMobilePhone(null);
        if (reason.length() > 0) {
            throw new IncorrectUserException(reason);
        }
    }

}
