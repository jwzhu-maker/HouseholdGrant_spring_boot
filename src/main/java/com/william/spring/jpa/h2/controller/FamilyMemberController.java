package com.william.spring.jpa.h2.controller;

import com.william.spring.jpa.h2.model.FamilyMember;
import com.william.spring.jpa.h2.model.Household;
import com.william.spring.jpa.h2.repository.FamilyMemberRepository;
import com.william.spring.jpa.h2.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class FamilyMemberController {
    @Autowired
    FamilyMemberRepository familyMemberRepository;
    HouseholdRepository householdRepository;

    public FamilyMemberController(FamilyMemberRepository familyMemberRepository, HouseholdRepository householdRepository) {
        this.familyMemberRepository = familyMemberRepository;
        this.householdRepository = householdRepository;
    }

    @GetMapping("/familyMembers")
    public ResponseEntity<List<FamilyMember>> getAllFamilyMembers(@RequestParam(required = false) String name) {
        try {
            List<FamilyMember> familyMembers = new ArrayList<>();

            if (name == null)
                familyMemberRepository.findAll().forEach(familyMembers::add);
            else
                familyMemberRepository.findByName(name).forEach(familyMembers::add);

            if (familyMembers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(familyMembers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/familyMembers/{id}")
    public ResponseEntity<FamilyMember> getFamilyMemberById(@PathVariable("id") long id) {
        Optional<FamilyMember> familyMemberData = familyMemberRepository.findById(id);

        if (familyMemberData.isPresent()) {
            return new ResponseEntity<>(familyMemberData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        return new ResponseEntity<FamilyMember>(familyMemberRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/familyMembers")
    public ResponseEntity<FamilyMember> createFamilyMember(@RequestBody FamilyMember familyMember) {
        try {
            FamilyMember _familyMember = new FamilyMember(familyMember.getName(),
                    familyMember.getGender(),
                    familyMember.getMaritalStatus(),
                    familyMember.getSpouseId(),
                    familyMember.getOccupationType(),
                    familyMember.getAnnualIncome(),
                    familyMember.getDob(),
                    familyMember.getHouseholdId());

            _familyMember = familyMemberRepository.save(_familyMember);

            // Update household size and household total income
            Optional<Household> householdData = householdRepository.findById(_familyMember.getHouseholdId());
            if (householdData.isPresent()) {
                Household household = householdData.get();
                household.setHouseholdSize(household.getHouseholdSize() + 1);
                household.setTotalIncome(household.getTotalIncome() + _familyMember.getAnnualIncome());
                householdRepository.save(household);
            }
            return new ResponseEntity<>(_familyMember, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/familyMembers/{id}")
    public ResponseEntity<FamilyMember> updateFamilyMember(@PathVariable("id") long id, @RequestBody FamilyMember familyMember) {
        Optional<FamilyMember> familyMemberData = familyMemberRepository.findById(id);

        if (familyMemberData.isPresent()) {
            FamilyMember _familyMember = familyMemberData.get();
            _familyMember.setName(familyMember.getName());
            _familyMember.setGender(familyMember.getGender());
            _familyMember.setMaritalStatus(familyMember.getMaritalStatus());
            _familyMember.setSpouseId(familyMember.getSpouseId());
            _familyMember.setOccupationType(familyMember.getOccupationType());
            _familyMember.setAnnualIncome(familyMember.getAnnualIncome());
            _familyMember.setDob(familyMember.getDob());
            return new ResponseEntity<>(familyMemberRepository.save(_familyMember), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/familyMembers/{id}")
    public ResponseEntity<HttpStatus> deleteFamilyMember(@PathVariable("id") long id) {
        try {
            // Update household size and household total income
            Optional<FamilyMember> familyMemberData = familyMemberRepository.findById(id);
            if (familyMemberData.isPresent()) {
                FamilyMember familyMember = familyMemberData.get();
                Optional<Household> householdData = householdRepository.findById(familyMember.getHouseholdId());
                if (householdData.isPresent()) {
                    Household household = householdData.get();
                    household.setHouseholdSize(household.getHouseholdSize() - 1);
                    household.setTotalIncome(household.getTotalIncome() - familyMember.getAnnualIncome());
                    householdRepository.save(household);
                }
            }

            familyMemberRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/familyMembers")
    public ResponseEntity<HttpStatus> deleteAllFamilyMembers() {
        try {
            familyMemberRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/familyMembers/name")
    public ResponseEntity<List<FamilyMember>> findByName(@RequestParam(required = true) String name) {
        try {
            List<FamilyMember> familyMembers = familyMemberRepository.findByName(name);

            if (familyMembers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(familyMembers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/familyMembers/dob")
    public ResponseEntity<List<FamilyMember>> findByDOB(@RequestParam(required = true) String DOB) {
        try {
            List<FamilyMember> familyMembers = familyMemberRepository.findByDob(DOB);

            if (familyMembers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(familyMembers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
