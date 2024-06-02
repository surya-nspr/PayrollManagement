package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.dto.BenefitsDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollProcessorDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;

public interface PayrollProcessorService {

    PayrollRecordDTO calculatePayroll();

    PayrollRecordDTO verifyPayrollData();

    void processPayments();

	void addPayrollProcessor(PayrollProcessorDTO payrollProcessorDTO);

	void updatePayrollProcessor(PayrollProcessorDTO payrollProcessorDTO, List<String> fieldsToUpdate);

	void updateBenefit(Long benefitId, BenefitsDTO updatedBenefitDTO) throws ResourceNotFoundException;

	void addBenefit(BenefitsDTO benefitDTO);

	List<BenefitsDTO> getAllBenefits();

	List<PayrollRecordDTO> getPayrollRecordsByProcessorId(Long processorId) throws ResourceNotFoundException;

	BenefitsDTO getBenefitById(Long benefitId) throws ResourceNotFoundException;

	List<PayrollProcessorDTO> getAllPayrollProcessors() throws InternalServerErrorException;

	PayrollProcessorDTO getProcessorById(Long processorId) throws ResourceNotFoundException;

	List<AuditTrailDTO> getAllAuditTrails();


}
