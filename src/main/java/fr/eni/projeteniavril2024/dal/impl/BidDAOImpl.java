package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Bid;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.BidDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BidDAOImpl implements BidDAO {
    private static final String SELECT_ALL = "" +
            "SELECT b.user_id, b.item_id, b.bid_date, b.bid_amount, " +
            "       u.username, u.last_name, u.first_name, u.email, u.phone, u.street, u.postal_code, u.city, u.credit, u.administrator, " +
            "       si.item_name, si.description, si.start_auction_date, si.end_auction_date, si.initial_price, si.sale_price " +
            "FROM BIDS AS b " +
            "   INNER JOIN USERS AS u " +
            "       ON b.user_id = u.user_id " +
            "   INNER JOIN SOLD_ITEMS AS si " +
            "       ON b.item_id = si.item_id " +
            "WHERE b.item_id = :item_id;";
    private static final String INSERT_INTO = "" +
            "INSERT INTO BIDS " +
            "       (user_id, item_id, bid_date, bid_amount) " +
            "   VALUES " +
            "       (:user_id, :item_id, :bid_date, :bid_amount);";
    private static final String DELETE = "" +
            "DELETE " +
            "FROM BIDS " +
            "WHERE user_id = :user_id AND item_id = :item_id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BidDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Bid> findAll(int itemId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("item_id", itemId);
        return namedParameterJdbcTemplate.query(
                SELECT_ALL,
                namedParameters,
                new BidRowMapper()
        );
    }

    @Override
    public void create(Bid bid) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", bid.getBuyer().getUserId());
        namedParameters.addValue("item_id", bid.getAuction().getItemId());
        namedParameters.addValue("bid_date", bid.getBidDate());
        namedParameters.addValue("bid_amount", bid.getBidAmount());
        namedParameterJdbcTemplate.update(INSERT_INTO, namedParameters);
    }

    @Override
    public void delete(int userId, int itemId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", userId);
        namedParameters.addValue("item_id", itemId);
        namedParameterJdbcTemplate.update(DELETE, namedParameters);
    }

    public static class BidRowMapper implements RowMapper<Bid> {
        @Override
        public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
            Bid bid = new Bid();
            bid.setBidDate(rs.getTimestamp("b.bid_date").toLocalDateTime());
            bid.setBidAmount(rs.getInt("b.bid_amount"));

            User user = new User();
            user.setUserId(rs.getInt("b.user_id"));
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
            bid.setBuyer(user);

            SoldItem soldItem = new SoldItem();
            soldItem.setItemId(rs.getInt("b.item_id"));
            soldItem.setItemName(rs.getString("si.item_name"));
            soldItem.setDescription(rs.getString("si.description"));
            soldItem.setStartAuctionDate(rs.getDate("si.start_auction_date").toLocalDate());
            soldItem.setEndAuctionDate(rs.getDate("si.end_auction_date").toLocalDate());
            soldItem.setInitialPrice(rs.getInt("si.initial_price"));
            soldItem.setSalePrice(rs.getInt("si.sale_price"));
            bid.setAuction(soldItem);

            return bid;
        }
    }
}
