package com.hexaware.payrollmanagementsystem.service;

import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;

public interface ManagerService {

	void approveLeaveRequest(LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException;
	
	void rejectLeaveRequest(LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException;

	void addManager(ManagerDTO managerDTO);

	void updateManager(ManagerDTO managerDTO, List<String> fieldsToUpdate);

	List<LeaveRequestDTO> getLeaveRequestsByManagerId(Long managerId) throws ResourceNotFoundException;

	List<PayrollRecordDTO> reviewTeamPayrolls(Long managerId) throws BadRequestException, ResourceNotFoundException;

	ManagerDTO getManagerById(Long managerId) throws InternalServerErrorException, ResourceNotFoundException;

	List<ManagerDTO> getAllManagers() throws InternalServerErrorException;

}
