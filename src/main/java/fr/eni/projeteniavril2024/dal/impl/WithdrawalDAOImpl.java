package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Withdrawal;
import fr.eni.projeteniavril2024.dal.WithdrawalDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class WithdrawalDAOImpl implements WithdrawalDAO {
    private static final String SELECT_BY_ITEM_ID = "SELECT item_id, street, postal_code, city FROM WITHDRAWALS WHERE item_id = :item_id;";

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
                SELECT_BY_ITEM_ID,
                namedParameters,
                new WithdrawalRowMapper()
        );
    }

    public static class WithdrawalRowMapper implements RowMapper<Withdrawal> {
        @Override
        public Withdrawal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setItemId(rs.getInt("item_id"));
            withdrawal.setStreet(rs.getString("street"));
            withdrawal.setPostalCode(rs.getString("postal_code"));
            withdrawal.setCity(rs.getString("city"));

            return withdrawal;
        }
    }
}
