package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.Accrual;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AccrualRowMapper implements RowMapper<Accrual> {
    @Override
    public Accrual mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accrual accrual = new Accrual(
                rs.getObject("id", Long.class),
                rs.getObject("user_id", Integer.class),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic"),
                rs.getObject("date_of_submit", LocalDateTime.class),
                rs.getString("service_name"),
                rs.getObject("tariff", Double.class),
                rs.getObject("sum_of_accrual", Double.class)
        );

        return accrual;
    }
}