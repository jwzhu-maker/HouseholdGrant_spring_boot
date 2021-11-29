package com.william.spring.jpa.h2.service;

import com.william.spring.jpa.h2.model.FamilyMember;

import java.util.List;

public interface FamilyMemberService {
    FamilyMember saveFamilyMember(FamilyMember familyMember);
    List<FamilyMember> getAllFamilyMembers();
    FamilyMember getFamilyMember(long id);
    FamilyMember updateFamilyMember(FamilyMember familyMember);
    void deleteFamilyMember(long id);
    List<FamilyMember> getFamilyMemberAfter(String dob);
    List<FamilyMember> getFamilyMemberBefore(String dob);

    List<FamilyMember> getFamilyMemberByHouseholdId(long householdId);

}
