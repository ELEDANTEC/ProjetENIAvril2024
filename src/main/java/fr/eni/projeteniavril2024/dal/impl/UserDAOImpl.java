package fr.eni.projetenavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final static String SELECT_ALL_USERS = "SELECT * FROM Users;";
    private final static String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE id = :id;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SELECT_ALL_USERS, new UserRowMapper());
    }

    @Override
    public User getUserById(int userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", userId);
        return namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_ID, namedParameters, new UserRowMapper());
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setUsername(rs.getString("username"));
            user.setLastName(rs.getString("lastName"));
            user.setFirstName(rs.getString("firstName"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setStreet(rs.getString("street"));
            user.setCity(rs.getString("city"));
            user.setPostalCode(rs.getString("postalCode"));
            user.setPassword(rs.getString("password"));
            user.setCredit(rs.getInt("credit"));
            user.setAdministrator(rs.getBoolean("administrator"));
            return user;
        }
    }
}