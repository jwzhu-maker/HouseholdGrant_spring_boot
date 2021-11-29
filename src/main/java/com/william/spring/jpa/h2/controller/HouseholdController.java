package com.william.spring.jpa.h2.controller;


import com.william.spring.jpa.h2.model.FamilyMember;
import com.william.spring.jpa.h2.model.GrantType;
import com.william.spring.jpa.h2.model.Household;
import com.william.spring.jpa.h2.model.HousingType;
import com.william.spring.jpa.h2.repository.HouseholdRepository;
import com.william.spring.jpa.h2.service.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HouseholdController {
    @Autowired
    HouseholdRepository householdRepository;

    private FamilyMemberService familyMemberService;

    @Autowired
    public HouseholdController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @GetMapping("/households")
    public ResponseEntity<List<Household>> getAllHouseholds(
            @RequestParam(required = false) HousingType housingType) {
        try {
            List<Household> households = new ArrayList<Household>();

            if (housingType == null)
                householdRepository.findAll().forEach(households::add);
            else
                householdRepository.findByHousingType(housingType).forEach(households::add);

            if (households.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(households, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/households/grant")
    public ResponseEntity<List<Household>> getAllHouseholdsByGrantType(
            @RequestParam(required = true) GrantType grantType) {
        try {
            List<Household> households = new ArrayList<Household>();

            ResponseEntity<List<Household>> errorResponse = getListResponseEntity(grantType, households);

            if (errorResponse != null) return errorResponse;


            if (households.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(households, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<List<Household>> getListResponseEntity(GrantType grantType, List<Household> households) {
        if (grantType == GrantType.Student_Encouragement_Bonus) {
            long nowMilliSeconds = currentTimeMillis();
            Date beforeDateOfBirth = new Date(nowMilliSeconds - (1000L * 60 * 60 * 24 * 365 * 16));
            List<FamilyMember> FamilyMembers = familyMemberService.getAllFamilyMembers();
            if (FamilyMembers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for (FamilyMember familyMember : FamilyMembers) {
                Calendar calendar = Calendar.getInstance();
//                    calendar.add(Calendar.YEAR, -16);
                calendar.clear();
                calendar.set(parseInt(familyMember.getDob().substring(0, 4)),
                        parseInt(familyMember.getDob().substring(5, 7)),
                        parseInt(familyMember.getDob().substring(8, 10)));

                if (calendar.getTime().after(beforeDateOfBirth)) {
                    Optional<Household> householdData = householdRepository.findById(familyMember.getHouseholdId());
                    if (householdData.isPresent()) {
                        Household household = householdData.get();

                        if (household.getTotalIncome() < 150000) {
                            if (!households.contains(household)) {
                                households.add(household);
                            }
                        }
                    } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                }
            }
        }

        if (grantType == GrantType.Family_Togetherness_Scheme) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -18);
            Date beforeDateOfBirth = calendar.getTime();
            String formattedDate = format("%1$tY-%1$tm-%1$td", beforeDateOfBirth);
            List<FamilyMember> FamilyMembers = familyMemberService
                    .getFamilyMemberAfter(formattedDate);
            if (FamilyMembers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for (FamilyMember familyMember : FamilyMembers) {
                Optional<Household> householdData = householdRepository.findById(familyMember.getHouseholdId());
                if (householdData.isPresent()) {
                    Household household = householdData.get();
                    if (household.getHouseholdSize() >= 3) {
                        if (!households.contains(household)) {
                            households.add(household);
                        }
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

        }

        if (grantType == GrantType.Elder_Bonus) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -50);
            Date beforeDateOfBirth = calendar.getTime();
            String formattedDate = format("%1$tY-%1$tm-%1$td", beforeDateOfBirth);
            List<FamilyMember> FamilyMembers = familyMemberService
                    .getFamilyMemberBefore(formattedDate);
            if (FamilyMembers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for (FamilyMember familyMember : FamilyMembers) {
                Optional<Household> householdData = householdRepository.findById(familyMember.getHouseholdId());
                if (householdData.isPresent()) {
                    Household household = householdData.get();
                    if (!households.contains(household)) {
                        households.add(household);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

        }

        if (grantType == GrantType.Baby_Sunshine_Grant) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -5);
            Date beforeDateOfBirth = calendar.getTime();
            String formattedDate = format("%1$tY-%1$tm-%1$td", beforeDateOfBirth);
            List<FamilyMember> FamilyMembers = familyMemberService
                    .getFamilyMemberAfter(formattedDate);
            if (FamilyMembers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for (FamilyMember familyMember : FamilyMembers) {
                Optional<Household> householdData = householdRepository.findById(familyMember.getHouseholdId());
                if (householdData.isPresent()) {
                    Household household = householdData.get();
                    if (!households.contains(household)) {
                        households.add(household);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }

        if (grantType == GrantType.YOLO_GST_Grant) {
            List<Household> householdsData = householdRepository.findAll();
            if (householdsData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for (Household household : householdsData) {
                if (household.getTotalIncome() < 100000) {
                    if (!households.contains(household)) {
                        households.add(household);
                    }
                }
            }
        }
        return null;
    }

    @GetMapping("/households/{id}")
    public ResponseEntity<Household> getHouseholdById(@PathVariable("id") long id) {
        Optional<Household> householdData = householdRepository.findById(id);

        if (householdData.isPresent()) {
            return new ResponseEntity<>(householdData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        return new ResponseEntity<Household>(householdRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/households")
    public ResponseEntity<Household> createHousehold(@RequestBody Household household) {
        try {
            Household _household = new Household(household.getHousingType(), household.getHouseholdSize(), household.getTotalIncome());

            _household = householdRepository.save(_household);
            return new ResponseEntity<>(_household, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/households/{id}")
    public ResponseEntity<Household> updateHousehold(@PathVariable("id") long id, @RequestBody Household household) {
        Optional<Household> householdData = householdRepository.findById(id);

        if (householdData.isPresent()) {
            Household _household = householdData.get();
            _household.setHousingType(household.getHousingType());
            _household.setHouseholdSize(household.getHouseholdSize());
            _household.setTotalIncome(household.getTotalIncome());
            return new ResponseEntity<>(householdRepository.save(_household), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/households/{id}")
    public ResponseEntity<HttpStatus> deleteHousehold(@PathVariable("id") long id) {
        try {
            householdRepository.deleteById(id);

            // remove all the family members associated with this household
            List<FamilyMember> familyMembers = familyMemberService.getFamilyMemberByHouseholdId(id);
            for (FamilyMember familyMember : familyMembers) {
                familyMemberService.deleteFamilyMember(familyMember.getId());
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/households")
    public ResponseEntity<HttpStatus> deleteAllHouseholds() {
        try {
            householdRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/households/totalIncome")
    public ResponseEntity<List<Household>> findByTotalIncome(@RequestParam(required = true) int totalIncome) {
        try {
            List<Household> households = householdRepository.findByTotalIncomeLessThan(totalIncome);

            if (households.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(households, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
