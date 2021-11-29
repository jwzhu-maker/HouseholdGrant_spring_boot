package com.william.spring.jpa.h2.service.impl;

import com.william.spring.jpa.h2.model.FamilyMember;
import com.william.spring.jpa.h2.repository.FamilyMemberRepository;
import com.william.spring.jpa.h2.service.FamilyMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private FamilyMemberRepository familyMemberRepository;

    public FamilyMemberServiceImpl(FamilyMemberRepository familyMemberRepository) {
        super();
        this.familyMemberRepository = familyMemberRepository;
    }


    @Override
    public FamilyMember saveFamilyMember(FamilyMember familyMember) {
        return familyMemberRepository.save(familyMember);
    }

    @Override
    public List<FamilyMember> getAllFamilyMembers() {
        return familyMemberRepository.findAll();
    }

    @Override
    public List<FamilyMember> getFamilyMemberAfter(String dob) {
        return familyMemberRepository.findByDobAfter(dob);
    }

    @Override
    public List<FamilyMember> getFamilyMemberBefore(String dob) {
        return familyMemberRepository.findByDobBefore(dob);
    }

    @Override
    public List<FamilyMember> getFamilyMemberByHouseholdId(long householdId) {
        return familyMemberRepository.findByHouseholdId(householdId);
    }

    @Override
    public FamilyMember getFamilyMember(long id) {
        return familyMemberRepository.findById(id).orElseThrow(() -> new RuntimeException("FamilyMember not found"));
    }

    @Override
    public FamilyMember updateFamilyMember(FamilyMember familyMember) {
        FamilyMember existingFamilyMember = familyMemberRepository.findById(familyMember.getId()).orElseThrow(() -> new RuntimeException("FamilyMember not found"));
        existingFamilyMember.setName(familyMember.getName());
        existingFamilyMember.setGender(familyMember.getGender());
        existingFamilyMember.setMaritalStatus(familyMember.getMaritalStatus());
        existingFamilyMember.setSpouseId(familyMember.getSpouseId());
        existingFamilyMember.setOccupationType(familyMember.getOccupationType());
        existingFamilyMember.setAnnualIncome(familyMember.getAnnualIncome());
        existingFamilyMember.setDob(familyMember.getDob());
        existingFamilyMember.setHouseholdId(familyMember.getHouseholdId());

        return familyMemberRepository.save(existingFamilyMember);
    }

    @Override
    public void deleteFamilyMember(long id) {
        familyMemberRepository.findById(id).orElseThrow(() -> new RuntimeException("FamilyMember not found"));
        familyMemberRepository.deleteById(id);

    }


}
