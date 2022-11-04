package org.geekhub.andrij.course_project.repositories;

import org.geekhub.andrij.course_project.entities.Apartment;
import org.geekhub.andrij.course_project.repositories.mappers.ApartmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ApartmentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ApartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<Apartment> getAll() {
        String sql =
                "SELECT a.id, " +
                       "str.name AS street, " +
                       "b_a.building, " +
                       "a.section, " +
                       "a.floor, " +
                       "a.apartment_number, " +
                       "a_i.square, " +
                       "a_i.account_number " +
                "FROM apartments a " +
                     "LEFT JOIN building_addresses b_a ON a.building_address_id = b_a.id " +
                     "LEFT JOIN streets str ON b_a.street_id = str.id " +
                     "LEFT JOIN apartments_info a_i ON a.id = a_i.apartment_id " +
                "ORDER BY a.id";

        return jdbcTemplate.query(sql, new ApartmentRowMapper());
    }

    public Integer getApartmentsCount() {
        String sql =
                "SELECT COUNT(*) " +
                "FROM apartments";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void updateSquare(Integer apartmentId, Double square) {
        String sql =
                "UPDATE apartments_info " +
                "SET square = :square " +
                "WHERE apartment_id = :apartmentId";

        Map<String, Number> namedParameters = new HashMap<>();

        namedParameters.put("apartmentId", apartmentId);
        namedParameters.put("square", square);

        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public Boolean isAccountNumberExist(int accountNumber) {
        String sql =
                "SELECT COUNT(account_number) > 0 " +
                "FROM apartments_info " +
                "WHERE account_number = :accountNumber";

        Map<String, Integer> namedParameters = Map.of("accountNumber", accountNumber);

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Boolean.class);
    }

    public Boolean isAccountNumberHasUsers(int accountNumber) {
        String sql =
                "SELECT COUNT(user_id) > 0 " +
                "FROM apartments_info a_i " +
                     "JOIN users_info u_i ON a_i.apartment_id = u_i.apartment_id " +
                "WHERE account_number = :accountNumber";

        Map<String, Integer> namedParameters = Map.of("accountNumber", accountNumber);

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Boolean.class);
    }

    public Integer getApartmentIdByAccountNumber(int accountNumber) {
        String sql =
                "SELECT apartment_id " +
                "FROM apartments_info " +
                "WHERE account_number = :accountNumber";

        Map<String, Integer> namedParameters = Map.of("accountNumber", accountNumber);

        List<Integer> result = namedParameterJdbcTemplate.query(sql, namedParameters, (rs, rowNum) -> rs.getObject("apartment_id", Integer.class));

        return result.isEmpty() ? null : result.get(0);
    }
}