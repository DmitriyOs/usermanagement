package dbconnection;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import entities.User;
import exceptions.DBException;
import exceptions.IncorrectUserException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.Assert.*;

public class DBConnectionTest {
    //from https://blogs.oracle.com/randystuph/entry/injecting_jndi_datasources_for_junit
    @BeforeClass
    public static void setUpClass() {
        // rcarver - setup the jndi context and the datasource
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            // Construct DataSource
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
            ds.setURL("jdbc:mysql://localhost:3306/usermanagement");
            ds.setUser("root");
            ds.setPassword("root");

            ic.bind("java:/comp/env/jdbc/testdb", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testDatabaseIsEmpty() throws DBException {
        assertFalse(DBConnection.databaseIsEmpty());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetUser() throws DBException, UserNotFoundException {
        User user = DBConnection.getUser("user1");
        assertEquals("user1", user.getLogin());
        assertEquals("user1", user.getPassword());
        assertEquals("user", user.getRole());
        assertEquals("First User", user.getFullName());
        assertNull(user.getEmail());
        assertNull(user.getMobilePhone());
        thrown.expect(UserNotFoundException.class);
        User user2 = DBConnection.getUser("nouser");

    }

    @Test
    public void testGetUserRole() throws DBException, UserNotFoundException {
        assertEquals("admin", DBConnection.getUserRole("first"));
        assertEquals("admin", DBConnection.getUserRole("second"));
        assertEquals("user", DBConnection.getUserRole("user1"));
        assertEquals("user", DBConnection.getUserRole("user2"));
        assertEquals("user", DBConnection.getUserRole("user3"));
        thrown.expect(UserNotFoundException.class);
        String role = DBConnection.getUserRole("nouser");
    }

    @Test
    public void testGetListUsers() throws DBException {
        List<User> users = DBConnection.getListUsers();
        for (User x : users) {
            System.out.println(x.getLogin()
                    + " " + x.getPassword()
                    + " " + x.getRole()
                    + " " + x.getFullName()
                    + " " + x.getEmail()
                    + " " + x.getMobilePhone());
        }
    }

    @Test
    public void testGetListRoles() throws DBException {
        for (String x : DBConnection.getListRoles()) {
            System.out.println(x);
        }
    }

    @Test
    public void testAddUser() throws DBException, UserAlreadyExistsException, IncorrectUserException {
        User user = new User("5", "xxx", "admin", "n", null, "2");
        DBConnection.addUser(user);
    }

    @Test
    public void testUpdateUser() throws DBException, UserNotFoundException, IncorrectUserException, UserAlreadyExistsException {
        String oldLogin = "7";
        User user = new User("7", "51", "admin", "55", null, null);
        DBConnection.updateUser(oldLogin, user);
    }

    @Test
    public void testDeleteUser() throws DBException, UserNotFoundException {
        DBConnection.deleteUser("3");
    }

    @Test
    public void testVerifyUser() throws DBException, IncorrectUserException {
        User user = new User(null, "xxx", "xxx", "xxx", null, null);
        //DBConnection.verifyUser(user);
    }
}