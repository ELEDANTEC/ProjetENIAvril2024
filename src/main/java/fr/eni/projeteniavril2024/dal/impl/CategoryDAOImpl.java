package fr.eni.projeteniavril2024.dal.impl;

import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.dal.CategoryDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    private static final String SELECT_ALL = "SELECT category_id, label FROM CATEGORIES;";
    private static final String SELECT_BY_ID = "SELECT label FROM CATEGORIES WHERE category_id = :category_id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public CategoryDAOImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTemplate jdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query(
                SELECT_ALL,
                new BeanPropertyRowMapper<Category>(Category.class)
        );
    }

    @Override
    public Category findById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("category_id", id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                namedParameters,
                new BeanPropertyRowMapper<Category>(Category.class)
        );
    }
}
