package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.SoldItemDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SoldItemDAOImpl implements SoldItemDAO {
    private static final String SELECT_ALL = "" +
            "SELECT si.item_id, si.item_name, si.description, si.start_auction_date, si.end_auction_date, si.initial_price, si.sale_price, si.user_id, si.category_id, " +
            "       c.label, " +
            "       u.username, u.last_name, u.first_name, u.email, u.phone, u.street, u.postal_code, u.city, u.credit, u.administrator " +
            "FROM SOLD_ITEMS AS si " +
            "   INNER JOIN CATEGORIES AS c " +
            "       ON si.category_id = c.category_id " +
            "   INNER JOIN USERS AS u " +
            "       ON si.user_id = u.user_id;";
    private static final String SELECT_BY_ID = "" +
            "SELECT si.item_id, si.item_name, si.description, si.start_auction_date, si.end_auction_date, si.initial_price, si.sale_price, si.user_id, si.category_id, " +
            "       c.label, " +
            "       u.username, u.last_name, u.first_name, u.email, u.phone, u.street, u.postal_code, u.city, u.credit, u.administrator " +
            "FROM SOLD_ITEMS AS si " +
            "   INNER JOIN CATEGORIES AS c " +
            "       ON si.category_id = c.category_id " +
            "   INNER JOIN USERS AS u " +
            "       ON si.user_id = u.user_id " +
            "WHERE si.item_id = :item_id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public SoldItemDAOImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTemplate jdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SoldItem> findAll() {
        return jdbcTemplate.query(
                SELECT_ALL,
                new SoldItemRowMapper()
        );
    }

    @Override
    public SoldItem findById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("item_id", id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                namedParameters,
                new SoldItemRowMapper()
        );
    }

    public static class SoldItemRowMapper implements RowMapper<SoldItem> {
        @Override
        public SoldItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoldItem soldItem = new SoldItem();
            soldItem.setItemId(rs.getInt("si.item_id"));
            soldItem.setItemName(rs.getString("si.item_name"));
            soldItem.setDescription(rs.getString("si.description"));
            soldItem.setStartAuctionDate(rs.getDate("si.start_auction_date").toLocalDate());
            soldItem.setEndAuctionDate(rs.getDate("si.end_auction_date").toLocalDate());
            soldItem.setInitialPrice(rs.getInt("si.initial_price"));
            soldItem.setSalePrice(rs.getInt("si.sale_price"));

            Category category = new Category();
            category.setCategoryId(rs.getInt("si.category_id"));
            category.setLabel(rs.getString("c.label"));
            soldItem.setCategory(category);

            User user = new User();
            user.setUserId(rs.getInt("si.user_id"));
            user.setUsername(rs.getString("u.username"));
            user.setLastName(rs.getString("u.last_name"));
            user.setFirstName(rs.getString("u.first_name"));
            user.setEmail(rs.getString("u.email"));
            user.setPhone(rs.getString("u.phone"));
            user.setStreet(rs.getString("u.street"));
            user.setPostalCode(rs.getString("u.postal_code"));
            user.setCity(rs.getString("u.city"));
            user.setCredit(rs.getInt("u.credit"));
            user.setAdministrator(rs.getBoolean("u.administrator"));
            soldItem.setSeller(user);

            return soldItem;
        }
    }
}
