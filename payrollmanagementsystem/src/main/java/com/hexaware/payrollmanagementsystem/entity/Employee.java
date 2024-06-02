package com.hexaware.payrollmanagementsystem.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

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
    
    @Column(name="total_leaves")
    private Long totalLeaves;
    
    @Column(name = "date_of_joining") // New field added
    private LocalDate dateOfJoining; // New field added
    
    //@JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    //@JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private List<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "employee")
    private List<TimeSheet> timeSheets;
    
    //@JsonManagedReference
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayrollRecord> payrollRecords;
    
    //@JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "compliance_report_id")
    private ComplianceReport complianceReport;
    
    //@JsonManagedReference
    //@JsonIgnore
    @ManyToMany(mappedBy = "employees")
    private List<Benefits> benefits;
    
    public Employee() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Employee(Long employeeId, String firstName, String lastName, LocalDate dateOfBirth, Gender gender,
            String emailId, String position, Double salary, String phoneNumber, Long totalLeaves, LocalDate dateOfJoining,
            Department department, Manager manager, List<LeaveRequest> leaveRequests, List<TimeSheet> timeSheets,
            List<PayrollRecord> payrollRecords, Address address, Admin admin, ComplianceReport complianceReport,
            List<Benefits> benefits) {
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
        this.leaveRequests = leaveRequests;
        this.timeSheets = timeSheets;
        this.payrollRecords = payrollRecords;
        this.address = address;
        this.admin = admin;
        this.complianceReport = complianceReport;
        this.benefits = benefits;
    }

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }

    public List<TimeSheet> getTimeSheets() {
        return timeSheets;
    }

    public void setTimeSheets(List<TimeSheet> timeSheets) {
        this.timeSheets = timeSheets;
    }

    public List<PayrollRecord> getPayrollRecords() {
        return payrollRecords;
    }

    public void setPayrollRecords(List<PayrollRecord> payrollRecords) {
        this.payrollRecords = payrollRecords;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public ComplianceReport getComplianceReport() {
        return complianceReport;
    }

    public void setComplianceReport(ComplianceReport complianceReport) {
        this.complianceReport = complianceReport;
    }

    public List<Benefits> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefits> benefits) {
        this.benefits = benefits;
    }

    @Override
    public String toString() {
        return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", emailId=" + emailId + ", position="
                + position + ", salary=" + salary + ", phoneNumber=" + phoneNumber + ", totalLeaves=" + totalLeaves
                + ", dateOfJoining=" + dateOfJoining + ", department=" + department + ", manager=" + manager
                + ", leaveRequests=" + leaveRequests + ", timeSheets=" + timeSheets + ", payrollRecords="
                + payrollRecords + ", address=" + address + ", admin=" + admin + ", complianceReport="
                + complianceReport + ", benefits=" + benefits + "]";
    }
}