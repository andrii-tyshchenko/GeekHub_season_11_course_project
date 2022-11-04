package org.geekhub.andrij.course_project.repositories;

import org.geekhub.andrij.course_project.entities.Accrual;
import org.geekhub.andrij.course_project.entities.AmountBySection;
import org.geekhub.andrij.course_project.entities.AutoAccrualStatus;
import org.geekhub.andrij.course_project.repositories.mappers.AccrualRowMapper;
import org.geekhub.andrij.course_project.repositories.mappers.AmountBySectionRowMapper;
import org.geekhub.andrij.course_project.repositories.mappers.AutoAccrualStatusRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccrualRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final UserRepository userRepository;

    public AccrualRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.userRepository = userRepository;
    }

    public LocalDateTime getLastAccrualDate() {
        String sql =
                "SELECT date_of_submit " +
                "FROM accruals " +
                "ORDER BY date_of_submit DESC " +
                "LIMIT 1";

        List<LocalDateTime> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getObject("date_of_submit", LocalDateTime.class));

        return result.isEmpty() ? null : result.get(0);
    }

    public LocalDateTime getFirstAccrualDate() {
        String sql =
                "SELECT date_of_submit " +
                "FROM accruals " +
                "ORDER BY date_of_submit " +
                "LIMIT 1";

        List<LocalDateTime> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getObject("date_of_submit", LocalDateTime.class));

        return result.isEmpty() ? null : result.get(0);
    }

    public Double getLastAccrualAmountForUserId(int id) {
        String sql =
                "SELECT SUM(sum_of_accrual) " +
                "FROM accruals a " +
                     "LEFT JOIN accruals_info ac_i ON a.id = ac_i.accrual_id " +
                "GROUP BY a.id " +
                "HAVING a.user_id = " + id + " " +
                "ORDER BY a.date_of_submit DESC " +
                "LIMIT 1";

        List<Double> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getObject("sum", Double.class));

        return result.isEmpty() ? null : result.get(0);
    }

    public Double getLastTariffValue() {
        String sql =
                "SELECT tariff " +
                "FROM accruals a " +
                "JOIN accruals_info a_i ON a.id = a_i.accrual_id " +
                "ORDER BY date_of_submit DESC " +
                "LIMIT 1";

        List<Double> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getObject("tariff", Double.class));

        return result.isEmpty() ? null : result.get(0);
    }

    public void addAccrualsAndAccrualsInfo(String serviceName, Double tariff) {
        List<Integer> userIds = userRepository.getAllEnabledNonAdminUsersId();

        String sqlForAccruals =
                "INSERT INTO accruals (user_id, date_of_submit) " +
                "VALUES (:user_id, :date_of_submit) " +
                "RETURNING id";

        String sqlForAccrualsInfo =
                "INSERT INTO accruals_info (accrual_id, service_name, tariff, sum_of_accrual) " +
                "VALUES (:accrual_id, " +
                        ":service_name, " +
                        ":tariff, " +
                        "ROUND(((SELECT square " +
                                "FROM users u " +
                                    "JOIN users_info u_i ON u.id = u_i.user_id " +
                                    "JOIN apartments_info a_i ON a_i.apartment_id = u_i.apartment_id " +
                                "WHERE u.id = :user_id) * :tariff)::numeric, 2))";

        for (int userId : userIds) {
            Map<String, Object> paramsForAccruals = new HashMap<>();
            paramsForAccruals.put("user_id", userId);
            paramsForAccruals.put("date_of_submit", LocalDateTime.now());

            Long accrualId = namedParameterJdbcTemplate.queryForObject(sqlForAccruals, paramsForAccruals, Long.class);

            Map<String, Object> paramsForAccrualsInfo = new HashMap<>();
            paramsForAccrualsInfo.put("accrual_id", accrualId);
            paramsForAccrualsInfo.put("service_name", serviceName);
            paramsForAccrualsInfo.put("tariff", tariff);
            paramsForAccrualsInfo.put("user_id", userId);

            namedParameterJdbcTemplate.update(sqlForAccrualsInfo, paramsForAccrualsInfo);
        }
    }

    public List<Accrual> getAllAccruals() {
        String sql =
                "SELECT ac.id, " +
                       "ac.user_id, " +
                       "u_i.last_name, " +
                       "u_i.first_name, " +
                       "u_i.patronymic, " +
                       "ac.date_of_submit, " +
                       "ac_i.service_name, " +
                       "ac_i.tariff, " +
                       "ac_i.sum_of_accrual " +
                "FROM accruals ac " +
                     "LEFT JOIN accruals_info ac_i ON ac.id = ac_i.accrual_id " +
                     "LEFT JOIN users_info u_i ON ac.user_id = u_i.user_id " +
                "ORDER BY date_of_submit DESC";

        return jdbcTemplate.query(sql, new AccrualRowMapper());
    }

    public List<Accrual> getAllAccrualsForUserId(int id) {
        String sql =
                "SELECT ac.id, " +
                       "ac.user_id, " +
                       "u_i.last_name, " +
                       "u_i.first_name, " +
                       "u_i.patronymic, " +
                       "ac.date_of_submit, " +
                       "ac_i.service_name, " +
                       "ac_i.tariff, " +
                       "ac_i.sum_of_accrual " +
                "FROM accruals ac " +
                     "LEFT JOIN accruals_info ac_i ON ac.id = ac_i.accrual_id " +
                     "LEFT JOIN users_info u_i ON ac.user_id = u_i.user_id " +
                "WHERE ac.user_id = " + id + " " +
                "ORDER BY date_of_submit DESC";

        return jdbcTemplate.query(sql, new AccrualRowMapper());
    }

    public List<Accrual> getLastAccrualForUserId(int id) {
        String sql =
                "SELECT ac.id, " +
                       "ac.user_id, " +
                       "u_i.last_name, " +
                       "u_i.first_name, " +
                       "u_i.patronymic, " +
                       "ac.date_of_submit, " +
                       "ac_i.service_name, " +
                       "ac_i.tariff, " +
                       "ac_i.sum_of_accrual " +
                "FROM accruals ac " +
                     "LEFT JOIN accruals_info ac_i ON ac.id = ac_i.accrual_id " +
                     "LEFT JOIN users_info u_i ON ac.user_id = u_i.user_id " +
                "WHERE ac.user_id = " + id + " " +
                "ORDER BY date_of_submit DESC " +
                "LIMIT 1";

        return jdbcTemplate.query(sql, new AccrualRowMapper());
    }

    public AutoAccrualStatus getLastAutoAccrualStatus() {
        String sql =
                "SELECT id, " +
                       "enabled, " +
                       "date_of_change, " +
                       "service_name, " +
                       "tariff " +
                "FROM autoaccruals_status " +
                "ORDER BY date_of_change DESC " +
                "LIMIT 1";

        List<AutoAccrualStatus> result = jdbcTemplate.query(sql, new AutoAccrualStatusRowMapper());

        return result.isEmpty() ? null : result.get(0);
    }

    public void addAutoAccrualStatus(AutoAccrualStatus autoAccrualStatus) {
        String sql =
                "INSERT INTO autoaccruals_status (enabled, date_of_change, service_name, tariff) " +
                "VALUES (:enabled, :dateOfChange, :serviceName, :tariff)";

        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("enabled", autoAccrualStatus.getEnabled());
        namedParameters.put("dateOfChange", LocalDateTime.now());
        namedParameters.put("serviceName", autoAccrualStatus.getServiceName());
        namedParameters.put("tariff", autoAccrualStatus.getTariff());

        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public List<AmountBySection> getMonthlyAccrualsBySection(LocalDate fromDate) {
        String sql =
                "SELECT section, " +
                       "ROUND(COALESCE(SUM(sum_of_accrual), 0)::numeric, 2) AS amount " +
                "FROM apartments a " +
                     "LEFT JOIN users_info u_i ON a.id = u_i.apartment_id " +
                     "LEFT JOIN (SELECT * " +
                                "FROM accruals " +
                                "WHERE date_of_submit BETWEEN :fromDate AND :toDate) AS acc ON acc.user_id = u_i.user_id " +
                     "LEFT JOIN accruals_info acc_i ON acc.id = acc_i.accrual_id " +
                "GROUP BY section " +
                "ORDER BY section";

        Map<String, LocalDate> namedParameters = new HashMap<>();
        namedParameters.put("fromDate", fromDate);
        namedParameters.put("toDate", fromDate.plusMonths(1));

        return namedParameterJdbcTemplate.query(sql, namedParameters, new AmountBySectionRowMapper());
    }

    public Double getMonthlyAccrualOfBuilding(LocalDate fromDate) {
        String sql =
                "SELECT ROUND(COALESCE(SUM(sum_of_accrual), 0)::numeric, 2) " +
                "FROM apartments a " +
                     "LEFT JOIN users_info u_i ON a.id = u_i.apartment_id " +
                     "LEFT JOIN accruals acc ON acc.user_id = u_i.user_id " +
                     "LEFT JOIN accruals_info acc_i ON acc.id = acc_i.accrual_id " +
                "WHERE date_of_submit BETWEEN :fromDate AND :toDate";

        Map<String, LocalDate> namedParameters = new HashMap<>();
        namedParameters.put("fromDate", fromDate);
        namedParameters.put("toDate", fromDate.plusMonths(1));

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Double.class);
    }
}