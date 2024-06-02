package com.hexaware.payrollmanagementsystem.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Benefits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benefit_id")
    private Long benefitId;

    @Column(name = "benifit_name")
    private String benefitName;

    private BigDecimal amount;
    
    @Column(name = "description")
    private String description;

    @Column(name = "coverage")
    private String coverage;
    
    //@JsonBackReference
    @ManyToMany
    @JoinTable(
        name = "employee_benefits",
        joinColumns = @JoinColumn(name = "benefit_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;
    
    //@JsonBackReference
    @ManyToMany
    @JoinTable(
        name = "benefit_departments",
        joinColumns = @JoinColumn(name = "benefit_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments;

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Benefits() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Benefits(Long benefitId, String benefitName, BigDecimal amount, String description, String coverage,
			List<Employee> employees) {
		super();
		this.benefitId = benefitId;
		this.benefitName = benefitName;
		this.amount = amount;
		this.description = description;
		this.coverage = coverage;
		this.employees = employees;
	}

	public Long getBenefitId() {
		return benefitId;
	}

	public void setBenefitId(Long benefitId) {
		this.benefitId = benefitId;
	}

	public String getBenefitName() {
		return benefitName;
	}

	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Benefits [BenefitId=" + benefitId + ", benefitName=" + benefitName + ", amount=" + amount + ", description="
				+ description + ", coverage=" + coverage + ", employees=" + employees + "]";
	}

    // Getters and setters
    
}