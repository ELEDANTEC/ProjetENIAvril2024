package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.bo.Withdrawal;
import fr.eni.projeteniavril2024.dal.SoldItemDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SoldItemDAOImpl implements SoldItemDAO {
    private static final String SELECT_ALL = "" +
            "SELECT si.item_id, si.item_name, si.description, si.start_auction_date, si.end_auction_date, si.initial_price, si.sale_price, si.user_id, si.category_id, " +
            "       c.label, " +
            "       u.username, u.last_name, u.first_name, u.email, u.phone, u.street, u.postal_code, u.city, u.credit, u.administrator, " +
            "       w.street, w.postal_code, w.city " +
            "FROM SOLD_ITEMS AS si " +
            "   INNER JOIN CATEGORIES AS c " +
            "       ON si.category_id = c.category_id " +
            "   INNER JOIN USERS AS u " +
            "       ON si.user_id = u.user_id " +
            "   INNER JOIN WITHDRAWALS AS w " +
            "       ON si.item_id = w.item_id;";
    private static final String SELECT_BY_ID = "" +
            "SELECT si.item_id, si.item_name, si.description, si.start_auction_date, si.end_auction_date, si.initial_price, si.sale_price, si.user_id, si.category_id, " +
            "       c.label, " +
            "       u.username, u.last_name, u.first_name, u.email, u.phone, u.street, u.postal_code, u.city, u.credit, u.administrator, " +
            "       w.street, w.postal_code, w.city " +
            "FROM SOLD_ITEMS AS si " +
            "   INNER JOIN CATEGORIES AS c " +
            "       ON si.category_id = c.category_id " +
            "   INNER JOIN USERS AS u " +
            "       ON si.user_id = u.user_id " +
            "   INNER JOIN WITHDRAWALS AS w " +
            "       ON si.item_id = w.item_id;";
    private static final String INSERT_INTO = "" +
            "INSERT INTO SOLD_ITEMS " +
            "       (item_name, description, start_auction_date, end_auction_date, initial_price, user_id, category_id) " +
            "   VALUES " +
            "       (:item_name, :description, :start_auction_date, :end_auction_date, :initial_price, :user_id, :category_id);";
    private static final String SELECT_UNIQUE = "" +
            "SELECT COUNT(item_id) FROM SOLD_ITEMS WHERE item_name = :item_name AND description = :description AND category_id = :category_id AND user_id = :user_id;";

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

    @Override
    public int create(SoldItem soldItem) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_name", soldItem.getItemName());
        namedParameters.addValue("description", soldItem.getDescription());
        namedParameters.addValue("start_auction_date", soldItem.getStartAuctionDate());
        namedParameters.addValue("end_auction_date", soldItem.getEndAuctionDate());
        namedParameters.addValue("initial_price", soldItem.getInitialPrice());
        namedParameters.addValue("user_id", soldItem.getSeller().getUserId());
        namedParameters.addValue("category_id", soldItem.getCategory().getCategoryId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_INTO, namedParameters, keyHolder);
        Number key = keyHolder.getKey();

        return key.intValue();
    }

    @Override
    public int uniqueSoldItem(SoldItem soldItem) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_name", soldItem.getItemName());
        namedParameters.addValue("description", soldItem.getDescription());
        namedParameters.addValue("category_id", soldItem.getCategory().getCategoryId());
        namedParameters.addValue("user_id", soldItem.getSeller().getUserId());

        return namedParameterJdbcTemplate.queryForObject(SELECT_UNIQUE, namedParameters, Integer.class);
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

            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setItemId(rs.getInt("si.item_id"));
            withdrawal.setStreet(rs.getString("w.street"));
            withdrawal.setPostalCode(rs.getString("w.postal_code"));
            withdrawal.setCity(rs.getString("w.city"));
            soldItem.setWithdrawal(withdrawal);

            return soldItem;
        }
    }
}
