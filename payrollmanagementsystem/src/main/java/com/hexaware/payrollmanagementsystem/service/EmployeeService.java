package com.hexaware.payrollmanagementsystem.service;

import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.dto.TimeSheetDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;

import java.util.List;

public interface EmployeeService {
    
    EmployeeDTO getEmployeeById(Long employeeId) throws ResourceNotFoundException;

    List<EmployeeDTO> getAllEmployees();

    void submitLeaveRequest(Employee employee, LeaveRequestDTO leaveRequest);

    void submitTimeSheet(Employee employee, TimeSheetDTO timeSheet) throws ResourceNotFoundException;
    
	List<TimeSheetDTO> getTimeSheetsByEmployeeId(Long employeeId) throws ResourceNotFoundException;

	List<PayrollRecordDTO> getPayrollRecordsByEmployeeId(Long employeeId) throws ResourceNotFoundException;

	void deleteEmployeeById(Long employeeId) throws ResourceNotFoundException;

	void addEmployeeInformation(EmployeeDTO employeeDTO);

	void manageEmployeeInformation(EmployeeDTO employeeDTO, List<String> fieldsToUpdate);

	void updateTimeSheet(Long timeSheetId, TimeSheetDTO updatedTimeSheetDTO) throws ResourceNotFoundException;

	int getTotalLeavesByEmployeeId(Long employeeId) throws ResourceNotFoundException;

	List<LeaveRequestDTO> getLeavesByEmployeeId(Long employeeId) throws ResourceNotFoundException;
	
	
    
}