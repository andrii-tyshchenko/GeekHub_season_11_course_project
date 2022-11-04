package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.AutoAccrualStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AutoAccrualStatusRowMapper implements RowMapper<AutoAccrualStatus> {
    @Override
    public AutoAccrualStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        AutoAccrualStatus autoAccrualStatus = new AutoAccrualStatus(
                rs.getObject("id", Long.class),
                rs.getObject("enabled", Boolean.class),
                rs.getObject("date_of_change", LocalDateTime.class),
                rs.getString("service_name"),
                rs.getObject("tariff", Double.class)
        );

        return autoAccrualStatus;
    }
}