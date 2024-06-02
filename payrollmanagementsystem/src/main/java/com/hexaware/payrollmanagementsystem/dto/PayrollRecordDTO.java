package com.hexaware.payrollmanagementsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PayrollRecordDTO {
	
    
	 private Long recordId;

	 private PayrollProcessorDTO payrollProcessor;

	 private EmployeeDTO employee;
	 @NotNull(message = "Basic salary cannot be null")
	 private BigDecimal basicSalary;
	 @NotNull(message = "Allowances cannot be null")
	 private BigDecimal allowances;
	 @NotBlank(message = "Description cannot be blank")
	 private String description;
	 @NotNull(message = "Deductions cannot be null")
	 private BigDecimal deductions;
	 @NotNull(message = "Tax cannot be null")
	 private BigDecimal tax;
	 @NotNull(message = "Overtime pay cannot be null")
	 private BigDecimal overtimePay;
	 @NotNull(message = "Net salary cannot be null")
	 private BigDecimal netSalary;
	 @CreatedDate
	 private LocalDate payrollDate;
	 @NotBlank(message = "Status cannot be blank")
	 private String status;

	 @JsonIgnore
	 private List<PayrollRecordDTO> payrollRecords;

	 private ManagerDTO manager;
	public PayrollRecordDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PayrollRecordDTO(List<PayrollRecordDTO> payrollRecords, Long recordId, PayrollProcessorDTO payrollProcessor,
			EmployeeDTO employee, BigDecimal basicSalary, BigDecimal allowances, String description,
			BigDecimal deductions, BigDecimal tax, BigDecimal overtimePay, BigDecimal netSalary, LocalDate payrollDate,
			String status, ManagerDTO manager) {
		super();
		this.payrollRecords = payrollRecords;
		this.recordId = recordId;
		this.payrollProcessor = payrollProcessor;
		this.employee = employee;
		this.basicSalary = basicSalary;
		this.allowances = allowances;
		this.description = description;
		this.deductions = deductions;
		this.tax = tax;
		this.overtimePay = overtimePay;
		this.netSalary = netSalary;
		this.payrollDate = payrollDate;
		this.status = status;
		this.manager = manager;
	}
	public List<PayrollRecordDTO> getPayrollRecords() {
		return payrollRecords;
	}
	public void setPayrollRecords(List<PayrollRecordDTO> payrollRecords) {
		this.payrollRecords = payrollRecords;
	}
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public PayrollProcessorDTO getPayrollProcessor() {
		return payrollProcessor;
	}
	public void setPayrollProcessor(PayrollProcessorDTO payrollProcessor) {
		this.payrollProcessor = payrollProcessor;
	}
	public EmployeeDTO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}
	public BigDecimal getAllowances() {
		return allowances;
	}
	public void setAllowances(BigDecimal allowances) {
		this.allowances = allowances;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getDeductions() {
		return deductions;
	}
	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getOvertimePay() {
		return overtimePay;
	}
	public void setOvertimePay(BigDecimal overtimePay) {
		this.overtimePay = overtimePay;
	}
	public BigDecimal getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}
	public LocalDate getPayrollDate() {
		return payrollDate;
	}
	public void setPayrollDate(LocalDate payrollDate) {
		this.payrollDate = payrollDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ManagerDTO getManager() {
		return manager;
	}
	public void setManager(ManagerDTO manager) {
		this.manager = manager;
	}
	@Override
	public String toString() {
		return "PayrollRecordDTO [payrollRecords=" + payrollRecords + ", recordId=" + recordId + ", payrollProcessor="
				+ payrollProcessor + ", employee=" + employee + ", basicSalary=" + basicSalary + ", allowances="
				+ allowances + ", description=" + description + ", deductions=" + deductions + ", tax=" + tax
				+ ", overtimePay=" + overtimePay + ", netSalary=" + netSalary + ", payrollDate=" + payrollDate
				+ ", status=" + status + ", manager=" + manager + "]";
	}
    
    
}