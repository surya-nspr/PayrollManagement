package com.hexaware.payrollmanagementsystem.service;

import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import java.util.List;

public interface PayrollRecordService {

    List<PayrollRecordDTO> getAllPayrollRecords();

   PayrollRecordDTO calculatePayroll();

   PayrollRecordDTO verifyPayrollData();

   List<PayrollRecordDTO> reviewTeamPayrolls(Long managerId);

   List<PayrollRecordDTO> getPayrollRecordsByProcessorId(Long processorId) throws ResourceNotFoundException;


}
