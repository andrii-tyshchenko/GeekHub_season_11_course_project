package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.DebtorInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DebtorInfoRowMapper implements RowMapper<DebtorInfo> {
    @Override
    public DebtorInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        DebtorInfo debtorInfo = new DebtorInfo(
                rs.getObject("id", Integer.class),
                rs.getObject("account_number", Integer.class),
                rs.getString("street"),
                rs.getObject("building", Integer.class),
                rs.getObject("section", Integer.class),
                rs.getObject("apartment_number", Integer.class),
                rs.getString("email"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic"),
                rs.getDouble("user_payments"),
                rs.getDouble("user_accruals"),
                rs.getDouble("debt")
        );

        return debtorInfo;
    }
}