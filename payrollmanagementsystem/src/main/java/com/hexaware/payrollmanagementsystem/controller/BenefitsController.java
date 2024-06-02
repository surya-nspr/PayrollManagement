package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.BenefitsDTO;
import com.hexaware.payrollmanagementsystem.service.BenefitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benefits")
public class BenefitsController {

    private final BenefitsService benefitsService;

    @Autowired
    public BenefitsController(BenefitsService benefitsService) {
        this.benefitsService = benefitsService;
    }

    @PostMapping
    public ResponseEntity<String> addBenefit(@RequestBody BenefitsDTO benefitDTO) throws InternalServerErrorException{
        benefitsService.addBenefit(benefitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Benefit added successfully");
    }

    @PutMapping("/{benefitId}")
    public ResponseEntity<String> updateBenefit(@PathVariable Long benefitId, @RequestBody BenefitsDTO updatedBenefitDTO) throws ResourceNotFoundException{
        benefitsService.updateBenefit(benefitId, updatedBenefitDTO);
        return ResponseEntity.ok("Benefit updated successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<BenefitsDTO>> getAllBenefits() throws InternalServerErrorException {
        List<BenefitsDTO> allBenefits = benefitsService.getAllBenefits();
        return ResponseEntity.ok(allBenefits);
    }

    @GetMapping("/{benefitId}")
    public ResponseEntity<BenefitsDTO> getBenefitById(@PathVariable Long benefitId) throws ResourceNotFoundException {
        BenefitsDTO benefitDTO = benefitsService.getBenefitById(benefitId);
        return ResponseEntity.ok(benefitDTO);
    }
}
