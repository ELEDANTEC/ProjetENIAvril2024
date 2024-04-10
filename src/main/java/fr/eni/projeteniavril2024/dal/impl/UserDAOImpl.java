package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.UserDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final String SELECT_BY_ID = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM USERS WHERE user_id = :user_id;";
    private static final String SELECT_BY_EMAIL = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM USERS WHERE email = :email;";
    private static final String SELECT_BY_USERNAME = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM USERS WHERE username = :username;";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User findById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("user_id", id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                namedParameters,
                new UserRowMapper()
        );
    }

    @Override
    public User findByEmail(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_EMAIL,
                namedParameters,
                new UserRowMapper()
        );
    }

    @Override
    public User findByUsername(String username) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("username", username);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_USERNAME,
                namedParameters,
                new UserRowMapper()
        );
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setLastName(rs.getString("last_name"));
            user.setFirstName(rs.getString("first_name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setStreet(rs.getString("street"));
            user.setPostalCode(rs.getString("postal_code"));
            user.setCity(rs.getString("city"));
            user.setCredit(rs.getInt("credit"));
            user.setAdministrator(rs.getBoolean("administrator"));

            return user;
        }
    }
}
