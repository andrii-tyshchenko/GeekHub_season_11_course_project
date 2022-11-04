package org.geekhub.andrij.course_project.repositories;

import org.geekhub.andrij.course_project.entities.AmountBySection;
import org.geekhub.andrij.course_project.entities.Payment;
import org.geekhub.andrij.course_project.repositories.mappers.AmountBySectionRowMapper;
import org.geekhub.andrij.course_project.repositories.mappers.PaymentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<Payment> getAll() {
        String sql =
                "SELECT p.id, " +
                       "p.user_id, " +
                       "date_of_submit, " +
                       "u_i.last_name, " +
                       "u_i.first_name, " +
                       "u_i.patronymic, " +
                       "amount " +
                "FROM payments p " +
                     "LEFT JOIN users_info u_i ON p.user_id = u_i.user_id " +
                "ORDER BY date_of_submit DESC";

        return jdbcTemplate.query(sql, new PaymentRowMapper());
    }

    public List<Payment> getAllForUserId(int id) {
        String sql =
                "SELECT p.id, " +
                       "p.user_id, " +
                       "date_of_submit, " +
                       "u_i.last_name, " +
                       "u_i.first_name, " +
                       "u_i.patronymic, " +
                       "amount " +
                "FROM payments p " +
                     "LEFT JOIN users_info u_i ON p.user_id = u_i.user_id " +
                "WHERE p.user_id = " + id + " " +
                "ORDER BY date_of_submit DESC";

        return jdbcTemplate.query(sql, new PaymentRowMapper());
    }

    public LocalDateTime getFirstPaymentDate() {
        String sql =
                "SELECT date_of_submit " +
                "FROM payments " +
                "ORDER BY date_of_submit " +
                "LIMIT 1";

        List<LocalDateTime> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getObject("date_of_submit", LocalDateTime.class));

        return result.isEmpty() ? null : result.get(0);
    }

    public void makePaymentForUser(int id, Double amount) {
        String sql =
                "INSERT INTO payments(user_id, date_of_submit, amount) " +
                "VALUES (:id, :dateOfSubmit, :amount)";

        Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("id", id);
        namedParameters.put("dateOfSubmit", LocalDateTime.now());
        namedParameters.put("amount", amount);

        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public List<AmountBySection> getMonthlyPaymentsBySection(LocalDate fromDate) {
        String sql =
                "SELECT section, " +
                       "ROUND(COALESCE(SUM(amount), 0)::numeric, 2) AS amount " +
                "FROM apartments a " +
                     "LEFT JOIN users_info u_i ON a.id = u_i.apartment_id " +
                     "LEFT JOIN (SELECT * " +
                                "FROM payments " +
                                "WHERE date_of_submit BETWEEN :fromDate AND :toDate) AS p ON p.user_id = u_i.user_id " +
                "GROUP BY section " +
                "ORDER BY section";

        Map<String, LocalDate> namedParameters = new HashMap<>();
        namedParameters.put("fromDate", fromDate);
        namedParameters.put("toDate", fromDate.plusMonths(1));

        return namedParameterJdbcTemplate.query(sql, namedParameters, new AmountBySectionRowMapper());
    }

    public Double getMonthlyPaymentOfBuilding(LocalDate fromDate) {
        String sql =
                "SELECT ROUND(COALESCE(SUM(amount), 0)::numeric, 2) " +
                "FROM apartments a " +
                     "LEFT JOIN users_info u_i ON a.id = u_i.apartment_id " +
                     "LEFT JOIN payments p ON p.user_id = u_i.user_id " +
                "WHERE date_of_submit BETWEEN :fromDate AND :toDate";

        Map<String, LocalDate> namedParameters = new HashMap<>();
        namedParameters.put("fromDate", fromDate);
        namedParameters.put("toDate", fromDate.plusMonths(1));

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Double.class);
    }
}