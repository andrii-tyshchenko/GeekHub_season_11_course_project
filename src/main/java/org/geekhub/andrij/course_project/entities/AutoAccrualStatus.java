package org.geekhub.andrij.course_project.entities;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class AutoAccrualStatus {
    private final Long id;
    private final Boolean isEnabled;
    private final LocalDateTime dateOfChange;
    @NotBlank(message = "shouldn't be blank")
    @Size(min = 3, max = 64, message = "should be between 3 and 64 characters")
    private final String serviceName;
    @Min(value = 0, message = "should be greater than 0")
    private final Double tariff;

    public AutoAccrualStatus(Long id, Boolean isEnabled, LocalDateTime dateOfChange, String serviceName, Double tariff) {
        this.id = id;
        this.isEnabled = isEnabled;
        this.dateOfChange = dateOfChange;
        this.serviceName = serviceName;
        this.tariff = tariff;
    }

    public Long getId() {
        return id;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public LocalDateTime getDateOfChange() {
        return dateOfChange;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Double getTariff() {
        return tariff;
    }
}