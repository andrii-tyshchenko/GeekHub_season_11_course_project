package org.geekhub.andrij.course_project.entities;

import javax.validation.constraints.Min;

public class UserInfo {
    private User user;
    @Min(value = 1, message = "should be greater than 0")
    private final Integer apartmentId;
    private final String lastName;
    private final String firstName;
    private final String patronymic;

    public UserInfo(User user, Integer apartmentId, String lastName, String firstName, String patronymic) {
        this.user = user;
        this.apartmentId = apartmentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public Integer getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getAuthority() {
        return user.getAuthority();
    }

    public boolean isEnabled() {
        return user.isEnabled();
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setUser(User user) {
        this.user = user;
    }
}