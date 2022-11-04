package org.geekhub.andrij.course_project.entities;

public class DebtorInfo {
    private final Integer userId;
    private final Integer accountNumber;
    private final String street;
    private final Integer building;
    private final Integer section;
    private final Integer apartmentNumber;
    private final String email;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final Double userPayments;
    private final Double userAccruals;
    private final Double debt;

    public DebtorInfo(Integer userId,
                      Integer accountNumber,
                      String street,
                      Integer building,
                      Integer section,
                      Integer apartmentNumber,
                      String email,
                      String lastName,
                      String firstName,
                      String patronymic,
                      Double userPayments,
                      Double userAccruals,
                      Double debt) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.street = street;
        this.building = building;
        this.section = section;
        this.apartmentNumber = apartmentNumber;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.userPayments = userPayments;
        this.userAccruals = userAccruals;
        this.debt = debt;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getStreet() {
        return street;
    }

    public Integer getBuilding() {
        return building;
    }

    public Integer getSection() {
        return section;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public String getEmail() {
        return email;
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

    public Double getUserPayments() {
        return userPayments;
    }

    public Double getUserAccruals() {
        return userAccruals;
    }

    public Double getDebt() {
        return debt;
    }
}