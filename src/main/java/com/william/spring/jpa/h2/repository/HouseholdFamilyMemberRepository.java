package com.william.spring.jpa.h2.repository;

import com.william.spring.jpa.h2.model.HouseholdFamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdFamilyMemberRepository extends JpaRepository<HouseholdFamilyMember, Long> {
}