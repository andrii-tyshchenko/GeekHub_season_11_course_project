package org.geekhub.andrij.course_project.repositories.mappers;

import org.geekhub.andrij.course_project.entities.UserSidebarInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSidebarInfoRowMapper implements RowMapper<UserSidebarInfo> {
    @Override
    public UserSidebarInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserSidebarInfo userSidebarInfo = new UserSidebarInfo(
                rs.getObject("account_number", Integer.class),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("patronymic"),
                rs.getString("street"),
                rs.getObject("building", Integer.class),
                rs.getObject("section", Integer.class),
                rs.getObject("apartment_number", Integer.class)
        );

        return userSidebarInfo;
    }
}