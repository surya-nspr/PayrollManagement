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
@Table(name = "payroll_policy")
public class PayrollPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    @Column(name = "policy_name")
    private String policyName;

    @Column(name = "description")
    private String description;

    // Relations with other entities
    // Assuming Admin entity exists for managing payroll policies
 // PayrollPolicy entity
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;


    // Assuming PayrollProcessor entity exists for processing payroll based on policies
    @JsonManagedReference
    @OneToMany(mappedBy = "payrollPolicy")
    private List<PayrollProcessor> payrollProcessors;


	public PayrollPolicy() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PayrollPolicy(Long policyId, String policyName, String description, Admin admin,
			List<PayrollProcessor> payrollProcessors) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.description = description;
		this.admin = admin;
		this.payrollProcessors = payrollProcessors;
	}


	public Long getPolicyId() {
		return policyId;
	}


	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}


	public String getPolicyName() {
		return policyName;
	}


	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Admin getAdmin() {
		return admin;
	}


	public void setAdmin(Admin admin) {
		this.admin = admin;
	}


	public List<PayrollProcessor> getPayrollProcessors() {
		return payrollProcessors;
	}


	public void setPayrollProcessors(List<PayrollProcessor> payrollProcessors) {
		this.payrollProcessors = payrollProcessors;
	}


	@Override
	public String toString() {
		return "PayrollPolicy [policyId=" + policyId + ", policyName=" + policyName + ", description=" + description
				+ ", admin=" + admin + ", payrollProcessors=" + payrollProcessors + "]";
	}

}