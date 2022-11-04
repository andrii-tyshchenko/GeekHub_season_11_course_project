package org.geekhub.andrij.course_project.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private final Long id;
    private final Integer userId;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final LocalDateTime dateOfSubmit;
    private final Double amount;

    public Payment(Long id,
                   Integer userId,
                   String lastName,
                   String firstName,
                   String patronymic,
                   LocalDateTime dateOfSubmit,
                   Double amount) {
        this.id = id;
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.dateOfSubmit = dateOfSubmit;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
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

    public LocalDateTime getDateOfSubmit() {
        return dateOfSubmit;
    }

    public String getFormattedDateOfSubmit() {
        return dateOfSubmit.format(formatter);
    }

    public Double getAmount() {
        return amount;
    }
}