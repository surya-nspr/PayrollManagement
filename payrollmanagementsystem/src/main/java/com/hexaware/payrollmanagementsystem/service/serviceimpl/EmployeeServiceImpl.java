package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AddressDTO;
import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.dto.TimeSheetDTO;
import com.hexaware.payrollmanagementsystem.entity.Address;
import com.hexaware.payrollmanagementsystem.entity.Admin;
import com.hexaware.payrollmanagementsystem.entity.Department;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.LeaveRequest;
import com.hexaware.payrollmanagementsystem.entity.Manager;
import com.hexaware.payrollmanagementsystem.entity.PayrollRecord;
import com.hexaware.payrollmanagementsystem.entity.TimeSheet;
import com.hexaware.payrollmanagementsystem.repository.AddressRepository;
import com.hexaware.payrollmanagementsystem.repository.AdminRepository;
import com.hexaware.payrollmanagementsystem.repository.DepartmentRepository;
import com.hexaware.payrollmanagementsystem.repository.EmployeeRepository;
import com.hexaware.payrollmanagementsystem.repository.LeaveRequestRepository;
import com.hexaware.payrollmanagementsystem.repository.ManagerRepository;
import com.hexaware.payrollmanagementsystem.repository.PayrollRecordRepository;
import com.hexaware.payrollmanagementsystem.repository.TimeSheetRepository;
import com.hexaware.payrollmanagementsystem.service.AuditTrailService;
import com.hexaware.payrollmanagementsystem.service.EmployeeService;
import com.hexaware.payrollmanagementsystem.service.LeaveRequestService;
import com.hexaware.payrollmanagementsystem.service.NotificationService;
import com.hexaware.payrollmanagementsystem.service.TimeSheetService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final LeaveRequestRepository leaveRequestRepository;
    
    private final AddressRepository addressRepository;
    
    private final  TimeSheetRepository timeSheetRepository;

    private final PayrollRecordRepository payrollRecordRepository;
    
    private final ManagerRepository managerRepository;
    
    private final DepartmentRepository departmentRepository;
    
    private final LeaveRequestService leaveRequestService;
    
    private final TimeSheetService timeSheetService;
    
    private final AuditTrailService auditTrailsService;

    private final ModelMapper modelMapper;
    
    private final NotificationService notificationService;
    
    private final AdminRepository adminRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, LeaveRequestRepository leaveRequestRepository,
			TimeSheetRepository timeSheetRepository, PayrollRecordRepository payrollRecordRepository,
			ModelMapper modelMapper,AddressRepository addressRepository,ManagerRepository managerRepository,
			DepartmentRepository departmentRepository, LeaveRequestService leaveRequestService,
			TimeSheetService timeSheetService,AuditTrailService auditTrailsService, NotificationService notificationService,AdminRepository adminRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.leaveRequestRepository = leaveRequestRepository;
		this.timeSheetRepository = timeSheetRepository;
		this.payrollRecordRepository = payrollRecordRepository;
		this.modelMapper = modelMapper;
		this.addressRepository= addressRepository;
		this.managerRepository = managerRepository;
		this.departmentRepository = departmentRepository;
		this.leaveRequestService =  leaveRequestService;
		this.timeSheetService = timeSheetService;
		this.auditTrailsService = auditTrailsService;
		this.notificationService = notificationService;
		this.adminRepository = adminRepository;
	}

	@Override
    public EmployeeDTO getEmployeeById(Long employeeId)throws ResourceNotFoundException {
		try {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        auditTrailsService.logEmployeeActivity("Employee with ID " + employeeId + " retrieved.");
        return modelMapper.map(employee, EmployeeDTO.class);
		}
		catch (Exception e) {
    		throw new  ResourceNotFoundException("Employee", "ID", employeeId);
    	}
    }

    @Override
    public List<EmployeeDTO> getAllEmployees()throws InternalServerErrorException  {
    	try {
        List<Employee> employees = employeeRepository.findAll();
        auditTrailsService.logEmployeeActivity("All employees retrieved.");
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to retrive employees");
         }
    }



    @Override
    public List<TimeSheetDTO> getTimeSheetsByEmployeeId(Long employeeId) throws ResourceNotFoundException{
    	try {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            List<TimeSheet> timeSheets = timeSheetRepository.findByEmployee(employee);
            auditTrailsService.logEmployeeActivity("Time sheets for employee with ID " + employeeId + " retrieved.");
            return timeSheets.stream()
                    .map(timeSheet -> modelMapper.map(timeSheet, TimeSheetDTO.class))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    	}
    	catch (Exception e) {
            throw new ResourceNotFoundException("Employee", "ID", employeeId);
        }
    }


    @Override
    public List<PayrollRecordDTO> getPayrollRecordsByEmployeeId(Long employeeId)throws ResourceNotFoundException {
    	try {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        auditTrailsService.logEmployeeActivity("Payroll Records for employee with ID " + employeeId + " retrieved.");
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            List<PayrollRecord> payrollRecords = payrollRecordRepository.findByEmployee(employee);
            
            return payrollRecords.stream()
                    .map(payrollRecord -> modelMapper.map(payrollRecord, PayrollRecordDTO.class))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    	}
    	catch (Exception e) {
            throw new ResourceNotFoundException("Employee", "ID", employeeId);
        }
    }
    
    @Override
    public void deleteEmployeeById(Long employeeId) throws ResourceNotFoundException{
    	try {
    	Employee employee = employeeRepository.findById(employeeId)
    	        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

    	    // Delete associated leave requests
    	    List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployee(employee);
    	    leaveRequestRepository.deleteAll(leaveRequests);
        employeeRepository.deleteById(employeeId);
        auditTrailsService.logEmployeeActivity("Employee with ID " + employeeId + " deleted.");
    }
   
    catch (Exception e) {
        throw new ResourceNotFoundException("Employee", "ID", employeeId);
    }
    }

    
    @Override
    public void addEmployeeInformation(EmployeeDTO employeeDTO)throws BadRequestException {
    	try {
    	Employee employee = modelMapper.map(employeeDTO, Employee.class);
    
    	Address address = new Address();
    	address.setStreetAddress(employeeDTO.getAddress().getStreetAddress());
    	address.setCity(employeeDTO.getAddress().getCity());
    	address.setState(employeeDTO.getAddress().getState());
    	address.setPostalCode(employeeDTO.getAddress().getPostalCode());
    	address.setCountry(employeeDTO.getAddress().getCountry());

    	Address savedAddress = addressRepository.save(address);

    	Manager manager = managerRepository.findById(employeeDTO.getManager().getManagerId()).orElse(null);

    	Department department = departmentRepository.findById(employeeDTO.getDepartment().getDepartmentId()).orElse(null);

    	if (manager != null && department != null) {

    		employee.setAddress(savedAddress);
    		employee.setManager(manager);
    		employee.setDepartment(department);
    		employee.setTotalLeaves(24L);
    		employee.setDateOfJoining(LocalDate.now());

    		employeeRepository.save(employee);
    	} else {

    		StringBuilder errorMsg = new StringBuilder("Unable to add employee due to missing or invalid data: ");

        if (manager == null) {
            errorMsg.append("Manager not found with ID: ").append(employeeDTO.getManager().getManagerId()).append(". ");
        }
        if (department == null) {
            errorMsg.append("Department not found with ID: ").append(employeeDTO.getDepartment().getDepartmentId()).append(". ");
        }

        addressRepository.delete(savedAddress);

        throw new IllegalArgumentException(errorMsg.toString());
        }
    	auditTrailsService.logEmployeeActivity("New employee added with ID " + employeeDTO.getEmployeeId() + ".");
    }
    
    catch (Exception e) {
		throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to add employee inofrmation", e.getMessage());
	}
    
}
    
 
    @Override
    public void manageEmployeeInformation(EmployeeDTO employeeDTO, List<String> fieldsToUpdate) {
        // Check if the employeeDTO contains a valid employeeId
        Long employeeId = employeeDTO.getEmployeeId();
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null.");
        }

        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(employeeId);

        existingEmployeeOptional.ifPresent(existingEmployee -> {
            Employee updatedEmployee = modelMapper.map(employeeDTO, Employee.class);

            for (String field : fieldsToUpdate) {
                switch (field) {
                    case "emailId":
                        existingEmployee.setEmailId(updatedEmployee.getEmailId());
                        break;
                    case "position":
                        existingEmployee.setPosition(updatedEmployee.getPosition());
                        break;
                    case "phoneNumber":
                        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
                        break;
                    case "department":
                    	 if (employeeDTO.getDepartment() != null) {
                             // Fetch the department from the repository based on the provided department ID
                             Optional<Department> existingDepartmentOptional = departmentRepository.findById(employeeDTO.getDepartment().getDepartmentId());
                             existingDepartmentOptional.ifPresent(existingDepartment -> {
                                 // Update the employee's department
                                 existingEmployee.setDepartment(existingDepartment);
                             });
                         }
                        break;
                    case "salary":
                        existingEmployee.setSalary(updatedEmployee.getSalary());
                        break;
                    case "manager":
                        if (employeeDTO.getManager() != null) {
                            Optional<Manager> existingManagerOptional = managerRepository.findById(employeeDTO.getManager().getManagerId());
                            existingManagerOptional.ifPresent(existingManager -> {
                                existingEmployee.setManager(existingManager);
                            });
                        }
                        break;
                    case "address":
                    	AddressDTO addressDTO = employeeDTO.getAddress();
       	             if (addressDTO != null) {
       	                 Address address = existingEmployee.getAddress();
       	                 if (address == null) {
       	                     address = new Address();
       	                 }
       	                 address.setStreetAddress(addressDTO.getStreetAddress());
       	                 address.setCity(addressDTO.getCity());
       	                 address.setState(addressDTO.getState());
       	                 address.setPostalCode(addressDTO.getPostalCode());
       	                 address.setCountry(addressDTO.getCountry());
       	              existingEmployee.setAddress(address);
       	             }
                        break;
                    default:
                        // Handle unknown field
                    	System.out.println("Fields not updated");
                        break;
                }
            }

            // Save the updated employee
            employeeRepository.save(existingEmployee);
        });
        auditTrailsService.logEmployeeActivity("Employee with ID " + employeeId + " details updated.");
        
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            String employeeEmail = employee.getEmailId(); // Get employee's email address
            String subject = "Employee Information Updated";
            String body = "Dear Employee,\n\nYour information has been updated.";

            notificationService.sendEmail(employeeEmail, subject, body);
        } else {
            throw new InternalServerErrorException("Employee details not found.");
        }
    }
	
    
    @Override
    public void submitLeaveRequest(Employee employee, LeaveRequestDTO leaveRequestDTO) {
        // Delegate the call to leaveRequestService
        leaveRequestService.submitLeaveRequest(employee, leaveRequestDTO);
        auditTrailsService.logEmployeeActivity("Leave request submitted by employee with ID " + employee.getEmployeeId() + ".");
        
        sendEmployeeNotification(employee.getEmailId(), "Leave Request Submitted", "Your leave request has been submitted successfully.");
        
        sendAdminNotification("Leave Request Submitted", "Your leave request has been submitted successfully."+ employee.getEmployeeId());
    }
    
    @Override
    public void submitTimeSheet(Employee employee, TimeSheetDTO timeSheetDTO) throws ResourceNotFoundException{
       
    	try{// Delegate the call to timeSheetService
        timeSheetService.submitTimeSheet(employee, timeSheetDTO);
        auditTrailsService.logEmployeeActivity("Time sheet submitted by employee with ID " + employee.getEmployeeId() + ".");
        
        sendEmployeeNotification(employee.getEmailId(), "Time Sheet Submitted", "Your time sheet has been submitted successfully.");
        
        sendAdminNotification("Time Sheet Submitted", "Your time sheet has been submitted successfully."+ employee.getEmployeeId());
    	}
    	catch (Exception e) {
            throw new ResourceNotFoundException("Employee", "ID", employee.getEmployeeId());
        }
    }

    @Override
    public void updateTimeSheet(Long timeSheetId, TimeSheetDTO updatedTimeSheetDTO) throws ResourceNotFoundException {
        // Delegate the call to timeSheetService
        try {
			timeSheetService.updateTimeSheet(timeSheetId, updatedTimeSheetDTO);
			
			//sendAdminNotification("Time Sheet Updated", "Your time sheet has been updated successfully."+ updatedTimeSheetDTO.getEmployee().getEmployeeId());
			
			 //sendEmployeeNotification(updatedTimeSheetDTO.getEmployee().getEmailId(), "Time Sheet Updated", "Your time sheet has been updated successfully.");
			 sendAdminNotification("Time Sheet Updated", "Your time sheet has been updated successfully.");
			 
		       // sendEmployeeNotification(employee.getEmailId(), "Time Sheet Updated", "Your time sheet has been updated successfully.");
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Failed to get time sheet","with Id: ",timeSheetId);
		}
        auditTrailsService.logEmployeeActivity("Time sheet with ID " + timeSheetId + " updated.");
    }
    
    @Override
    public int getTotalLeavesByEmployeeId(Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "ID", employeeId));
        return Math.toIntExact(employee.getTotalLeaves());
    }
    
    private void sendAdminNotification(String subject, String body) {
        Optional<Admin> adminOptional = adminRepository.findById(1L); // Assuming admin ID is 1
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            String adminEmail = admin.getEmailId(); // Get admin's email address

            // Send notification to admin
            notificationService.sendEmail(adminEmail, subject, body);
        }
    }
    
    private void sendEmployeeNotification(String recipientEmail, String subject, String body) {
        notificationService.sendEmail(recipientEmail, subject, body);
    }
    
    @Override
    public List<LeaveRequestDTO> getLeavesByEmployeeId(Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "ID", employeeId));

        // Call the leaveRequestService to get leave requests for the employee
        return leaveRequestService.getLeaveRequestsByEmployeeId(employeeId);
    }

}