package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.BenefitsDTO;

public interface BenefitsService {
    void addBenefit(BenefitsDTO benefitDTO);
    void updateBenefit(Long benefitId, BenefitsDTO updatedBenefitDTO) throws ResourceNotFoundException;
    List<BenefitsDTO> getAllBenefits();
    BenefitsDTO getBenefitById(Long benefitId) throws ResourceNotFoundException;
}
