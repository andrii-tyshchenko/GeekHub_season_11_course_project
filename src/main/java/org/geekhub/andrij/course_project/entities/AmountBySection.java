package org.geekhub.andrij.course_project.entities;

public class AmountBySection {
    private final Integer section;
    private final Double amount;

    public AmountBySection(Integer section, Double amount) {
        this.section = section;
        this.amount = amount;
    }

    public Integer getSection() {
        return section;
    }

    public Double getAmount() {
        return amount;
    }
}