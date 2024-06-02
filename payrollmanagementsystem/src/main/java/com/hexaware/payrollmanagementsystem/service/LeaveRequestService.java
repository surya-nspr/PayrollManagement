package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;

public interface LeaveRequestService {

    void submitLeaveRequest(LeaveRequestDTO leaveRequestDTO);

	void approveLeaveRequest(LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException;

	void rejectLeaveRequest(LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException;

	void submitLeaveRequest(Employee employee, LeaveRequestDTO leaveRequest);

	List<LeaveRequestDTO> getLeaveRequestsByManagerId(Long managerId);

	List<LeaveRequestDTO> getLeaveRequestsByEmployeeId(Long employeeId) throws InternalServerErrorException;


}
