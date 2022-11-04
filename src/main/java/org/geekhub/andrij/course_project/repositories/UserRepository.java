package org.geekhub.andrij.course_project.repositories;

import org.geekhub.andrij.course_project.entities.User;
import org.geekhub.andrij.course_project.entities.UserInfo;
import org.geekhub.andrij.course_project.entities.UserSidebarInfo;
import org.geekhub.andrij.course_project.repositories.mappers.UserInfoRowMapper;
import org.geekhub.andrij.course_project.repositories.mappers.UserRowMapper;
import org.geekhub.andrij.course_project.repositories.mappers.UserSidebarInfoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public User findByEmail(String email) {
        String sql =
                "SELECT * " +
                "FROM users " +
                "WHERE email = :email";

        Map<String, String> namedParameters = Map.of("email", email);

        List<User> users = namedParameterJdbcTemplate.query(sql, namedParameters, new UserRowMapper());

        return users.isEmpty() ? null : users.get(0);
    }

    public void addUser(UserInfo userInfo) {
        String sqlForUsers =
                "INSERT INTO users(email, password, authority, enabled) " +
                "VALUES (:email, :password, :authority, :enabled) " +
                "RETURNING id";

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userInfo.getPassword());

        Map<String, Object> paramsForUser = new HashMap<>();

        paramsForUser.put("email", userInfo.getEmail());
        paramsForUser.put("password", hashedPassword);
        paramsForUser.put("authority", userInfo.getAuthority());
        paramsForUser.put("enabled", userInfo.isEnabled());

        Integer userId = namedParameterJdbcTemplate.queryForObject(sqlForUsers, paramsForUser, Integer.class);

        String sqlForUserInfo =
                "INSERT INTO users_info(user_id, apartment_id, last_name, first_name, patronymic) " +
                "VALUES (:userId, :apartmentId, :lastName, :firstName, :patronymic)";

        Map<String, Object> paramsForUserInfo = new HashMap<>();

        paramsForUserInfo.put("userId", userId);
        paramsForUserInfo.put("apartmentId", userInfo.getApartmentId());
        paramsForUserInfo.put("lastName", userInfo.getLastName());
        paramsForUserInfo.put("firstName", userInfo.getFirstName());
        paramsForUserInfo.put("patronymic", userInfo.getPatronymic());

        namedParameterJdbcTemplate.update(sqlForUserInfo, paramsForUserInfo);
    }

    public List<UserInfo> getAll() {
        String sql =
                "SELECT * " +
                "FROM users u " +
                     "LEFT JOIN users_info u_i ON u.id = u_i.user_id " +
                "ORDER BY authority DESC, u.id";

        return jdbcTemplate.query(sql, new UserInfoRowMapper());
    }

    public UserInfo getUserInfoById(int id) {
        String sql =
                "SELECT * " +
                "FROM users u " +
                     "LEFT JOIN users_info u_i ON u.id = u_i.user_id " +
                "WHERE u.id = " + id;

        List<UserInfo> users = jdbcTemplate.query(sql, new UserInfoRowMapper());

        return users.isEmpty() ? null : users.get(0);
    }

    public List<Integer> getAllEnabledNonAdminUsersId() {
        String sql =
                "SELECT id " +
                "FROM users " +
                "WHERE authority != 'ROLE_ADMIN' " +
                      "AND enabled = 'true'";

        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    public Integer getCustomersCount() {
        String sql =
                "SELECT COUNT(*) " +
                "FROM users " +
                "WHERE authority != 'ROLE_ADMIN'";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public UserSidebarInfo getUserSidebarInfo(int userId) {
        String sql =
                "SELECT a_i.account_number, " +
                       "u_i.last_name, " +
                       "u_i.first_name, " +
                       "u_i.patronymic, " +
                       "str.name AS street, " +
                       "b_a.building, " +
                       "a.section, " +
                       "a.apartment_number " +
                "FROM users u " +
                     "LEFT JOIN users_info u_i ON u.id = u_i.user_id " +
                     "LEFT JOIN apartments a ON u_i.apartment_id = a.id " +
                     "LEFT JOIN building_addresses b_a ON a.building_address_id = b_a.id " +
                     "LEFT JOIN streets str ON b_a.street_id = str.id " +
                     "LEFT JOIN apartments_info a_i ON a.id = a_i.apartment_id " +
                "WHERE u.id = " + userId;

        List<UserSidebarInfo> userSidebarInfos = jdbcTemplate.query(sql, new UserSidebarInfoRowMapper());

        return userSidebarInfos.isEmpty() ? null : userSidebarInfos.get(0);
    }

    public void updateUserAndUserInfo(UserInfo userInfo) {
        String sqlForUsers =
                "UPDATE users " +
                "SET email = :email, " +
                    "authority = :authority, " +
                    "enabled = :enabled " +
                "WHERE id = :userId";

        Integer userId = userInfo.getId();

        Map<String, Object> paramsForUsers = new HashMap<>();

        paramsForUsers.put("email", userInfo.getEmail());
        paramsForUsers.put("authority", userInfo.getAuthority());
        paramsForUsers.put("enabled", userInfo.isEnabled());
        paramsForUsers.put("userId", userId);

        namedParameterJdbcTemplate.update(sqlForUsers, paramsForUsers);

        String sqlForUsersInfo =
                "UPDATE users_info " +
                "SET apartment_id = :apartmentId, " +
                    "last_name = :lastName, " +
                    "first_name = :firstName, " +
                    "patronymic = :patronymic " +
                "WHERE user_id = :userId";

        Map<String, Object> paramsForUsersInfo = new HashMap<>();

        paramsForUsersInfo.put("apartmentId", userInfo.getApartmentId());
        paramsForUsersInfo.put("lastName", userInfo.getLastName());
        paramsForUsersInfo.put("firstName", userInfo.getFirstName());
        paramsForUsersInfo.put("patronymic", userInfo.getPatronymic());
        paramsForUsersInfo.put("userId", userId);

        namedParameterJdbcTemplate.update(sqlForUsersInfo, paramsForUsersInfo);
    }

    public void updateUserEmailAndName(int userId, String email, UserInfo userInfo) {
        String sqlForUserEmail =
                "UPDATE users " +
                "SET email = :email " +
                "WHERE id = " + userId;

        Map<String, String> paramsForUserEmail = Map.of("email", email);

        namedParameterJdbcTemplate.update(sqlForUserEmail, paramsForUserEmail);

        String sqlForUserInfo =
                "UPDATE users_info " +
                "SET last_name = :lastName, " +
                    "first_name = :firstName, " +
                    "patronymic = :patronymic " +
                "WHERE user_id = " + userId;

        Map<String,String> paramsForUserInfo = new HashMap<>();

        paramsForUserInfo.put("lastName", userInfo.getLastName());
        paramsForUserInfo.put("firstName", userInfo.getFirstName());
        paramsForUserInfo.put("patronymic", userInfo.getPatronymic());

        namedParameterJdbcTemplate.update(sqlForUserInfo, paramsForUserInfo);
    }

    public void updateUserPassword(int userId, String password) {
        String sql =
                "UPDATE USERS " +
                "SET password = '" + password + "' " +
                "WHERE id = " + userId;

        jdbcTemplate.update(sql);
    }
}