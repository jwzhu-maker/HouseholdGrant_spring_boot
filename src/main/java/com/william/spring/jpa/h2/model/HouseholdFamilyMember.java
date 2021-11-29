package com.william.spring.jpa.h2.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "householdFamilyMember")
public class HouseholdFamilyMember {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "familyMemberId", nullable = false)
    private Long familyMemberId;

    @Column(name = "lastModifiedDate", nullable = false)
    private Instant lastModifiedDate = Instant.now();

    public HouseholdFamilyMember() {
    }

    public HouseholdFamilyMember(Long id, Long familyMemberId, Instant lastModifiedDate) {
        this.id = id;
        this.familyMemberId = familyMemberId;
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "Household{" +
                "id=" + id +
                ", familyMemberId=" + familyMemberId +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseholdFamilyMember)) return false;
        HouseholdFamilyMember householdFamilyMember = (HouseholdFamilyMember) o;
        return getId().equals(householdFamilyMember.getId()) && getFamilyMemberId().equals(householdFamilyMember.getFamilyMemberId()) && getLastModifiedDate().equals(householdFamilyMember.getLastModifiedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFamilyMemberId(), getLastModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public HouseholdFamilyMember(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(Long familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
