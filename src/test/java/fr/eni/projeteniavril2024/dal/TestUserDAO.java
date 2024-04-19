package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TestUserDAO {
    @Autowired
    private UserDAO userDAO;

    @Test
    void test01_user_findAll() {
        int expectedCount = 9;
        String expectedFirstUserName = "bobDrr";
        String expectedFirstUserEmail = "bobD@gmail.com";

        List<User> users = userDAO.findAll();

        assertNotNull(users);
        assertEquals(expectedCount, users.size());
        assertEquals(expectedFirstUserName, users.get(0).getUsername());
        assertEquals(expectedFirstUserEmail, users.get(0).getEmail());
    }

    @Test
    void test02_user_findById() {
        int userId = 1;
        String expectedUserName = "bobDrr";
        String expectedEmail = "bobD@gmail.com";

        User user = userDAO.findById(userId);

        assertNotNull(user);
        assertEquals(expectedUserName, user.getUsername());
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    void test03_user_findByUsername() {
        String username = "bobDrr";
        String expectedEmail = "bobD@gmail.com";
        int expectedUserId = 1;

        User user = userDAO.findByUsername(username);

        assertNotNull(user);
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedUserId, user.getUserId());
    }

    @Test
    void test04_user_findByEmail() {
        String email = "bobD@gmail.com";
        String expectedUserName = "bobDrr";
        int expectedUserId = 1;

        User user = userDAO.findByEmail(email);

        assertNotNull(user);
        assertEquals(expectedUserName, user.getUsername());
        assertEquals(expectedUserId, user.getUserId());
    }

    @Test
    void test05_user_existingUser() {
        int userId = 1;
        int expectedCount = 1;

        int count = userDAO.existingUser(userId);

        assertEquals(expectedCount, count);
    }

    @Test
    void test06_user_isUniqueUsername() {
        String username = "bobDrr";
        int expectedCount = 1;

        int count = userDAO.isUniqueUsername(username);

        assertEquals(expectedCount, count);
    }

    @Test
    void test07_user_isUniqueEmail() {
        String email = "bobD@gmail.com";
        int expectedCount = 1;

        int count = userDAO.isUniqueEmail(email);

        assertEquals(expectedCount, count);
    }
}
