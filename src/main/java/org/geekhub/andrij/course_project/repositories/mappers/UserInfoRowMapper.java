package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.UserInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoRowMapper implements RowMapper<UserInfo> {
    @Override
    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInfo userInfo = new UserInfo(
                new UserRowMapper().mapRow(rs, rowNum),
                rs.getObject("apartment_id", Integer.class),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic")
        );

        return userInfo;
    }
}