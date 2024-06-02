package com.hexaware.payrollmanagementsystem.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "position")
    private String position;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "phone_number")
    private String phoneNumber;
    
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    //@JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;
    
    //@JsonManagedReference 
    @OneToMany(mappedBy = "manager")
    private List<LeaveRequest> leaveRequests;

    //@OneToMany(mappedBy = "manager")
    //private List<Notification> notifications;
    
    //@JsonManagedReference 
    @OneToMany(mappedBy = "manager")
    private List<TimeSheet> timeSheetsToReview;

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager(Long managerId, String firstName, String lastName, LocalDate dateOfBirth, Gender gender,
			String emailId, String position, Double salary, String phoneNumber, Address address,
			List<Employee> employees, List<LeaveRequest> leaveRequests, List<TimeSheet> timeSheetsToReview) {
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
		this.employees = employees;
		this.leaveRequests = leaveRequests;
		this.timeSheetsToReview = timeSheetsToReview;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}

	public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
		this.leaveRequests = leaveRequests;
	}

	public List<TimeSheet> getTimeSheetsToReview() {
		return timeSheetsToReview;
	}

	public void setTimeSheetsToReview(List<TimeSheet> timeSheetsToReview) {
		this.timeSheetsToReview = timeSheetsToReview;
	}

	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", emailId=" + emailId + ", position="
				+ position + ", salary=" + salary + ", phoneNumber=" + phoneNumber + ", address=" + address
				+ ", employees=" + employees + ", leaveRequests=" + leaveRequests + ", timeSheetsToReview="
				+ timeSheetsToReview + "]";
	}
    
}