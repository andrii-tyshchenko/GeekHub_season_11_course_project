package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.Apartment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApartmentRowMapper implements RowMapper<Apartment> {
    @Override
    public Apartment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Apartment apartment = new Apartment(
                rs.getObject("id", Integer.class),
                rs.getString("street"),
                rs.getObject("building", Integer.class),
                rs.getObject("section", Integer.class),
                rs.getObject("floor", Integer.class),
                rs.getObject("apartment_number", Integer.class),
                rs.getObject("square", Double.class),
                rs.getObject("account_number", Integer.class)
        );

        return apartment;
    }
}