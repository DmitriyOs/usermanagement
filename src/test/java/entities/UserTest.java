package entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void smallTest() {
        User user = new User("first", null, "admin", null, null, "+123");
        assertEquals("first", user.getLogin());
        assertNull(user.getPassword());
        assertEquals("admin", user.getRole());
        assertNull(user.getFullName());
        assertNull(user.getEmail());
        assertEquals("+123", user.getMobilePhone());
        user.setMobilePhone(null);
        assertNull(user.getMobilePhone());
        user.setFullName("FN");
        assertEquals("FN", user.getFullName());

    }

}