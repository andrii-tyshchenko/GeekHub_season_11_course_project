package org.geekhub.andrij.course_project.entities;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Accrual {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private final Long id;
    private final Integer userId;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final LocalDateTime dateOfSubmit;
    @NotBlank(message = "shouldn't be blank")
    @Size(min = 3, max = 64, message = "should be between 3 and 64 characters")
    private final String serviceName;
    @Min(value = 0, message = "should be greater than 0")
    private final Double tariff;
    private final Double sumOfAccrual;

    public Accrual(Long id,
                   Integer userId,
                   String lastName,
                   String firstName,
                   String patronymic,
                   LocalDateTime dateOfSubmit,
                   String serviceName,
                   Double tariff,
                   Double sumOfAccrual) {
        this.id = id;
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.dateOfSubmit = dateOfSubmit;
        this.serviceName = serviceName;
        this.tariff = tariff;
        this.sumOfAccrual = sumOfAccrual;
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

    public String getServiceName() {
        return serviceName;
    }

    public Double getTariff() {
        return tariff;
    }

    public Double getSumOfAccrual() {
        return sumOfAccrual;
    }
}