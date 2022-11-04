package org.geekhub.andrij.course_project.entities;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public CurrentUser(User user) {
        super(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList(user.getAuthority())
        );
        this.user = user;
    }

    public Integer getId() {
        return user.getId();
    }

    public String getAuthority() {
        return user.getAuthority();
    }
}