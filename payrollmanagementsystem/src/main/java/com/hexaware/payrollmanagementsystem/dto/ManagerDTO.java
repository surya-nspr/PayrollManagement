package com.hexaware.payrollmanagementsystem.dto;

import com.hexaware.payrollmanagementsystem.entity.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class ManagerDTO {
    private Long managerId;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;
    @NotNull(message = "Gender cannot be null")
    private Gender gender;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String emailId;
    @NotBlank(message = "Position cannot be blank")
    private String position;
    @Positive(message = "Salary must be positive")
    private Double salary;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;
    @NotNull(message = "Address cannot be null")
    private AddressDTO address;
	public ManagerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ManagerDTO(Long managerId, String firstName, String lastName, LocalDate dateOfBirth, Gender gender,
			String emailId, String position, Double salary, String phoneNumber, AddressDTO address) {
		super();
		this.managerId = managerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.emailId = emailId;
		this.position = position;
		this.salary = salary;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
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
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "ManagerDTO [managerId=" + managerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", emailId=" + emailId + ", position="
				+ position + ", salary=" + salary + ", phoneNumber=" + phoneNumber + ", address=" + address
				+ "]";
	}
    
}