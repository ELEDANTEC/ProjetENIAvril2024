package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Withdrawal;
import fr.eni.projeteniavril2024.dal.WithdrawalDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WithdrawalDAOImpl implements WithdrawalDAO {
    private static final String SELECT_BY_ID = "SELECT item_id, street, postal_code, city FROM WITHDRAWALS WHERE item_id = :item_id;";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WithdrawalDAOImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Withdrawal findByItemId(int itemId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("item_id", itemId);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                namedParameters,
                new BeanPropertyRowMapper<Withdrawal>(Withdrawal.class)
        );
    }
}
