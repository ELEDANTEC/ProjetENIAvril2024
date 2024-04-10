package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.dal.SoldItemDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SoldItemDAOImpl implements SoldItemDAO {
    private static final String SELECT_ALL = "SELECT item_id, item_name, description, start_auction_date, end_auction_date, initial_price, sale_price, user_id, category_id FROM SOLD_ITEMS;";
    private static final String SELECT_BY_ID = "SELECT item_id, item_name, description, start_auction_date, end_auction_date, initial_price, sale_price, user_id, category_id FROM SOLD_ITEMS WHERE item_id = :item_id";

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
                new BeanPropertyRowMapper<SoldItem>(SoldItem.class)
        );
    }

    @Override
    public SoldItem findById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("item_id", id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                namedParameters,
                new BeanPropertyRowMapper<SoldItem>(SoldItem.class)
        );
    }
}
