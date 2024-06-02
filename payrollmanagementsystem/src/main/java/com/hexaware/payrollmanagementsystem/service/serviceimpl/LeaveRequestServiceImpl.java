package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.entity.ApprovalStatus;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.LeaveRequest;
import com.hexaware.payrollmanagementsystem.entity.LeaveType;
import com.hexaware.payrollmanagementsystem.entity.Manager;
import com.hexaware.payrollmanagementsystem.repository.EmployeeRepository;
import com.hexaware.payrollmanagementsystem.repository.LeaveRequestRepository;
import com.hexaware.payrollmanagementsystem.repository.ManagerRepository;
import com.hexaware.payrollmanagementsystem.service.LeaveRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository,
    		ManagerRepository managerRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public void submitLeaveRequest(LeaveRequestDTO leaveRequestDTO)throws InternalServerErrorException {
    	try {
        LeaveRequest leaveRequest = modelMapper.map(leaveRequestDTO, LeaveRequest.class);
        leaveRequest.setStatus(ApprovalStatus.PENDING); // Set status as pending initially
        leaveRequestRepository.save(leaveRequest);
    	}
    	 catch (Exception e) {
          	throw new InternalServerErrorException("Failed to submit leave request");
          }
    }

    //In manager serviceIMPL
    @Override
    public void approveLeaveRequest(LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException {

        try {
            Optional<LeaveRequest> existingLeaveRequestOptional = leaveRequestRepository.findById(leaveRequestDTO.getRequestId());

            if (existingLeaveRequestOptional.isPresent()) {
                LeaveRequest existingLeaveRequest = existingLeaveRequestOptional.get();

                LeaveType leaveType = existingLeaveRequest.getLeaveType();

                if (leaveType == LeaveType.MATERNITY_LEAVE ||
                        leaveType == LeaveType.SICK_LEAVE ||
                        leaveType == LeaveType.PATERNITY_LEAVE) {

                    existingLeaveRequest.setStatus(ApprovalStatus.APPROVED);
                } else if (existingLeaveRequest.getLeaveType() == LeaveType.UNPAID_LEAVE) {

                    Employee employee = existingLeaveRequest.getEmployee();
                    long leavesRequested = calculateLeavesWithoutWeekends(existingLeaveRequest.getStartDate(), existingLeaveRequest.getEndDate());

                    if (employee.getTotalLeaves() >= existingLeaveRequest.getLeavesTaken()) {

                        existingLeaveRequest.setStatus(ApprovalStatus.APPROVED);

                        existingLeaveRequest.setLeavesTaken(leavesRequested);

                        employee.setTotalLeaves(employee.getTotalLeaves() - leavesRequested);

                    } else {
                        // If total leaves are not available, set it as paid leave
                        existingLeaveRequest.setLeaveType(LeaveType.OTHER);
                    }
                } else {

                    long leavesTaken = calculateLeavesWithoutWeekends(existingLeaveRequest.getStartDate(), existingLeaveRequest.getEndDate());

                    existingLeaveRequest.setLeavesTaken(leavesTaken);

                    Employee employee = existingLeaveRequest.getEmployee();
                    employee.setTotalLeaves(employee.getTotalLeaves() - leavesTaken);

                    existingLeaveRequest.setStatus(ApprovalStatus.APPROVED);
                }

                leaveRequestRepository.save(existingLeaveRequest);
                employeeRepository.save(existingLeaveRequest.getEmployee());
            } else {
                throw new IllegalArgumentException("Leave request not found with id: " + leaveRequestDTO.getRequestId());
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Leave Request", "ID", leaveRequestDTO.getRequestId());
        }
    }

    private long calculateLeavesWithoutWeekends(LocalDate startDate, LocalDate endDate) {
        long leavesTaken = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                leavesTaken++;
            }
            date = date.plusDays(1);
        }
        return leavesTaken;
    }

    @Override
    public void rejectLeaveRequest(LeaveRequestDTO leaveRequestDTO)throws ResourceNotFoundException {

        try {

        Optional<LeaveRequest> existingLeaveRequestOptional = leaveRequestRepository.findById(leaveRequestDTO.getRequestId());

        if (existingLeaveRequestOptional.isPresent()) {
            LeaveRequest existingLeaveRequest = existingLeaveRequestOptional.get();

            existingLeaveRequest.setStatus(ApprovalStatus.REJECTED);

            existingLeaveRequest.setReason(leaveRequestDTO.getReason());

            leaveRequestRepository.save(existingLeaveRequest);
        } else {
            throw new IllegalArgumentException("Leave request not found with id: " + leaveRequestDTO.getRequestId());
        }
        }
        catch (Exception e) {
			throw new  ResourceNotFoundException("Leave Request", "ID", leaveRequestDTO.getRequestId());
    	}
    }

    @Override
    public void submitLeaveRequest(Employee employee, LeaveRequestDTO leaveRequest)throws InternalServerErrorException {
    	try {
        LeaveRequest newLeaveRequest = modelMapper.map(leaveRequest, LeaveRequest.class);
        Manager managerId = employee.getManager();
        newLeaveRequest.setEmployee(employee);
        newLeaveRequest.setManager(managerId);
        newLeaveRequest.setStatus(ApprovalStatus.PENDING);
        leaveRequestRepository.save(newLeaveRequest);
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to submit leave request");
         }

    }
    
    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByManagerId(Long managerId)throws InternalServerErrorException {
    	try {
        Optional<Manager> managerOptional = managerRepository.findById(managerId);
        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            List<LeaveRequest> leaveRequests = leaveRequestRepository.findByManager(manager);

            // Mapping leave requests to DTOs
            return leaveRequests.stream()
                    .map(leaveRequest -> modelMapper.map(leaveRequest, LeaveRequestDTO.class))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to retrive leave requests");
         }
    }
    
    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByEmployeeId(Long employeeId) throws InternalServerErrorException {
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployee(employee);

                // Mapping leave requests to DTOs
                return leaveRequests.stream()
                        .map(leaveRequest -> modelMapper.map(leaveRequest, LeaveRequestDTO.class))
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve leave requests for employee ID: " + employeeId);
        }
    }



    
    
    
}
