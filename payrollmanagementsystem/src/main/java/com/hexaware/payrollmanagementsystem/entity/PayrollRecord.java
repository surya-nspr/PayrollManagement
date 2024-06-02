package com.hexaware.payrollmanagementsystem.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payroll_record")
public class PayrollRecord {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

	//@JsonBackReference
	//@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "processor_id")
    private PayrollProcessor payrollProcessor;
	
	//@JsonBackReference
	//@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "basic_salary")
    private BigDecimal basicSalary;

    @Column(name = "allowances")
    private BigDecimal allowances;

    @Column(name = "description")
    private String description;

    @Column(name = "deductions")
    private BigDecimal deductions;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "overtime_pay")
    private BigDecimal overtimePay;

    @Column(name = "net_salary")
    private BigDecimal netSalary;

    @Column(name = "payroll_date")
    private LocalDate payrollDate;

    @Column(name = "status")
    private String status;

    //@JsonBackReference
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

	public PayrollRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayrollRecord(Long recordId, PayrollProcessor payrollProcessor, Employee employee, BigDecimal basicSalary,
			BigDecimal allowances, String description, BigDecimal deductions, BigDecimal tax, BigDecimal overtimePay,
			BigDecimal netSalary, LocalDate payrollDate, String status, Manager manager) {
		super();
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

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public PayrollProcessor getPayrollProcessor() {
		return payrollProcessor;
	}

	public void setPayrollProcessor(PayrollProcessor payrollProcessor) {
		this.payrollProcessor = payrollProcessor;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
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

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "PayrollRecord [recordId=" + recordId + ", payrollProcessor=" + payrollProcessor + ", employee="
				+ employee + ", basicSalary=" + basicSalary + ", allowances=" + allowances + ", description="
				+ description + ", deductions=" + deductions + ", tax=" + tax + ", overtimePay=" + overtimePay
				+ ", netSalary=" + netSalary + ", payrollDate=" + payrollDate + ", status=" + status + ", manager="
				+ manager + "]";
	}



}