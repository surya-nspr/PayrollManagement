package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.dto.BenefitsDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollProcessorDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.service.PayrollProcessorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payrollprocessor")
@CrossOrigin("*")
public class PayrollProcessorController {

    private final PayrollProcessorService payrollProcessorService;

    @Autowired
    public PayrollProcessorController(PayrollProcessorService payrollProcessorService) {
        this.payrollProcessorService = payrollProcessorService;
    }

    @GetMapping("/calculate")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<PayrollRecordDTO> calculatePayroll()throws InternalServerErrorException {
        PayrollRecordDTO payrollRecordDTO = payrollProcessorService.calculatePayroll();
        return ResponseEntity.ok(payrollRecordDTO);
    }

    @GetMapping("/verify")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<PayrollRecordDTO> verifyPayrollData() throws InternalServerErrorException{
        PayrollRecordDTO payrollRecordDTO = payrollProcessorService.verifyPayrollData();
        return ResponseEntity.ok(payrollRecordDTO);
    }

    @GetMapping("/process-payments")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<String> processPayments()throws BadRequestException {
        payrollProcessorService.processPayments();
        return ResponseEntity.ok("Payments processed successfully.");
    }
    
    @PostMapping("/benefits")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<Void> addBenefit(@RequestBody BenefitsDTO benefitDTO) throws InternalServerErrorException {
        payrollProcessorService.addBenefit(benefitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/benefits/{benefitId}")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<Void> updateBenefit(@PathVariable("benefitId") Long benefitId,
                                              @RequestBody BenefitsDTO updatedBenefitDTO) throws ResourceNotFoundException {
        payrollProcessorService.updateBenefit(benefitId, updatedBenefitDTO);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/benefits")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<List<BenefitsDTO>> getAllBenefits() throws InternalServerErrorException{
        List<BenefitsDTO> benefits = payrollProcessorService.getAllBenefits();
        return ResponseEntity.ok(benefits);
    }

    @GetMapping("/benefits/{benefitId}")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<BenefitsDTO> getBenefitById(@PathVariable Long benefitId) throws ResourceNotFoundException {
        BenefitsDTO benefit = payrollProcessorService.getBenefitById(benefitId);
        return ResponseEntity.ok(benefit);
    }

    @GetMapping("/payrollrecords/{processorId}")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<List<PayrollRecordDTO>> getPayrollRecordsByProcessorId(@PathVariable Long processorId) throws ResourceNotFoundException {
        List<PayrollRecordDTO> payrollRecords = payrollProcessorService.getPayrollRecordsByProcessorId(processorId);
        return ResponseEntity.ok(payrollRecords);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PayrollProcessorDTO>> getAllPayrollProcessors() {
        List<PayrollProcessorDTO> payrollProcessors = payrollProcessorService.getAllPayrollProcessors();
        return ResponseEntity.ok(payrollProcessors);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_PAYROLL_PROCESSOR')")
    public ResponseEntity<PayrollProcessorDTO> getPayrollProcessorById(@PathVariable("id") Long processorId)  throws ResourceNotFoundException{
            PayrollProcessorDTO payrollProcessor = payrollProcessorService.getProcessorById(processorId);
            return ResponseEntity.ok(payrollProcessor);
    }
    
    @GetMapping("/audittrails/all")
    public List<AuditTrailDTO> getAllAuditTrails() throws InternalServerErrorException{
        // Call the service method to fetch all compliance reports
        return payrollProcessorService.getAllAuditTrails();
    }

   

}
