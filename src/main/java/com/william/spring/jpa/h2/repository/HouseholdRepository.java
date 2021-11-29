package com.william.spring.jpa.h2.repository;

import com.william.spring.jpa.h2.model.Household;
import com.william.spring.jpa.h2.model.HousingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
//    Optional<Household> findByFamilyMemberId(Long familyMemberId);

    Optional<Household> findById(Long id);

    List<Household> findByHousingType(HousingType housingType);

    List<Household> findByTotalIncomeLessThan(int totalIncome);

//    @Query("SELECT H from HOUSEHOLD H JOIN FAMILY_MEMEBER F where F.HOUSEHOLD_ID = H.ID " +
//            "AND F.DOB < ?1")
//    List<Household> findByDobLessThan(String dob);

//    @Query(value = "SELECT H from HOUSEHOLD H, FAMILY_MEMBER F where F.HOUSEHOLD_ID = H.ID " +
//            "AND F.DOB > :dob ")
//    List<Household> findByDobGreaterThan(@Param("dob") String dob);

//    @Query("SELECT H from HOUSEHOLD H JOIN FAMILY_MEMEBER F where F.HOUSEHOLD_ID = H.ID " +
//            "AND F.DOB = ?1")
//    List<Household> findByDobEqual(String dob);






}