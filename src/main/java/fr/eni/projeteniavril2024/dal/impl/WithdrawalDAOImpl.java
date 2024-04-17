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
    private static final String INSERT_INTO = "" +
            "INSERT INTO WITHDRAWALS " +
            "       (item_id, street, postal_code, city) " +
            "   VALUES " +
            "       (:item_id, :street, :postal_code, :city);";

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

    @Override
    public void create(Withdrawal withdrawal) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", withdrawal.getItemId());
        namedParameters.addValue("street", withdrawal.getStreet());
        namedParameters.addValue("postal_code", withdrawal.getPostalCode());
        namedParameters.addValue("city", withdrawal.getCity());
        namedParameterJdbcTemplate.update(INSERT_INTO, namedParameters);
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
