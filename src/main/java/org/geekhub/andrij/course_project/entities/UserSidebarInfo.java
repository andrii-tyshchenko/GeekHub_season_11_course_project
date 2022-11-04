package org.geekhub.andrij.course_project.entities;

public class UserSidebarInfo {
    private final Integer accountNumber;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final String street;
    private final Integer building;
    private final Integer section;
    private final Integer apartmentNumber;

    public UserSidebarInfo(Integer accountNumber,
                           String lastName,
                           String firstName,
                           String patronymic,
                           String street,
                           Integer building,
                           Integer section,
                           Integer apartmentNumber) {
        this.accountNumber = accountNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.street = street;
        this.building = building;
        this.section = section;
        this.apartmentNumber = apartmentNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
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
}