package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getObject("id", Integer.class),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("authority"),
                rs.getBoolean("enabled")
        );

        return user;
    }
}