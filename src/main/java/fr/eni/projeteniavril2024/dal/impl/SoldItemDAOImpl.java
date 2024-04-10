package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
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
            "       c.label " +
            "FROM SOLD_ITEMS AS si " +
            "   INNER JOIN CATEGORIES AS c " +
            "       ON si.category_id = c.category_id;";
    private static final String SELECT_BY_ID = "" +
            "SELECT si.item_id, si.item_name, si.description, si.start_auction_date, si.end_auction_date, si.initial_price, si.sale_price, si.user_id, si.category_id, " +
            "       c.label " +
            "FROM SOLD_ITEMS AS si " +
            "   INNER JOIN CATEGORIES AS c " +
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

            return soldItem;
        }
    }
}
