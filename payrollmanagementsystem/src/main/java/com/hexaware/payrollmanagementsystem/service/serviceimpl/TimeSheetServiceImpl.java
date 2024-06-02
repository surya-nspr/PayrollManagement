package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.TimeSheetDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.TimeSheet;
import com.hexaware.payrollmanagementsystem.repository.EmployeeRepository;
import com.hexaware.payrollmanagementsystem.repository.TimeSheetRepository;
import com.hexaware.payrollmanagementsystem.service.TimeSheetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    public TimeSheetServiceImpl(TimeSheetRepository timeSheetRepository, ModelMapper modelMapper,
 			EmployeeRepository employeeRepository) {
 		super();
 		this.timeSheetRepository = timeSheetRepository;
 		this.modelMapper = modelMapper;
 		this.employeeRepository = employeeRepository;
 	}

 
	@Override
    public List<TimeSheetDTO> getTimesheetsByEmployee(EmployeeDTO employee)throws ResourceNotFoundException {
    	try {
        List<TimeSheet> timeSheets = timeSheetRepository.findByEmployee(employee);
        return timeSheets.stream()
                .map(timeSheet -> modelMapper.map(timeSheet, TimeSheetDTO.class))
                .collect(Collectors.toList());
    	}
    	catch (Exception e) {
         	throw new  ResourceNotFoundException("Time sheet not found ","with ID: " , employee.getEmployeeId());
         }
    }


    @Override
    public void deleteTimeSheet(Long timeSheetId) throws InternalServerErrorException, ResourceNotFoundException {
        try {
            timeSheetRepository.deleteById(timeSheetId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Time sheet not found ","with ID: " , timeSheetId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete time sheet");
        }
    }

    
    @Override
    public void submitTimeSheet(Employee employee, TimeSheetDTO timeSheetDTO) throws BadRequestException {
        try {
            TimeSheet timeSheet = modelMapper.map(timeSheetDTO, TimeSheet.class);
            timeSheet.setEmployee(employee);
            
            // Parse start date and end date from the DTO
            LocalDate startDate = timeSheetDTO.getStartDate();
            LocalDate endDate = timeSheetDTO.getEndDate();
            
            // Calculate number of days worked
            long daysWorked = ChronoUnit.DAYS.between(startDate, endDate)+ 1;;
            
            // Calculate total hours worked
            int totalHoursWorked = timeSheetDTO.getHoursWorked() * (int) daysWorked;
            
            // Set total hours worked in the time sheet entity
            timeSheet.setTotalHoursWorked(totalHoursWorked);
            System.out.println("Days"+daysWorked);
            System.out.println("Hours"+totalHoursWorked);
            
            timeSheetRepository.save(timeSheet);
        } catch (Exception e) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to submit time sheet", e.getMessage());
        }
    }


    @Override
    public void updateTimeSheet(Long timeSheetId, TimeSheetDTO updatedTimeSheetDTO) throws ResourceNotFoundException {
        // Retrieve the existing time sheet from the repository using its ID
        Optional<TimeSheet> optionalTimeSheet = timeSheetRepository.findById(timeSheetId);
        
        // Check if the time sheet exists
        if (optionalTimeSheet.isPresent()) {
            TimeSheet existingTimeSheet = optionalTimeSheet.get();
            
            // Update the existing time sheet with the new values
            existingTimeSheet.setStartDate(updatedTimeSheetDTO.getStartDate());
            existingTimeSheet.setEndDate(updatedTimeSheetDTO.getEndDate());
            existingTimeSheet.setHoursWorked(updatedTimeSheetDTO.getHoursWorked());
            existingTimeSheet.setOverTime(updatedTimeSheetDTO.getOverTime());
            existingTimeSheet.setStatus(updatedTimeSheetDTO.getStatus());
            
            // Calculate number of days worked based on the updated date range
            long daysWorked = ChronoUnit.DAYS.between(existingTimeSheet.getStartDate(), existingTimeSheet.getEndDate()) + 1;
            
            // Calculate total hours worked based on the updated hours per day and days worked
            int totalHoursWorked = updatedTimeSheetDTO.getHoursWorked() * (int) daysWorked;
            
            // Set the recalculated total hours worked in the time sheet entity
            existingTimeSheet.setTotalHoursWorked(totalHoursWorked);
            
            // Save the updated time sheet
            timeSheetRepository.save(existingTimeSheet);
        } else {
            // If the time sheet with the given ID doesn't exist, you can throw an exception or handle it accordingly
            throw new ResourceNotFoundException("Time sheet not found", "with ID: ", timeSheetId);
        }
    }
   
    @Override
    public List<TimeSheetDTO> getAllTimeSheets() throws InternalServerErrorException{
    	try {
        List<TimeSheet> timeSheets = timeSheetRepository.findAll();
        return timeSheets.stream()
                .map(timeSheet -> modelMapper.map(timeSheet, TimeSheetDTO.class))
                .collect(Collectors.toList());
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to retrieve time sheets");
         }
    }
    
    @Override
    public TimeSheetDTO getTimeSheetById(Long timeSheetId) throws ResourceNotFoundException{
        // Retrieve the time sheet from the repository using its ID
        Optional<TimeSheet> optionalTimeSheet = timeSheetRepository.findById(timeSheetId);

        // Check if the time sheet exists
        if (optionalTimeSheet.isPresent()) {
            // Map the TimeSheet entity to TimeSheetDTO and return
            return modelMapper.map(optionalTimeSheet.get(), TimeSheetDTO.class);
        } else {
            // If the time sheet with the given ID doesn't exist, you can throw an exception or handle it accordingly
        	throw new ResourceNotFoundException("Time sheet not found", "with ID: " , timeSheetId);
        }
        }
    @Override
    public int getTotalHoursWorkedByEmployee(Long employeeId) throws ResourceNotFoundException {

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isPresent()) {

            Employee employee = optionalEmployee.get();

            List<TimeSheet> timeSheets = timeSheetRepository.findByEmployee(employee);

            int totalHoursWorked = timeSheets.stream()
                    .mapToInt(TimeSheet::getTotalHoursWorked)
                    .sum();

            return totalHoursWorked;
        } else {
            throw new ResourceNotFoundException("Employee not found", "with ID: ", employeeId);
        }
    }
}


