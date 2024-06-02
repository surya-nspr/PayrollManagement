package com.hexaware.payrollmanagementsystem.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    // Additional fields and mappings
    // User Management
    // Assuming UserRole entity exists for managing user roles
    //@JsonManagedReference
    @OneToMany(mappedBy = "admin")
    private List<User> users;
    
    // Payroll Configuration
    // Assuming PayrollPolicy entity exists for defining payroll policies
    //@JsonManagedReference
    @OneToMany(mappedBy = "admin")
    private List<PayrollPolicy> payrollPolicies;
    
    @OneToMany(mappedBy = "admin")
    private List<Employee> employees;
    
    @OneToMany(mappedBy = "admin")
    private List<ComplianceReport> complianceReports;

    
    public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(Long adminId, String firstName, String lastName, String emailId, String phoneNumber, List<User> users,
			List<PayrollPolicy> payrollPolicies, List<Employee> employees, List<ComplianceReport> complianceReports) {
		super();
		this.adminId = adminId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.users = users;
		this.payrollPolicies = payrollPolicies;
		this.employees = employees;
		this.complianceReports = complianceReports;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<PayrollPolicy> getPayrollPolicies() {
		return payrollPolicies;
	}

	public void setPayrollPolicies(List<PayrollPolicy> payrollPolicies) {
		this.payrollPolicies = payrollPolicies;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<ComplianceReport> getComplianceReports() {
		return complianceReports;
	}

	public void setComplianceReports(List<ComplianceReport> complianceReports) {
		this.complianceReports = complianceReports;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId="
				+ emailId + ", phoneNumber=" + phoneNumber + ", users=" + users + ", payrollPolicies=" + payrollPolicies
				+ ", employees=" + employees + ", complianceReports=" + complianceReports + "]";
	}
	
}