package com.hexaware.payrollmanagementsystem.service;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.TimeSheetDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;

import java.util.List;

public interface TimeSheetService {


    List<TimeSheetDTO> getTimesheetsByEmployee(EmployeeDTO employee) throws ResourceNotFoundException;

    void deleteTimeSheet(Long timeSheetId)throws ResourceNotFoundException,InternalServerErrorException;

	void submitTimeSheet(Employee employee, TimeSheetDTO timeSheet);

	void updateTimeSheet(Long timeSheetId, TimeSheetDTO updatedTimeSheetDTO) throws ResourceNotFoundException;

	List<TimeSheetDTO> getAllTimeSheets();

	TimeSheetDTO getTimeSheetById(Long timeSheetId) throws ResourceNotFoundException;

	int getTotalHoursWorkedByEmployee(Long employeeId) throws ResourceNotFoundException;

}

