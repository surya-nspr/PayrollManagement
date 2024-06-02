package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.PayrollPolicyDTO;

public interface PayrollPolicyService {


	    void addPayrollPolicy(PayrollPolicyDTO payrollPolicyDTO);

	    void updatePayrollPolicy(Long policyId, PayrollPolicyDTO updatedPolicyDTO) throws ResourceNotFoundException;

	    List<PayrollPolicyDTO> getAllPayrollPolicies();
	    
	    PayrollPolicyDTO getPolicyByPolicyId(Long policyId) throws ResourceNotFoundException;
	}
