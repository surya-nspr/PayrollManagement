package com.hexaware.payrollmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.PayrollPolicyDTO;
import com.hexaware.payrollmanagementsystem.service.PayrollPolicyService;

@RestController
@RequestMapping("/api/payroll-policies")
@CrossOrigin("*")
public class PayrollPolicyController {

    private final PayrollPolicyService payrollPolicyService;

    @Autowired
    public PayrollPolicyController(PayrollPolicyService payrollPolicyService) {
        this.payrollPolicyService = payrollPolicyService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addPayrollPolicy(@RequestBody PayrollPolicyDTO payrollPolicyDTO)throws InternalServerErrorException {
        payrollPolicyService.addPayrollPolicy(payrollPolicyDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{policyId}")
    public ResponseEntity<Void> updatePayrollPolicy(@PathVariable Long policyId, @RequestBody PayrollPolicyDTO updatedPolicyDTO) throws ResourceNotFoundException {
        payrollPolicyService.updatePayrollPolicy(policyId, updatedPolicyDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PayrollPolicyDTO>> getAllPayrollPolicies() throws InternalServerErrorException {
        List<PayrollPolicyDTO> payrollPolicies = payrollPolicyService.getAllPayrollPolicies();
        return new ResponseEntity<>(payrollPolicies, HttpStatus.OK);
    }
    
    @GetMapping("/{policyId}")
    public ResponseEntity<PayrollPolicyDTO> getPolicyByPolicyId(@PathVariable Long policyId) throws ResourceNotFoundException {
            PayrollPolicyDTO payrollPolicyDTO = payrollPolicyService.getPolicyByPolicyId(policyId);
            return ResponseEntity.ok(payrollPolicyDTO);
        
    }
}
