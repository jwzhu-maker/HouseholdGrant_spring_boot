package com.william.spring.jpa.h2.model;

import javax.persistence.*;

@Entity
@Table(name = "familyMember")
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // value only: F, M, U
    @Column(name = "gender")
    private String gender;

    @Column(name = "maritalStatus")
    // value only: M, U, S, W, N(not disclosed)
    private String maritalStatus;

    @Column(name = "spouseId")
    private Long spouseId;  // refer to same familyMember table

    @Enumerated(EnumType.STRING)
    @Column(name = "occupationType")
    private OccupationType occupationType;

    @Column(name = "annualIncome")
    private int annualIncome;

    @Column(name="dob")
    private String dob;

    @Column(name="householdId")
    private long householdId;

    public FamilyMember() {
    }

    public FamilyMember(String name, String gender, String maritalStatus, Long spouseId, OccupationType occupationType, int annualIncome, String dob, long householdId) {
        this.name = name;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.spouseId = spouseId;
        this.occupationType = occupationType;
        this.annualIncome = annualIncome;
        this.dob = dob;
        this.householdId = householdId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Long getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(Long spouseId) {
        this.spouseId = spouseId;
    }

    public OccupationType getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(OccupationType occupationType) {
        this.occupationType = occupationType;
    }

    public int getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(int annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(long householdId) {
        this.householdId = householdId;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", spouseId=" + spouseId +
                ", occupationType=" + occupationType +
                ", annualIncome=" + annualIncome +
                ", dob='" + dob + '\'' +
                ", householdId=" + householdId +
                '}';
    }

}

