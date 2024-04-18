package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Bid;
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
            "SELECT user_id, item_id, bid_date, bid_amount " +
            "FROM BIDS " +
            "WHERE item_id = :item_id;";
    private static final String INSERT_INTO = "" +
            "INSERT INTO BIDS " +
            "       (user_id, item_id, bid_date, bid_amount) " +
            "   VALUES " +
            "       (:user_id, :item_id, :bid_date, :bid_amount);";

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
        namedParameters.addValue("user_id", bid.getUserId());
        namedParameters.addValue("item_id", bid.getItemId());
        namedParameters.addValue("bid_date", bid.getBidDate());
        namedParameters.addValue("bid_amount", bid.getBidAmount());
        namedParameterJdbcTemplate.update(INSERT_INTO, namedParameters);
    }

    public static class BidRowMapper implements RowMapper<Bid> {
        @Override
        public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
            Bid bid = new Bid();
            bid.setUserId(rs.getInt("user_id"));
            bid.setItemId(rs.getInt("item_id"));
            bid.setBidDate(rs.getTimestamp("bid_date").toLocalDateTime());
            bid.setBidAmount(rs.getInt("bid_amount"));

            return bid;
        }
    }
}
