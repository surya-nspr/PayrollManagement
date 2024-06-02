package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.dto.UserDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollPolicyDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollProcessorDTO;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AdminDTO;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.dto.ComplianceReportDTO;
import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;
import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;
import com.hexaware.payrollmanagementsystem.service.AdminService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @PostMapping("/addadmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addAdmin(@RequestBody AdminDTO adminDTO) throws InternalServerErrorException {
            adminService.addAdmin(adminDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin added successfully.");
    }
    
    @PostMapping("/manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addManager(@RequestBody ManagerDTO managerDTO) throws InternalServerErrorException {
        adminService.addManager(managerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Manager added successfully.");
    }
    
    @PutMapping("/managers/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateManager(@PathVariable Long managerId,
                                                @RequestBody ManagerDTO managerDTO,
                                                @RequestParam List<String> fieldsToUpdate) throws InternalServerErrorException {
        
            adminService.updateManager(managerDTO, fieldsToUpdate);
            return ResponseEntity.ok("Manager updated successfully.");
    }
    
    @PostMapping("/employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) throws InternalServerErrorException{
        adminService.addEmployeeInformation(employeeDTO);
        return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
    }
    
    @PutMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> manageEmployeeInformation(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDTO, @RequestParam List<String> fieldsToUpdate) throws InternalServerErrorException {
 
            adminService.manageEmployeeInformation(employeeDTO, fieldsToUpdate);
            return ResponseEntity.ok("Employee information updated successfully.");
    }
    
    @PostMapping("/payrollprocessor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addPayrollProcessor(@RequestBody PayrollProcessorDTO payrollProcessorDTO) throws InternalServerErrorException  {
        adminService.addPayrollProcessor(payrollProcessorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Payroll Processor added successfully.");
    }
    
    @PutMapping("/payrollprocessor/{payrollProcessorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updatePayrollProcessor(@PathVariable Long payrollProcessorId,
                                                          @RequestBody PayrollProcessorDTO payrollProcessorDTO,
                                                          @RequestParam List<String> fieldsToUpdate) throws InternalServerErrorException {
        
            adminService.updatePayrollProcessor(payrollProcessorDTO, fieldsToUpdate);
            return ResponseEntity.ok("Payroll Processor updated successfully.");
        
    }
    
    @PostMapping("/department")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addDepartment(@RequestBody DepartmentDTO departmentDTO) throws InternalServerErrorException {
        adminService.addDepartment(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Department added successfully.");
    }

    @PutMapping("/department/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO departmentDTO) throws  InternalServerErrorException {
            adminService.updateDepartment(departmentId, departmentDTO);
            return ResponseEntity.ok("Department updated successfully");
    }
    
    @PostMapping("/adduser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) throws InternalServerErrorException{
        
            adminService.addUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully.");
    }

 
    @PutMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(@PathVariable Long userId, @RequestBody UserDTO updatedUserDTO) throws ResourceNotFoundException {
        adminService.updateUser(userId, updatedUserDTO);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long userId) throws ResourceNotFoundException {
        adminService.deleteUser(userId);
    }

    @GetMapping("/compliancereport")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplianceReportDTO> generateComplianceReport() throws InternalServerErrorException{
        ComplianceReportDTO report = adminService.generateComplianceReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/compliancereports/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ComplianceReportDTO> getAllComplianceReports() throws InternalServerErrorException {
        // Call the service method to fetch all compliance reports
        return adminService.getAllComplianceReports();
    }
    
    @PostMapping("/payroll-policy")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addPayrollPolicy(@RequestBody PayrollPolicyDTO payrollPolicyDTO) {
        try {
            adminService.addPayrollPolicy(payrollPolicyDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/payrollPolicies/{policyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updatePayrollPolicy(@PathVariable Long policyId, @RequestBody PayrollPolicyDTO updatedPolicyDTO) throws ResourceNotFoundException{
            adminService.updatePayrollPolicy(policyId, updatedPolicyDTO);
            return ResponseEntity.ok().build();
    }
    
    @GetMapping("/audittrails/all")
    //@PreAuthorize("hasRole('ADMIN')")
    public List<AuditTrailDTO> getAllAuditTrails() throws InternalServerErrorException{
        // Call the service method to fetch all compliance reports
        return adminService.getAllAuditTrails();
    }

}
