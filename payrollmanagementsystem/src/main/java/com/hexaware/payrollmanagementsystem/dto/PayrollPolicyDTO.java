package com.hexaware.payrollmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class PayrollPolicyDTO {
    private Long policyId;
    @NotBlank(message = "Policy name cannot be blank")
    private String policyName;
    @NotBlank(message = "Description cannot be blank")
    private String description;
	public PayrollPolicyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PayrollPolicyDTO(Long policyId, String policyName, String description) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.description = description;
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
	@Override
	public String toString() {
		return "PayrollPolicyDTO [policyId=" + policyId + ", policyName=" + policyName + ", description=" + description
				+"]";
	}
    
}