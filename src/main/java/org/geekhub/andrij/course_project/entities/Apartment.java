package org.geekhub.andrij.course_project.entities;

import javax.validation.constraints.DecimalMin;

public class Apartment {
    private final Integer id;
    private final String street;
    private final Integer building;
    private final Integer section;
    private final Integer floor;
    private final Integer apartmentNumber;
    @DecimalMin(value = "0.0", message = "should be greater than 0")
    private final Double square;
    private final Integer accountNumber;

    public Apartment(Integer id, String street, Integer building, Integer section, Integer floor,
                     Integer apartmentNumber, Double square, Integer accountNumber) {
        this.id = id;
        this.street = street;
        this.building = building;
        this.section = section;
        this.floor = floor;
        this.apartmentNumber = apartmentNumber;
        this.square = square;
        this.accountNumber = accountNumber;
    }

    public Integer getId() {
        return id;
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

    public Integer getFloor() {
        return floor;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public Double getSquare() {
        return square;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }
}