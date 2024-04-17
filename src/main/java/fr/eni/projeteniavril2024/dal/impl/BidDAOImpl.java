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
