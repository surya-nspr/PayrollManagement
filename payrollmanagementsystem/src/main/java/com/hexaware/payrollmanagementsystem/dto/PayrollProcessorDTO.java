package com.hexaware.payrollmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PayrollProcessorDTO {
    private Long processorId;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotBlank(message = "Email ID cannot be blank")
    @Email(message = "Invalid email format")
    private String emailId;
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Invalid phone number format")
    private String phoneNumber;
	public PayrollProcessorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PayrollProcessorDTO(Long processorId, String firstName, String lastName, String emailId, String phoneNumber) {
		super();
		this.processorId = processorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
	}

	public Long getProcessorId() {
		return processorId;
	}
	public void setProcessorID(Long processorId) {
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


	@Override
	public String toString() {
		return "PayrollProcessorDTO [processorId=" + processorId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", phoneNumber=" + phoneNumber + "]";
	}
}