package com.hexaware.payrollmanagementsystem.dto;

import com.hexaware.payrollmanagementsystem.entity.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class EmployeeDTO {
    private Long employeeId;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String emailId;
    @NotBlank(message = "Position is required")
    private String position;
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private Double salary;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;
    @NotNull(message = "Total leaves is required")
    @Positive(message = "Total leaves must be positive")
    private Long totalLeaves;
    @NotNull(message = "Date of joining is required")
    private LocalDate dateOfJoining;

    
    private DepartmentDTO department;
    private ManagerDTO manager;
    private AddressDTO address;

    public EmployeeDTO() {
        super();
    }

    public EmployeeDTO(Long employeeId, String firstName, String lastName, LocalDate dateOfBirth, Gender gender,
            String emailId, String position, Double salary, String phoneNumber, Long totalLeaves, LocalDate dateOfJoining,
            DepartmentDTO department, ManagerDTO manager, AddressDTO address) {
        super();
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.emailId = emailId;
        this.position = position;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.totalLeaves = totalLeaves;
        this.dateOfJoining = dateOfJoining;
        this.department = department;
        this.manager = manager;
        this.address = address;
    }

    // Getters and setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public Long getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(Long totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public ManagerDTO getManager() {
        return manager;
    }

    public void setManager(ManagerDTO manager) {
        this.manager = manager;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EmployeeDTO [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", emailId=" + emailId + ", position="
                + position + ", salary=" + salary + ", phoneNumber=" + phoneNumber + ", totalLeaves=" + totalLeaves
                + ", dateOfJoining=" + dateOfJoining + ", department=" + department + ", manager=" + manager
                + ", address=" + address + "]";
    }
}