package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollPolicyDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollProcessorDTO;
import com.hexaware.payrollmanagementsystem.dto.ComplianceReportDTO;
import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;
import com.hexaware.payrollmanagementsystem.dto.UserDTO;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AdminDTO;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.entity.Admin;
import com.hexaware.payrollmanagementsystem.entity.PayrollProcessor;
import com.hexaware.payrollmanagementsystem.entity.User;
import com.hexaware.payrollmanagementsystem.repository.AdminRepository;
import com.hexaware.payrollmanagementsystem.repository.PayrollProcessorRepository;
import com.hexaware.payrollmanagementsystem.repository.UserRepository;
import com.hexaware.payrollmanagementsystem.service.AdminService;
import com.hexaware.payrollmanagementsystem.service.AuditTrailService;
import com.hexaware.payrollmanagementsystem.service.CompilanceReportService;
import com.hexaware.payrollmanagementsystem.service.DepartmentService;
import com.hexaware.payrollmanagementsystem.service.EmployeeService;
import com.hexaware.payrollmanagementsystem.service.ManagerService;
import com.hexaware.payrollmanagementsystem.service.NotificationService;
import com.hexaware.payrollmanagementsystem.service.PayrollPolicyService;
import com.hexaware.payrollmanagementsystem.service.PayrollProcessorService;
import com.hexaware.payrollmanagementsystem.service.UserService;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.List;
import org.modelmapper.ModelMapper;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    
    private final UserRepository userRepository;

    private final CompilanceReportService compilanceReportService ;
    
    private final AdminRepository adminRepository;
    
    private final ModelMapper modelMapper;
    
    private final PayrollProcessorRepository payrollProcessorRepository;
    
    private final EmployeeService employeeService;
    
    private final ManagerService managerService;
    
    private final PayrollProcessorService payrollProcessorService;
    
    private final DepartmentService departmentService;
    
    private final UserService userService;
    
    private final AuditTrailService auditTrailService;
    
    private final PayrollPolicyService payrollPolicyService;
    
    private final NotificationService notificationService;
    
    @Autowired
    public AdminServiceImpl(UserRepository userRepository, CompilanceReportService compilanceReportService,
			AdminRepository adminRepository, ModelMapper modelMapper,
			PayrollProcessorRepository payrollProcessorRepository, EmployeeService employeeService,
			ManagerService managerService, PayrollProcessorService payrollProcessorService,
			DepartmentService departmentService, UserService userService, AuditTrailService auditTrailService,
			PayrollPolicyService payrollPolicyService, NotificationService notificationService) {
		super();
		this.userRepository = userRepository;
		this.compilanceReportService = compilanceReportService;
		this.adminRepository = adminRepository;
		this.modelMapper = modelMapper;
		this.payrollProcessorRepository = payrollProcessorRepository;
		this.employeeService = employeeService;
		this.managerService = managerService;
		this.payrollProcessorService = payrollProcessorService;
		this.departmentService = departmentService;
		this.userService = userService;
		this.auditTrailService = auditTrailService;
		this.payrollPolicyService = payrollPolicyService;
		this.notificationService = notificationService;
	}
    
    @Override
    public void addAdmin(AdminDTO adminDTO) throws InternalServerErrorException {
    	
    	try {
    		Admin admin = modelMapper.map(adminDTO, Admin.class);
        	adminRepository.save(admin);
        	sendAdminNotification("Admin Added", "Dear Admin,\n\nYou have been added as an admin successfully.");
        	
        	auditTrailService.logAdminActivity("Added Admins: " + admin.getFirstName());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Failed to add admin.");
        }
    }

	@Override
	 public void addManager(ManagerDTO managerDTO) throws InternalServerErrorException {
		 
		 try {
			 managerService.addManager(managerDTO);
			 
			 auditTrailService.logAdminActivity("Added manager: " + managerDTO.getFirstName());
			 
			 sendAdminNotification("Manager Added", "Dear Admin,\n\nA new manager has been added: " + managerDTO.getFirstName());
		 }catch (Exception ex) {
	            throw new InternalServerErrorException("Failed to add manager.");
	        }
	}
		 
	 
	 @Override
	 public void updateManager(ManagerDTO managerDTO, List<String> fieldsToUpdate)throws InternalServerErrorException {
		 try {
			 Long managerId = managerDTO.getManagerId();
			 if (managerId == null) {
				 throw new IllegalArgumentException("Manager ID cannot be null.");
				 }
			 managerService.updateManager(managerDTO, fieldsToUpdate);
			 
			 String managerEmail = managerDTO.getEmailId();
		        String subject = "Manager Updated";
		        String body = "Dear Manager,\n\nYour profile has been updated.";

		        notificationService.sendEmail(managerEmail, subject, body);
			 
		        sendAdminNotification("Manager Updated", "Dear Admin,\n\nA new manager has been updated: " + managerDTO.getFirstName());
		        
		     auditTrailService.logAdminActivity("Updated manager with ID " + managerId + " with fields: " + fieldsToUpdate);
		 }catch (Exception ex) {
	            throw new InternalServerErrorException("Failed to update manager.");
	        }
	}
	 
	@Override
	public void addEmployeeInformation(EmployeeDTO employeeDTO) throws InternalServerErrorException{
	   try {
		   employeeService.addEmployeeInformation(employeeDTO);
		   auditTrailService.logAdminActivity("Added employee: " + employeeDTO.getFirstName());
		   sendAdminNotification("Employee Added", "Dear Admin,\n\nA new Employee has been added: " + employeeDTO.getFirstName());
       } catch (Exception ex) {
           throw new InternalServerErrorException("Failed to add employee information.");
       }
	}

	@Override
	public void manageEmployeeInformation(EmployeeDTO employeeDTO, List<String> fieldsToUpdate) throws InternalServerErrorException{
	   
	   try {
		   employeeService.manageEmployeeInformation(employeeDTO, fieldsToUpdate);
		   sendAdminNotification("Employee Managed", "Dear Admin,\n\nEmployee information has been managed: " + employeeDTO.getFirstName());
	        
		   auditTrailService.logAdminActivity("Managed employee: " + employeeDTO.getFirstName() + " with fields: " + fieldsToUpdate);
       } catch (Exception ex) {
           throw new InternalServerErrorException("Failed to manage employee information.");
       }
	}
	
	
	
	@Override
    public void deleteEmployee(Long employeeId) throws ResourceNotFoundException, InternalServerErrorException{
        try {
        	employeeService.deleteEmployeeById(employeeId);
           
        	auditTrailService.logAdminActivity("Deleted employee with ID " + employeeId);
            
        	sendAdminNotification("Employee Deleted", "Dear Admin,\n\nEmployee has been deleted: " + employeeId);
        } catch (Exception ex) {
            throw new InternalServerErrorException("Failed to delete employee.");
        }
    }
	
	@Override
    public void addPayrollProcessor(PayrollProcessorDTO payrollProcessorDTO)throws InternalServerErrorException {
        try {
        	payrollProcessorService.addPayrollProcessor(payrollProcessorDTO);
        	
            auditTrailService.logAdminActivity("Added payroll processor: " + payrollProcessorDTO.getFirstName());
            
            sendAdminNotification("Payroll Processor Added", "Dear Admin,\n\nA new Payroll Processor has been added: " + payrollProcessorDTO.getFirstName());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Failed to add payroll processor.");
        }
    }

  
	@Override
	public void updatePayrollProcessor(PayrollProcessorDTO payrollProcessorDTO, List<String> fieldsToUpdate) throws InternalServerErrorException {
	    try {
	        Long payrollProcessorId = payrollProcessorDTO.getProcessorId();
	        if (payrollProcessorId == null) {
	            throw new IllegalArgumentException("Payroll Processor ID cannot be null.");
	        }
	        payrollProcessorService.updatePayrollProcessor(payrollProcessorDTO, fieldsToUpdate);
	        auditTrailService.logAdminActivity("Updated payroll processor with ID " + payrollProcessorId + " with fields: " + fieldsToUpdate);

	        // Send notification to payroll processor about the update
	        sendAdminNotification("Payroll Processor Updated", "Dear Admin,\n\nA Payroll Processor has been updated: " + payrollProcessorDTO.getProcessorId());
	        
	        Optional<PayrollProcessor> payrollProcessorOptional = payrollProcessorRepository.findById(payrollProcessorId);
	        if (payrollProcessorOptional.isPresent()) {
	            PayrollProcessor payrollProcessor = payrollProcessorOptional.get();
	            String processorEmail = payrollProcessor.getEmailId(); // Get processor's email address
	            String subject = "Payroll Processor Details Updated";
	            String body = "Dear Payroll Processor,\n\nYour details have been updated.";

	            notificationService.sendEmail(processorEmail, subject, body);
	            
	            
	        } else {
	            throw new InternalServerErrorException("Payroll processor details not found.");
	        }
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Failed to update payroll processor details.");
	    }
	}
    
    @Override
    public void addDepartment(DepartmentDTO departmentDTO) throws InternalServerErrorException {
       
        try {
        	departmentService.addDepartment(departmentDTO);
        	
            auditTrailService.logAdminActivity("Added department: " + departmentDTO.getDepartmentName());
            
            sendAdminNotification("Department Added", "Dear Admin,\n\nA new Department has been added: " + departmentDTO.getDepartmentName());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Failed to add department.");
        }
    }

  
    @Override
    public void updateDepartment(Long departmentId, DepartmentDTO departmentDTO) throws InternalServerErrorException {
        try {
        	departmentService.updateDepartment(departmentId, departmentDTO);
        	
            auditTrailService.logAdminActivity("Updated department with ID " + departmentId + " with new name: " + departmentDTO);
            
            sendAdminNotification("Department Updated", "Dear Admin,\n\nA Department has been updated: " + departmentDTO.getDepartmentId());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Failed to update department.");
        }
    }
	
    @Override
    public void addUser(UserDTO userDTO) throws InternalServerErrorException {
        try {
        	userService.addUser(userDTO);
            auditTrailService.logAdminActivity("Added user: " + userDTO.getUsername());
            
            sendAdminNotification("User Added", "Dear Admin,\n\nA new User has been added: " + userDTO.getUserId());
        } catch (Exception ex) {
            throw new InternalServerErrorException("Failed to add user."+ex.getMessage());
        }
    }

    @Override
    public void updateUser(Long userId, UserDTO updatedUserDTO) throws ResourceNotFoundException {
        try {
            userService.updateUser(userId, updatedUserDTO);
            
            sendAdminNotification("User Updated", "Dear Admin,\n\nA user has been updated: " + updatedUserDTO.getUserId());
            
	        Optional<User> userOptional = userRepository.findById(userId);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            String userEmail = user.getEmail(); // Get user's email address
	            String subject = "User Details Updated";
	            String body = "Dear User,\n\nYour details have been updated.";

	            notificationService.sendEmail(userEmail, subject, body);
	        } else {
	            throw new ResourceNotFoundException("User not found with", " id: ", userId);
	        }
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with"," id: " , userId);
        }
    }

    @Override
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        try {
            userService.deleteUser(userId);
            
            sendAdminNotification("User Deleted", "Dear Admin,\n\nA User has been deleted: " + userId);
            
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with"," id: " , userId);
        }
    }
    
	 @Override
	 public ComplianceReportDTO generateComplianceReport() throws InternalServerErrorException {
	    	try {
	    		ComplianceReportDTO report = compilanceReportService.generateComplianceReport(); // Assuming a method to retrieve the generated report
		    	auditTrailService.logAdminActivity("Generated all Compliance Reports");
		    	
		    	 sendAdminNotification("Compliance Report Generated", "Dear Admin,\n\nA Compliance Report has been generated.");
		    	 
		        return modelMapper.map(report, ComplianceReportDTO.class);
	    
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Failed to generate compliance report.");
	        }
	 }
	 
	 @Override
	 public List<ComplianceReportDTO> getAllComplianceReports() throws InternalServerErrorException {
	     try {
	    	 // Use the CompilanceReportService to fetch all compliance reports
		     List<ComplianceReportDTO> complianceReports = compilanceReportService.getAllComplianceReports();
		     // Return the list of compliance reports
		     auditTrailService.logAdminActivity("Retrieved all Compliance Reports");
		     return complianceReports;
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Failed to retrieve compliance reports.");
	        }
	 }
	 
	 @Override
	 public void addPayrollPolicy(PayrollPolicyDTO payrollPolicyDTO) {
	     payrollPolicyService.addPayrollPolicy(payrollPolicyDTO);
	     auditTrailService.logAdminActivity("Added payroll policy: " + payrollPolicyDTO.getPolicyName()); 
	     
	     Optional<Admin> adminOptional = adminRepository.findById(1L); // Assuming admin ID is 1
	        if (adminOptional.isPresent()) {
	            Admin admin = adminOptional.get();
	            String adminEmail = admin.getEmailId(); // Get admin's email address

	            // Send notification to admin about the manager addition
	            String adminSubject = "Policy added";
	            String adminBody = "Dear Admin,\n\nA new Policy has been added: " + payrollPolicyDTO.getPolicyName();

	            notificationService.sendEmail(adminEmail, adminSubject, adminBody);
    }
	     
	 }

	 @Override
	 public void updatePayrollPolicy(Long policyId, PayrollPolicyDTO updatedPolicyDTO) throws ResourceNotFoundException{
	     payrollPolicyService.updatePayrollPolicy(policyId, updatedPolicyDTO);
	     auditTrailService.logAdminActivity("Updated payroll policy with ID " + policyId);
	     Optional<Admin> adminOptional = adminRepository.findById(1L); // Assuming admin ID is 1
	        if (adminOptional.isPresent()) {
	            Admin admin = adminOptional.get();
	            String adminEmail = admin.getEmailId(); // Get admin's email address

	            // Send notification to admin about the manager addition
	            String adminSubject = "Policy updated";
	            String adminBody = "Dear Admin,\n\nA Policy has been updated: " + updatedPolicyDTO.getPolicyId();

	            notificationService.sendEmail(adminEmail, adminSubject, adminBody);
 }
	 }

	   @Override
	    public List<AuditTrailDTO> getAllAuditTrails() {
	    	List<AuditTrailDTO> auditTrails = auditTrailService.getAllAuditTrails();
	    	auditTrailService.logAdminActivity("Retrieved all audit trails");
		     return auditTrails;
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
}
