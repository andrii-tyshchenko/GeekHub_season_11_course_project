package org.geekhub.andrij.course_project.entities;

import javax.validation.constraints.*;

public class User {
    private final Integer id;

    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "email address has incorrect format (example of valid address - JohnDoe@mail.com)")
    @Size(min = 5, max = 256, message = "should be between 5 and 256 characters")
    private final String email;

    @Size(min = 8, message = "should be at least 8 characters long")
    private final String password;

    private final String authority;
    private final Boolean isEnabled;

    public User(Integer id, String email, String password, String authority, Boolean isEnabled) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.isEnabled = isEnabled;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthority() {
        return authority;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }
}