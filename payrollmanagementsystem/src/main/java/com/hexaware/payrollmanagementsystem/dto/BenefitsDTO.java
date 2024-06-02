package com.hexaware.payrollmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class BenefitsDTO {
    private Long benefitId;
    @NotBlank(message = "Benefit name is required")
    private String benefitName;
    @PositiveOrZero(message = "Amount must be positive or zero")
    private double amount;
    @NotBlank(message = "descripition should not be empty")
    private String description;
    @NotBlank(message = "coverage should not be empty")
    private String coverage;
    
	public BenefitsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BenefitsDTO(Long benefitId, String benefitName, double amount, String description, String coverage
			) {
		super();
		this.benefitId = benefitId;
		this.benefitName = benefitName;
		this.amount = amount;
		this.description = description;
		this.coverage = coverage;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
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
	@Override
	public String toString() {
		return "BenefitsDTO [benefitId=" + benefitId + ", benefitName=" + benefitName + ", amount=" + amount
				+ ", description=" + description + ", coverage=" + coverage + "]";
	}
    
    
}