package ru.sanctio.models.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    public void testSetAndGetId() {
        user.setId(1);

        assertEquals(1, user.getId());
    }

    @Test
    public void testSetAndGetLogin() {
        user.setLogin("testUser");

        assertEquals("testUser", user.getLogin());
    }

    @Test
    public void testSetAndGetPassword() {
        user.setPassword("password123");

        assertEquals("password123", user.getPassword());
    }


}