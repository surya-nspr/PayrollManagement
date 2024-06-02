package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.dto.BenefitsDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollProcessorDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.entity.Admin;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.PayrollProcessor;
import com.hexaware.payrollmanagementsystem.entity.PayrollRecord;
import com.hexaware.payrollmanagementsystem.repository.AdminRepository;
import com.hexaware.payrollmanagementsystem.repository.PayrollProcessorRepository;
import com.hexaware.payrollmanagementsystem.repository.PayrollRecordRepository;
import com.hexaware.payrollmanagementsystem.service.AuditTrailService;
import com.hexaware.payrollmanagementsystem.service.BenefitsService;
import com.hexaware.payrollmanagementsystem.service.NotificationService;
import com.hexaware.payrollmanagementsystem.service.PayrollProcessorService;
import com.hexaware.payrollmanagementsystem.service.PayrollRecordService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PayrollProcessorServiceImpl implements PayrollProcessorService {

    private final PayrollRecordRepository payrollRecordRepository;
    private final ModelMapper modelMapper;

    private final PayrollProcessorRepository payrollProcessorRepository;
    private final PayrollRecordService payrollRecordService;
    private final BenefitsService benefitsService;
    private final AuditTrailService auditTrailsService;
    private final NotificationService notificationService;
    private final AdminRepository adminRepository;
    
	@Autowired
    public PayrollProcessorServiceImpl(PayrollRecordRepository payrollRecordRepository, ModelMapper modelMapper,
			PayrollProcessorRepository payrollProcessorRepository, PayrollRecordService payrollRecordService,
			BenefitsService benefitsService, AuditTrailService auditTrailsService,
			NotificationService notificationService, AdminRepository adminRepository) {
		super();
		this.payrollRecordRepository = payrollRecordRepository;
		this.modelMapper = modelMapper;
		this.payrollProcessorRepository = payrollProcessorRepository;
		this.payrollRecordService = payrollRecordService;
		this.benefitsService = benefitsService;
		this.auditTrailsService = auditTrailsService;
		this.notificationService = notificationService;
		this.adminRepository = adminRepository;
	}
 
    @Override
	 public void addPayrollProcessor(PayrollProcessorDTO payrollProcessorDTO)throws InternalServerErrorException {
   	try {

	     PayrollProcessor payrollProcessor = modelMapper.map(payrollProcessorDTO, PayrollProcessor.class);

	     payrollProcessorRepository.save(payrollProcessor);
	     auditTrailsService.logPayrollProcessorActivity("Payroll processor added.");
   	}
   	catch (Exception e) {
        	throw new InternalServerErrorException("Internal server error.Failed to add payroll processor");
        }
	 }

	 @Override
	 public void updatePayrollProcessor(PayrollProcessorDTO payrollProcessorDTO, List<String> fieldsToUpdate) {
	  
	     Long payrollProcessorId = payrollProcessorDTO.getProcessorId();
	     if (payrollProcessorId == null) {
	         throw new IllegalArgumentException("Payroll Processor ID cannot be null.");
	     }

	     Optional<PayrollProcessor> existingPayrollProcessorOptional = payrollProcessorRepository.findById(payrollProcessorId);

	     existingPayrollProcessorOptional.ifPresent(existingPayrollProcessor -> {
	
	         PayrollProcessor updatedPayrollProcessor = modelMapper.map(payrollProcessorDTO, PayrollProcessor.class);

	         if (fieldsToUpdate.contains("firstName")) {
	             existingPayrollProcessor.setFirstName(updatedPayrollProcessor.getFirstName());
	         }
	         if (fieldsToUpdate.contains("lastName")) {
	             existingPayrollProcessor.setLastName(updatedPayrollProcessor.getLastName());
	         }
	         if (fieldsToUpdate.contains("emailId")) {
	             existingPayrollProcessor.setEmailId(updatedPayrollProcessor.getEmailId());
	         }
	         if (fieldsToUpdate.contains("phoneNumber")) {
	             existingPayrollProcessor.setPhoneNumber(updatedPayrollProcessor.getPhoneNumber());
	         }
	        
	         payrollProcessorRepository.save(existingPayrollProcessor);
	     });
	     auditTrailsService.logPayrollProcessorActivity("Payroll processor updated.");
	 }

	 @Override
	 public PayrollRecordDTO verifyPayrollData() throws InternalServerErrorException {
		 try {
	     // Call the verifyPayrollData method from PayrollProcessorService
		 auditTrailsService.logPayrollProcessorActivity("Payroll data verified.");
		 
		 sendPayrollProcessorNotifications("Payroll Data Verified", "Payroll data has been verified and is ready for processing.", 1L); // Assuming payroll processor ID is 1
		 
		 sendAdminNotification("Payroll Data Verified", "Payroll data has been verified and is ready for processing.");
	     return payrollRecordService.verifyPayrollData();
		 }
		 catch (Exception e) {
	         	throw new InternalServerErrorException("Internal server error.Failed to verify");
	         }
	 }
	 
	 @Override
	 public PayrollRecordDTO calculatePayroll()throws InternalServerErrorException {
		 try {
	     // Call the calculatePayroll method from PayrollProcessorService
		 auditTrailsService.logPayrollProcessorActivity("Payroll calculated.");
		 
		 sendAdminNotification("Payroll Calculated", "Payroll for the current month has been calculated successfully.");

		 sendPayrollProcessorNotifications("Payroll Calculated", "Payroll for the current month has been calculated successfully.", 1L); // Assuming payroll processor ID is 1
	     return payrollRecordService.calculatePayroll();
		 }
		 catch (Exception e) {
	         	throw new InternalServerErrorException("Internal server error.Failed to Calculate"+e.getMessage());
	         }
	 }
    
    @Override
    public void processPayments() throws BadRequestException {
        try {
            List<PayrollRecord> payrollRecords = payrollRecordRepository.findAll();

            for (PayrollRecord payrollRecord : payrollRecords) {
                // Check if the payroll record status is "Verified"
                if ("VERIFIED".equals(payrollRecord.getStatus())) {
                    Employee employee = payrollRecord.getEmployee();
                    BigDecimal netSalary = payrollRecord.getNetSalary();

                    boolean paymentSuccessful = initiatePayment(employee, netSalary);

                    if (paymentSuccessful) {
                        payrollRecord.setStatus("PAID");
                    } else {
                        payrollRecord.setStatus("Failed");
                    }

                    payrollRecordRepository.save(payrollRecord);
                    auditTrailsService.logPayrollProcessorActivity("Payments processed successfully."+payrollRecord.getEmployee().getEmployeeId());
                    
                    sendPayrollProcessorNotifications("Payments Processed", "Payments for verified payroll records have been successfully processed.", 1L); // Assuming payroll processor ID is 1
                    
                    sendAdminNotification("Payments Processed", "Payments for verified payroll records have been successfully processed.");

                } else {
                    // Log a message indicating that the payroll record is not verified
                    System.out.println("Payroll record not verified for employee: " + payrollRecord.getEmployee().getEmployeeId());
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to Process payments", e.getMessage());
        }
    }

    private boolean initiatePayment(Employee employee, BigDecimal netSalary)throws BadRequestException {
    	try {
        
        if (netSalary.compareTo(BigDecimal.ZERO) > 0) {
            
            System.out.println("Payment of " + netSalary + " initiated for employee " + employee.getEmployeeId());
            sendEmployeeNotification(employee, "Payment Processed", "Your salary payment has been processed successfully.");
            return true; 
        } else {
           
            System.out.println("Payment initiation failed for employee " + employee.getEmployeeId() + ": Negative or zero amount");
            return false; 
        }
    	}
    	catch (Exception e) {
    		throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to Intiate payment", e.getMessage());
    	}
    }

   
    
	 @Override
	    public void addBenefit(BenefitsDTO benefitDTO)throws InternalServerErrorException {
		 try {
		 	auditTrailsService.logPayrollProcessorActivity("Benefit added.");
	        benefitsService.addBenefit(benefitDTO);
	        
	        sendPayrollProcessorNotifications("Benefit Added", "New benefits have been added to the system.", 1L); // Assuming payroll processor ID is 1
	        
	        sendAdminNotification("Benefit Added", "New benefits have been added to the system.");
		 }
		 catch (Exception e) {
	         	throw new InternalServerErrorException("Internal server error.Failed to add benefit");
	         }
	    }

	    @Override
	    public void updateBenefit(Long benefitId, BenefitsDTO updatedBenefitDTO)throws ResourceNotFoundException {
	    	try {
	        benefitsService.updateBenefit(benefitId, updatedBenefitDTO);
	        auditTrailsService.logPayrollProcessorActivity("Benefit updated with Id : "+benefitId+".");
	        
	        sendPayrollProcessorNotifications("Benefit Updated", "Benefits information has been updated successfully.", 1L); // Assuming payroll processor ID is 1
	        
	        sendAdminNotification("Benefit Updated", "Benefits information has been updated successfully.");
	    	}
	    	catch (Exception e) {
				throw new  ResourceNotFoundException(" id not found", "ID", benefitId);
	    	}
	    }
	    
	    @Override
	    public List<BenefitsDTO> getAllBenefits()throws InternalServerErrorException {
	    	try {
	    	auditTrailsService.logPayrollProcessorActivity("All benefits retrieved.");
	        return benefitsService.getAllBenefits();
	    	}
	    	catch (Exception e) {
	         	throw new InternalServerErrorException("Internal server error.Failed to retrive benefits");
	         }
	    }
	    @Override
	    public BenefitsDTO getBenefitById(Long benefitId)throws ResourceNotFoundException {
	    	try {
	    	 auditTrailsService.logPayrollProcessorActivity("Benefit retrieved by ID: " + benefitId);
	    	 return benefitsService.getBenefitById(benefitId);
	    	}
	    	catch (Exception e) {
				throw new  ResourceNotFoundException(" id not found", "ID", benefitId);
	    	}
	    }

	    @Override
	    public List<PayrollProcessorDTO> getAllPayrollProcessors() throws InternalServerErrorException {
	        try {
	            List<PayrollProcessor> payrollProcessors = payrollProcessorRepository.findAll();
	            return payrollProcessors.stream()
	                    .map(payrollProcessor -> modelMapper.map(payrollProcessor, PayrollProcessorDTO.class))
	                    .collect(Collectors.toList());
	        } catch (Exception e) {
	            throw new InternalServerErrorException("Failed to retrieve all payroll processors");
	        }
	    }

	    @Override
	    public PayrollProcessorDTO getProcessorById(Long processorId) throws ResourceNotFoundException {
	        try {
	            Optional<PayrollProcessor> payrollProcessorOptional = payrollProcessorRepository.findById(processorId);
	            if (payrollProcessorOptional.isPresent()) {
	                PayrollProcessor payrollProcessor = payrollProcessorOptional.get();
	                return modelMapper.map(payrollProcessor, PayrollProcessorDTO.class);
	            } else {
	                throw new ResourceNotFoundException("Payroll processor not found with ID: " + processorId, "ID", processorId);
	            }
	        } catch (Exception e) {
	            throw new ResourceNotFoundException("Failed to retrieve payroll processor by ", "ID", processorId);
	        }
	    }
	    
	    @Override
	    public List<AuditTrailDTO> getAllAuditTrails() {
	    	List<AuditTrailDTO> auditTrails = auditTrailsService.getAllAuditTrails();
	    	auditTrailsService.logPayrollProcessorActivity("Retrieved all audit trails");
		     return auditTrails;
	    }
	    
	    @Override
	    public List<PayrollRecordDTO> getPayrollRecordsByProcessorId(Long processorId) throws ResourceNotFoundException {
	        try {
	            auditTrailsService.logPayrollProcessorActivity("Payroll records retrieved by processor ID: " + processorId);

	            PayrollProcessor payrollProcessor = payrollProcessorRepository.findById(processorId)
	                    .orElseThrow(() -> new EntityNotFoundException("PayrollProcessor not found with id: " +processorId));

	            List<PayrollRecord> payrollRecords = payrollRecordRepository.findByPayrollProcessor(payrollProcessor);


	            // Filter and fetch only the latest payroll records
	            List<PayrollRecord> latestPayrollRecords = new ArrayList<>();
	            for (PayrollRecord record : payrollRecords) {
	                // Assuming payroll records are sorted by date in descending order
	                if (latestPayrollRecords.isEmpty() || record.getPayrollDate().isAfter(latestPayrollRecords.get(0).getPayrollDate())) {
	                    latestPayrollRecords.clear();
	                    latestPayrollRecords.add(record);
	                } else if (record.getPayrollDate().isEqual(latestPayrollRecords.get(0).getPayrollDate())) {
	                    latestPayrollRecords.add(record);
	                }
	            }

	            // Map the latest payroll records to DTOs
	            return latestPayrollRecords.stream()
	                    .map(payrollRecord -> modelMapper.map(payrollRecord, PayrollRecordDTO.class))
	                    .collect(Collectors.toList());
	        } catch (Exception e) {
	            throw new ResourceNotFoundException("Failed to retrieve payroll records by processor ID: " + processorId, "ID", processorId);
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
	    
	    private void sendPayrollProcessorNotifications(String subject, String body, Long payrollProcessorId) {
	        Optional<PayrollProcessor> payrollProcessorOptional = payrollProcessorRepository.findById(payrollProcessorId);
	        if (payrollProcessorOptional.isPresent()) {
	            PayrollProcessor payrollProcessor = payrollProcessorOptional.get();
	            String payrollProcessorEmail = payrollProcessor.getEmailId(); // Get payroll processor's email address

	            // Send notification to payroll processor
	            notificationService.sendEmail(payrollProcessorEmail, subject, body);
	        } else {
	            System.out.println("Payroll processor not found with ID: " + payrollProcessorId);
	        }
	    }

	    private void sendEmployeeNotification(Employee employee, String subject, String body) {
	        String employeeEmail = employee.getEmailId(); // Assuming Employee entity has an email field
	        notificationService.sendEmail(employeeEmail, subject, body);
	    }

}
