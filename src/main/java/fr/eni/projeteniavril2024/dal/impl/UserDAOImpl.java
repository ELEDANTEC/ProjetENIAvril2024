package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.UserDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final String SELECT_BY_ID = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM USERS WHERE user_id = :user_id;";
    private static final String SELECT_BY_EMAIL = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM USERS WHERE email = :email;";
    private static final String SELECT_BY_USERNAME = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM USERS WHERE username = :username;";
    private static final String UPDATE_BY_ID = "UPDATE USERS SET username = :username, last_name = :last_name, first_name = :first_name, email = :email, phone = :phone, street = :street, postal_code = :postal_code, city = :city, password = :password WHERE user_id = :user_id;";
    private static final String INSERT_USER = "INSERT INTO USERS ("
            + "username, "
            + "last_name, "
            + "first_name, "
            + "email, "
            + "phone, "
            + "street, "
            + "postal_code, "
            + "city, "
            + "password) "
            + "VALUES (:username, :last_name, :first_name, :email, :phone, :street, :postal_code, :city, :password)";
    private final static String SELECT_ALL_USERS = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM Users;";
    private static final String DELETE_USER = "DELETE FROM USERS WHERE user_id = :userId";
    private static final String CREATE_USER = "INSERT INTO USERS (username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator) VALUES (:username, :last_name, :first_name, :email, :phone, :street, :postal_code, :city, :password, :credit, false);";


    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDAOImpl(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SELECT_ALL_USERS, new UserRowMapper());
    }

    @Override
    public User findByEmail(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("email", email);
        try {
            return namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_EMAIL,
                    namedParameters,
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getUserById(int userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", userId);
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new UserRowMapper());
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
    public User findByUsername(String username) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("username", username);
        try {
            return namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_USERNAME,
                    namedParameters,
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateUser(User user) {

        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("last_name", user.getLastName());
        params.put("first_name", user.getFirstName());
        params.put("email", user.getEmail());
        params.put("phone", user.getPhone());
        params.put("street", user.getStreet());
        params.put("postal_code", user.getPostalCode());
        params.put("city", user.getCity());
        params.put("password", user.getPassword());
        params.put("user_id", user.getUserId());  // Assurez-vous que votre objet User a un attribut userId

        namedParameterJdbcTemplate.update(UPDATE_BY_ID, params);
    }

    @Override
    public void deleteUserById(int userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("userId", userId);
        namedParameterJdbcTemplate.update(DELETE_USER, namedParameters);
    }


    @Override
    public void saveUser(User user) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", user.getUsername());
        paramMap.put("last_name", user.getLastName());
        paramMap.put("first_name", user.getFirstName());
        paramMap.put("email", user.getEmail());
        paramMap.put("phone", user.getPhone());
        paramMap.put("street", user.getStreet());
        paramMap.put("postal_code", user.getPostalCode());
        paramMap.put("city", user.getCity());
        paramMap.put("password", user.getPassword());

        jdbcTemplate.update(INSERT_USER, paramMap);
    }

    public void createUser(User user) {
                MapSqlParameterSource namedParameters = new MapSqlParameterSource();
                namedParameters.addValue("username", user.getUsername());
                namedParameters.addValue("last_name", user.getLastName());
                namedParameters.addValue("first_name", user.getFirstName());
                namedParameters.addValue("email", user.getEmail());
                namedParameters.addValue("phone", user.getPhone());
                namedParameters.addValue("street", user.getStreet());
                namedParameters.addValue("postal_code", user.getPostalCode());
                namedParameters.addValue("city", user.getCity());
                namedParameters.addValue("password", user.getPassword());
                namedParameters.addValue("credit", user.getCredit());
                namedParameterJdbcTemplate.update(CREATE_USER, namedParameters);
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
            user.setPassword(rs.getString("password"));
            user.setCredit(rs.getInt("credit"));
            user.setAdministrator(rs.getBoolean("administrator"));
            return user;
        }
    }
}