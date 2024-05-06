package com.daclink.project2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.daclink.project2.database.entities.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DiveHubUnitTest {
    User testUser;
    String username = "testUsername";
    String password = "testPassword";

    @Before
    public void setup() {
        testUser = new User(username, password);
    }

    @Test
    public void userTest(){
        testUser = null;
        assertNull(testUser);
        testUser = new User("testusername1", "testpassword1");
        assertNotNull(testUser);
    }

    @Test
    public void getUsername() {
        assertEquals(username, testUser.getUsername());
    }

    @Test
    public void setPassword() {
        String newPassword = "newPassword";
        assertNotEquals(newPassword, testUser.getPassword());
        testUser.setPassword(newPassword);
        assertEquals(newPassword, testUser.getPassword());
    }
}