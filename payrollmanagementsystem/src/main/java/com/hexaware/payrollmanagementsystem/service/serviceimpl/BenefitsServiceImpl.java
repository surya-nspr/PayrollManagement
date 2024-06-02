package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.BenefitsDTO;
import com.hexaware.payrollmanagementsystem.entity.Benefits;
import com.hexaware.payrollmanagementsystem.repository.BenefitRepository;
import com.hexaware.payrollmanagementsystem.service.BenefitsService;

@Service
public class BenefitsServiceImpl implements BenefitsService {

    private final BenefitRepository benefitRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BenefitsServiceImpl(BenefitRepository benefitRepository, ModelMapper modelMapper) {
        this.benefitRepository = benefitRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addBenefit(BenefitsDTO benefitDTO) throws InternalServerErrorException{
    	try {
        Benefits benefit = modelMapper.map(benefitDTO, Benefits.class);
        benefitRepository.save(benefit);
    	}
    	catch (Exception e) {
        	throw new InternalServerErrorException("Failed to add benefit");
        }
    }
    
    @Override
    public void updateBenefit(Long benefitId, BenefitsDTO updatedBenefitDTO) throws ResourceNotFoundException {
       try {
    	Optional<Benefits> optionalBenefit = benefitRepository.findById(benefitId);
        if (optionalBenefit.isPresent()) {
            Benefits existingBenefit = optionalBenefit.get();
            Benefits updatedBenefit = modelMapper.map(updatedBenefitDTO, Benefits.class);
            
            // Update existingBenefit with fields from updatedBenefit
            existingBenefit.setBenefitName(updatedBenefit.getBenefitName());
            existingBenefit.setDescription(updatedBenefit.getDescription());
            existingBenefit.setAmount(updatedBenefit.getAmount());
            existingBenefit.setCoverage(updatedBenefit.getCoverage());
            // Update any other fields as needed
            
            // Save the updated benefit
            benefitRepository.save(existingBenefit);
        } else {
            throw new IllegalArgumentException("Benefit not found with ID: " + benefitId);
        }
       }
       catch (Exception ex)
       {
    	   throw new ResourceNotFoundException("Benefit not found ","with ID: " , benefitId);
       }
    }

    @Override
    public List<BenefitsDTO> getAllBenefits()throws InternalServerErrorException {
    	try {
        List<Benefits> allBenefits = benefitRepository.findAll();
        return allBenefits.stream()
                .map(benefit -> modelMapper.map(benefit, BenefitsDTO.class))
                .collect(Collectors.toList());
    	}
    	catch (Exception e) {
        	throw new InternalServerErrorException("Failed to get all benefits");
        }
    }

    @Override
    public BenefitsDTO getBenefitById(Long benefitId) throws ResourceNotFoundException {
    	try {
        Optional<Benefits> benefitOptional = benefitRepository.findById(benefitId);
        if (benefitOptional.isPresent()) {
            Benefits benefit = benefitOptional.get();
            return modelMapper.map(benefit, BenefitsDTO.class);
        } else {
            throw new IllegalArgumentException("Benefit not found with ID: " + benefitId);
        }
    	}
        catch (Exception ex)
        {
     	   throw new ResourceNotFoundException("Benefit not found ","with ID: " , benefitId);
        }
    }
    
}
