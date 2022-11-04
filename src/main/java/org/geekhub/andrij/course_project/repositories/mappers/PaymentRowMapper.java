package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.Payment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PaymentRowMapper implements RowMapper<Payment> {
    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Payment payment = new Payment(
                rs.getObject("id", Long.class),
                rs.getObject("user_id", Integer.class),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic"),
                rs.getObject("date_of_submit", LocalDateTime.class),
                rs.getObject("amount", Double.class)
        );

        return payment;
    }
}