package com.hexaware.payrollmanagementsystem.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="payrollprocessor")
public class PayrollProcessor {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processor_id")
    private Long processorId;

	@Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email_id")
    private String emailId;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    

    // Assuming Admin entity exists for Admin-related functionalities
    
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Relations for Payroll Processing and Verification
    //@JsonManagedReference
    @OneToMany(mappedBy = "payrollProcessor")
    private List<PayrollRecord> payrollRecords;

    // Relations for Notifications
    //@OneToMany(mappedBy = "payrollProcessor")
    //private List<Notification> notifications;

    // Relations for Audit Trail (assuming AuditLog entity exists)
   // @OneToMany(mappedBy = "payrollProcessor")
    //private List<AuditTrail> auditLogs;
    
    //@JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "policy_id")
    private PayrollPolicy payrollPolicy;

	public PayrollProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayrollProcessor(Long processorId, String firstName, String lastName, String emailId, String phoneNumber,
			Admin admin, List<PayrollRecord> payrollRecords, PayrollPolicy payrollPolicy) {
		super();
		this.processorId = processorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.admin = admin;
		this.payrollRecords = payrollRecords;
		this.payrollPolicy = payrollPolicy;
	}

	public Long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<PayrollRecord> getPayrollRecords() {
		return payrollRecords;
	}

	public void setPayrollRecords(List<PayrollRecord> payrollRecords) {
		this.payrollRecords = payrollRecords;
	}

	public PayrollPolicy getPayrollPolicy() {
		return payrollPolicy;
	}

	public void setPayrollPolicy(PayrollPolicy payrollPolicy) {
		this.payrollPolicy = payrollPolicy;
	}

	@Override
	public String toString() {
		return "PayrollProcessor [processorId=" + processorId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", phoneNumber=" + phoneNumber + ", admin=" + admin + ", payrollRecords="
				+ payrollRecords + ", payrollPolicy=" + payrollPolicy + "]";
	}

}