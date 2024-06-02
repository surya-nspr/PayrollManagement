package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.PayrollPolicyDTO;
import com.hexaware.payrollmanagementsystem.entity.PayrollPolicy;
import com.hexaware.payrollmanagementsystem.repository.PayrollPolicyRepository;
import com.hexaware.payrollmanagementsystem.service.PayrollPolicyService;

@Service
public class PayrollPolicyServiceImpl implements PayrollPolicyService {

    private final PayrollPolicyRepository payrollPolicyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PayrollPolicyServiceImpl(PayrollPolicyRepository payrollPolicyRepository, ModelMapper modelMapper) {
        this.payrollPolicyRepository = payrollPolicyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addPayrollPolicy(PayrollPolicyDTO payrollPolicyDTO)throws InternalServerErrorException {
    	try {
        PayrollPolicy payrollPolicy = modelMapper.map(payrollPolicyDTO, PayrollPolicy.class);
        payrollPolicyRepository.save(payrollPolicy);
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to add payroll policy");
         }
    }


    
    @Override
    public List<PayrollPolicyDTO> getAllPayrollPolicies() throws InternalServerErrorException{
    	try {
        List<PayrollPolicy> payrollPolicies = payrollPolicyRepository.findAll();
        return payrollPolicies.stream()
                .map(payrollPolicy -> modelMapper.map(payrollPolicy, PayrollPolicyDTO.class))
                .collect(Collectors.toList());
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to retrive payroll policies");
         }
    }
    
    @Override
    public void updatePayrollPolicy(Long policyId, PayrollPolicyDTO updatedPolicyDTO) throws ResourceNotFoundException {
        Optional<PayrollPolicy> existingPolicyOptional = payrollPolicyRepository.findById(policyId);
        if (existingPolicyOptional.isPresent()) {
            PayrollPolicy existingPolicy = existingPolicyOptional.get();
            // Update the existing policy with the new values from the DTO
            existingPolicy.setPolicyName(updatedPolicyDTO.getPolicyName());
            existingPolicy.setDescription(updatedPolicyDTO.getDescription());
            // Save the updated policy
            payrollPolicyRepository.save(existingPolicy);
        } else {
            throw new ResourceNotFoundException("Payroll Policy", "ID", policyId);
        }
    }

    @Override
    public PayrollPolicyDTO getPolicyByPolicyId(Long policyId) throws ResourceNotFoundException {
        Optional<PayrollPolicy> payrollPolicyOptional = payrollPolicyRepository.findById(policyId);
        if (payrollPolicyOptional.isPresent()) {
            PayrollPolicy payrollPolicy = payrollPolicyOptional.get();
            return modelMapper.map(payrollPolicy, PayrollPolicyDTO.class);
        } else {
            throw new ResourceNotFoundException("Payroll Policy", "ID", policyId);
        }
    }

}