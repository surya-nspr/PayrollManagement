package com.hexaware.payrollmanagementsystem.service;

import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollPolicyDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollProcessorDTO;
import com.hexaware.payrollmanagementsystem.dto.ComplianceReportDTO;
import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;
import com.hexaware.payrollmanagementsystem.dto.UserDTO;

import java.util.List;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.AdminDTO;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;

public interface AdminService {

    ComplianceReportDTO generateComplianceReport();

	
	void addEmployeeInformation(EmployeeDTO employeeDTO);

	void manageEmployeeInformation(EmployeeDTO employeeDTO, List<String> fieldsToUpdate);

	void addPayrollPolicy(PayrollPolicyDTO payrollPolicyDTO);

	
	void addDepartment(DepartmentDTO departmentDTO);

	void addManager(ManagerDTO managerDTO);

	void addPayrollProcessor(PayrollProcessorDTO payrollProcessorDTO);

	void updateManager(ManagerDTO managerDTO, List<String> fieldsToUpdate);

	void updatePayrollProcessor(PayrollProcessorDTO payrollProcessorDTO, List<String> fieldsToUpdate);

	void addAdmin(AdminDTO adminDTO);

	void deleteEmployee(Long employeeId) throws ResourceNotFoundException, InternalServerErrorException;

	void updatePayrollPolicy(Long policyId, PayrollPolicyDTO updatedPolicyDTO) throws ResourceNotFoundException;

	List<ComplianceReportDTO> getAllComplianceReports();

	List<AuditTrailDTO> getAllAuditTrails();

	void updateDepartment(Long departmentId, DepartmentDTO departmentDTO) throws InternalServerErrorException;

	void updateUser(Long userId, UserDTO updatedUserDTO) throws ResourceNotFoundException;

	void deleteUser(Long userId) throws ResourceNotFoundException;

	void addUser(UserDTO userDTO) throws InternalServerErrorException;

}
