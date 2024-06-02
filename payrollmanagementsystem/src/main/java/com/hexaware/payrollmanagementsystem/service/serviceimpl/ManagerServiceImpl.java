package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.entity.Address;
import com.hexaware.payrollmanagementsystem.entity.Admin;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.LeaveRequest;
import com.hexaware.payrollmanagementsystem.entity.Manager;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AddressDTO;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;
import com.hexaware.payrollmanagementsystem.service.AuditTrailService;
import com.hexaware.payrollmanagementsystem.service.LeaveRequestService;
import com.hexaware.payrollmanagementsystem.service.ManagerService;
import com.hexaware.payrollmanagementsystem.service.NotificationService;
import com.hexaware.payrollmanagementsystem.service.PayrollRecordService;
import com.hexaware.payrollmanagementsystem.repository.AddressRepository;
import com.hexaware.payrollmanagementsystem.repository.AdminRepository;
import com.hexaware.payrollmanagementsystem.repository.LeaveRequestRepository;
import com.hexaware.payrollmanagementsystem.repository.ManagerRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ModelMapper modelMapper;
    private final LeaveRequestRepository leaveRequestRepository;
    private final AddressRepository addressRepository;
    private final ManagerRepository managerRepository;
    private final PayrollRecordService payrollRecordService;
    private final LeaveRequestService leaveRequestService;
    private final AuditTrailService auditTrailsService;
    private final NotificationService notificationService;
    private final AdminRepository adminRepository;
    
    @Autowired
    public ManagerServiceImpl(ModelMapper modelMapper, LeaveRequestRepository leaveRequestRepository,
			AddressRepository addressRepository, ManagerRepository managerRepository,
			PayrollRecordService payrollRecordService, LeaveRequestService leaveRequestService,
			AuditTrailService auditTrailsService, NotificationService notificationService,
			AdminRepository adminRepository) {
		super();
		this.modelMapper = modelMapper;
		this.leaveRequestRepository = leaveRequestRepository;
		this.addressRepository = addressRepository;
		this.managerRepository = managerRepository;
		this.payrollRecordService = payrollRecordService;
		this.leaveRequestService = leaveRequestService;
		this.auditTrailsService = auditTrailsService;
		this.notificationService = notificationService;
		this.adminRepository = adminRepository;
	}
    
    @Override
    public void addManager(ManagerDTO managerDTO)throws InternalServerErrorException {
    	try {

        Manager manager = modelMapper.map(managerDTO, Manager.class);
        
        AddressDTO addressDTO = managerDTO.getAddress();
        Address address = new Address();
        address.setStreetAddress(addressDTO.getStreetAddress());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCountry(addressDTO.getCountry());

        Address savedAddress = addressRepository.save(address);
        
        manager.setAddress(savedAddress);
        
        managerRepository.save(manager);
        auditTrailsService.logManagerActivity("New manager added with ID " + managerDTO.getManagerId() + ".");
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to add manager");
         }
    }
    
    @Override
	 public void updateManager(ManagerDTO managerDTO, List<String> fieldsToUpdate) {
		 
	     Long managerId = managerDTO.getManagerId();
	     if (managerId == null) {
	         throw new IllegalArgumentException("Manager ID cannot be null.");
	     }

	     Optional<Manager> existingManagerOptional = managerRepository.findById(managerId);

	     existingManagerOptional.ifPresent(existingManager -> {

	         Manager updatedManager = modelMapper.map(managerDTO, Manager.class);

	         if (fieldsToUpdate.contains("firstName")) {
	             existingManager.setFirstName(updatedManager.getFirstName());
	         }
	         if (fieldsToUpdate.contains("lastName")) {
	             existingManager.setLastName(updatedManager.getLastName());
	         }
	         if (fieldsToUpdate.contains("emailId")) {
	             existingManager.setEmailId(updatedManager.getEmailId());
	         }
	         if (fieldsToUpdate.contains("address")) {

	             AddressDTO addressDTO = managerDTO.getAddress();
	             if (addressDTO != null) {
	                 Address address = existingManager.getAddress();
	                 if (address == null) {
	                     address = new Address();
	                 }
	                 address.setStreetAddress(addressDTO.getStreetAddress());
	                 address.setCity(addressDTO.getCity());
	                 address.setState(addressDTO.getState());
	                 address.setPostalCode(addressDTO.getPostalCode());
	                 address.setCountry(addressDTO.getCountry());
	                 existingManager.setAddress(address);
	             }
	         }

	         managerRepository.save(existingManager);
	     });
	     auditTrailsService.logManagerActivity("Manager details updated for manager with ID " + managerDTO.getManagerId() + ".");
	 }
    
    @Override
    public List<PayrollRecordDTO> reviewTeamPayrolls(Long managerId)throws ResourceNotFoundException {
    	try {
        // Delegate the call to payrollRecordService
    	 auditTrailsService.logManagerActivity("Payrolls reviewed by manager with ID " + managerId + ".");
        return payrollRecordService.reviewTeamPayrolls(managerId);
    	}
    	catch (Exception e) {
    		throw new ResourceNotFoundException( "Failed to retrieve payrolls","with Id: " ,managerId);
    	}
    }
    

    @Override
    public void rejectLeaveRequest(LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestDTO.getRequestId())
                    .orElseThrow(() -> new ResourceNotFoundException("Leave request not found ", "with ID: ", leaveRequestDTO.getRequestId()));

            Employee employee = leaveRequest.getEmployee();
            String employeeEmail = employee.getEmailId();

            auditTrailsService.logManagerActivity("Leave request rejected with ID " + leaveRequestDTO.getRequestId() + ".");

            leaveRequestService.rejectLeaveRequest(leaveRequestDTO);

            String subject = "Leave Request Rejected";
            String body = "Dear Employee,\n\nYour leave request has been rejected.";

            // Send the rejection notification email to the employee
            notificationService.sendEmail(employeeEmail, subject, body);
           
            sendAdminNotification("Leave Request Rejected - Employee ID: " + employee.getEmployeeId(), "Dear Admin,\n\nThe leave request for employee ID: " + employee.getEmployeeId() + " has been rejected.");
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            throw new ResourceNotFoundException("Leave request ID not found", "ID", leaveRequestDTO.getRequestId());
        }
    }

    
    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByManagerId(Long managerId)throws ResourceNotFoundException {
    	try {
    	auditTrailsService.logManagerActivity("Retrieved leave requests for manager with ID " + managerId + ".");
    	return leaveRequestService.getLeaveRequestsByManagerId(managerId);
    	}
    	catch (Exception e) {
			throw new  ResourceNotFoundException("Leave Request id not found", "ID", managerId);
    	}
    }
    
    @Override
    public ManagerDTO getManagerById(Long managerId)throws InternalServerErrorException,ResourceNotFoundException {
    	try {
        Optional<Manager> managerOptional = managerRepository.findById(managerId);
        auditTrailsService.logManagerActivity("Retrieved manager details for manager with ID " + managerId + ".");
        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            return modelMapper.map(manager, ManagerDTO.class);
        } else {
            throw new IllegalArgumentException("Manager not found with ID: " + managerId);
        }
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to retrieve manager by ID");
         }
    }
    
    @Override
    public List<ManagerDTO> getAllManagers() throws InternalServerErrorException {
        try {
            List<Manager> managers = managerRepository.findAll();
            if (managers.isEmpty()) {
                throw new ResourceNotFoundException("No managers found.",null,null);
            }

            auditTrailsService.logManagerActivity("Retrieved all managers.");

            return managers.stream()
                    .map(manager -> modelMapper.map(manager, ManagerDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to retrieve all managers.");
        }
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
    
    @Override
    public void approveLeaveRequest(LeaveRequestDTO leaveRequestDTO)throws ResourceNotFoundException {
    	try {
        // Delegate the call to leaveRequestService
        leaveRequestService.approveLeaveRequest(leaveRequestDTO);
        auditTrailsService.logManagerActivity("Leave request approved with ID " + leaveRequestDTO.getRequestId() + ".");
        
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestDTO.getRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found ", "with ID: ", leaveRequestDTO.getRequestId()));

        Employee employee = leaveRequest.getEmployee();
        String employeeEmail = employee.getEmailId();
        

        String subject = "Leave Request approved";
        String body = "Dear Employee,\n\nYour leave request has been approved.";

        // Send the rejection notification email to the employee
        notificationService.sendEmail(employeeEmail, subject, body);
       
        sendAdminNotification("Leave Request Rejected - Employee ID: " + employee.getEmployeeId(), "Dear Admin,\n\nThe leave request for employee ID: " + employee.getEmployeeId() + " has been approved.");


    	}
    	catch (Exception e) {
			throw new  ResourceNotFoundException("Leave Request id not found", "ID", leaveRequestDTO.getRequestId());
    	}
    }

}