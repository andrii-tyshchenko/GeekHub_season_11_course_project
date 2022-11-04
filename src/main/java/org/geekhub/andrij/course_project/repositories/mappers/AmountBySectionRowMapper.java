package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.AmountBySection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AmountBySectionRowMapper implements RowMapper<AmountBySection> {
    @Override
    public AmountBySection mapRow(ResultSet rs, int rowNum) throws SQLException {
        AmountBySection amountBySection = new AmountBySection(
                rs.getObject("section", Integer.class),
                rs.getDouble("amount")
        );

        return amountBySection;
    }
}