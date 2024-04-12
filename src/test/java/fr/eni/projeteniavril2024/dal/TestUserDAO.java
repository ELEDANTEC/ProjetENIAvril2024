package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TestUserDAO {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void test01_user_findById() {
        int userId = 1;
        String expectedUserName = "bobD";
        String expectedEmail = "bobD@gmail.com";

        User user = userDAO.findById(userId);

        assertNotNull(user);
        assertEquals(expectedUserName, user.getUsername());
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    void test02_user_findByEmail() {
        String email = "bobD@gmail.com";
        String expectedUserName = "bobD";
        int expectedUserId = 1;

        User user = userDAO.findByEmail(email);

        assertNotNull(user);
        assertEquals(expectedUserName, user.getUsername());
        assertEquals(expectedUserId, user.getUserId());
    }

    @Test
    void test03_user_findByUsername() {
        String username = "bobD";
        String expectedEmail = "bobD@gmail.com";
        int expectedUserId = 1;

        User user = userDAO.findByUsername(username);

        assertNotNull(user);
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedUserId, user.getUserId());
    }

    @Test
    void test04_user_findByEmailAndPassword() {
        String email = "a@gmail.com";
        String expectedUserName = "Evaristo";
        int expectedUserId = 1;

        /*User user = userDAO.Adduser(email);*/
        jdbcTemplate.update("INSERT INTO USERS (username, last_name, first_name, email, street, postal_code, city, password, credit, administrator) VALUES (?, ?, ?,?,?,?,?,?,?,?)",
                "Evaristo",
                        "LE DANTEC",
                        "Evariste",
                        "a@gmail.com",
                        "rue",
                        "test",
                        "test",
                        passwordEncoder.encode(  "123456789"),
                        1500,
                        false) ;
    }
}
