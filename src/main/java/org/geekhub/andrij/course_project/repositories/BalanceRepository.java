package org.geekhub.andrij.course_project.repositories;

import org.geekhub.andrij.course_project.entities.DebtorInfo;
import org.geekhub.andrij.course_project.repositories.mappers.DebtorInfoRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BalanceRepository {
    private final JdbcTemplate jdbcTemplate;

    public BalanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Double getOSBBBalance() {
        String sql =
                "SELECT ROUND((COALESCE(SUM(amount), 0) - COALESCE((SELECT SUM(sum_of_accrual) " +
                                                                   "FROM accruals_info), 0))::numeric, 2) " +
                "FROM payments";

        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    public List<DebtorInfo> getTopDebtors(Integer quantity) {
        String sql =
                "WITH p AS (SELECT user_id, " +
                                  "SUM(amount) " +
                           "FROM payments " +
                           "GROUP BY user_id), " +
                     "ac AS (SELECT user_id, " +
                                   "SUM(sum_of_accrual) " +
                            "FROM accruals ac " +
                                 "LEFT JOIN accruals_info ac_i ON ac.id = ac_i.accrual_id " +
                            "GROUP BY user_id) " +
                "SELECT u.id, " +
                       "a_i.account_number, " +
                       "str.name AS street, " +
                       "b_a.building, " +
                       "a.section, " +
                       "a.apartment_number, " +
                       "u.email, " +
                       "u_i.last_name, " +
                       "u_i.first_name, " +
                       "u_i.patronymic, " +
                       "COALESCE(ROUND(p.sum::numeric, 2), 0) AS user_payments, " +
                       "COALESCE(ROUND(ac.sum::numeric, 2), 0) AS user_accruals, " +
                       "ROUND((COALESCE(p.sum, 0) - COALESCE(ac.sum, 0))::numeric, 2) as debt " +
                "FROM users u " +
                     "LEFT JOIN users_info u_i ON u.id = u_i.user_id " +
                     "LEFT JOIN apartments a ON u_i.apartment_id = a.id " +
                     "LEFT JOIN building_addresses b_a ON a.building_address_id = b_a.id " +
                     "LEFT JOIN streets str ON b_a.street_id = str.id " +
                     "LEFT JOIN apartments_info a_i ON a.id = a_i.apartment_id " +
                     "LEFT JOIN p ON u.id = p.user_id " +
                     "LEFT JOIN ac ON u.id = ac.user_id " +
                "WHERE ROUND((COALESCE(p.sum, 0) - COALESCE(ac.sum, 0))::numeric, 2) < 0 " +
                "ORDER BY debt " +
                "LIMIT " + quantity;

        return jdbcTemplate.query(sql, new DebtorInfoRowMapper());
    }

    public Double getUserDebtByUserId(int id) {
        String sql =
                "WITH p AS (SELECT user_id, " +
                                  "SUM(amount) " +
                           "FROM payments " +
                           "GROUP BY user_id), " +
                     "ac AS (SELECT user_id, " +
                                   "SUM(sum_of_accrual) " +
                            "FROM accruals ac " +
                                 "LEFT JOIN accruals_info ac_i ON ac.id = ac_i.accrual_id " +
                            "GROUP BY user_id) " +
                "SELECT ROUND((COALESCE(p.sum, 0) - COALESCE(ac.sum, 0))::numeric, 2) as debt " +
                "FROM users u " +
                     "LEFT JOIN p ON u.id = p.user_id " +
                     "LEFT JOIN ac ON u.id = ac.user_id " +
                "WHERE u.id = " + id;

        return jdbcTemplate.queryForObject(sql, Double.class);
    }
}