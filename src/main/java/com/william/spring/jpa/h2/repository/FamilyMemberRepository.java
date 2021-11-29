package com.william.spring.jpa.h2.repository;

import com.william.spring.jpa.h2.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    List<FamilyMember> findByName(String name);

    List<FamilyMember> findByDob(String dob);

    List<FamilyMember> findByDobAfter(String dob);

    List<FamilyMember> findByDobBefore(String dob);

    List<FamilyMember> findByHouseholdId(Long householdId);

}