package com.william.spring.jpa.h2.model;

import javax.persistence.*;

@Table(name = "household")
@Entity
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "housingType", nullable = false)
    private HousingType housingType;

    @Column(name = "householdSize")
    private int householdSize;

    @Column(name = ("totalIncome"))
    private int totalIncome;

    public Household() {
    }

    public Household(HousingType housingType, int householdSize, int totalIncome) {
        this.housingType = housingType;
        this.householdSize = householdSize;
        this.totalIncome = totalIncome;
    }

    public Long getId() {
        return id;
    }

    public HousingType getHousingType() {
        return housingType;
    }

    public void setHousingType(HousingType housingType) {
        this.housingType = housingType;
    }

    public int getHouseholdSize() {
        return householdSize;
    }

    public void setHouseholdSize(int householdSize) {
        this.householdSize = householdSize;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "Household{" +
                "id=" + id +
                ", housingType='" + housingType + '\'' +
                ", householdSize=" + householdSize +
                ", totalIncome=" + totalIncome +
                '}';
    }
}