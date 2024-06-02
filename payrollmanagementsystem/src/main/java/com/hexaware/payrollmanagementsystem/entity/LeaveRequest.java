package com.hexaware.payrollmanagementsystem.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "leave_request")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type")
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApprovalStatus status;
    
    @Column(name="leaves_taken")
    private Long leavesTaken;
    
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    //@JsonIgnore
    //@JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "leave_request_managers",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private List<Manager> approvingManagers;

    @Column(name = "reason")
    private String reason;

	public LeaveRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveRequest(Long requestId, Employee employee, LocalDate startDate, LocalDate endDate, LeaveType leaveType,
			ApprovalStatus status, Long leavesTaken, Manager manager, List<Manager> approvingManagers, String reason) {
		super();
		this.requestId = requestId;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.status = status;
		this.leavesTaken = leavesTaken;
		this.manager = manager;
		this.approvingManagers = approvingManagers;
		this.reason = reason;
	}



	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public ApprovalStatus getStatus() {
		return status;
	}

	public void setStatus(ApprovalStatus status) {
		this.status = status;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<Manager> getApprovingManagers() {
		return approvingManagers;
	}

	public void setApprovingManagers(List<Manager> approvingManagers) {
		this.approvingManagers = approvingManagers;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/*@Transient
	 public int getYear() {
	        return startDate.getYear();
	    }*/

	public Long getLeavesTaken() {
		return leavesTaken;
	}

	public void setLeavesTaken(Long leavesTaken) {
		this.leavesTaken = leavesTaken;
	}

	@Override
	public String toString() {
		return "LeaveRequest [requestId=" + requestId + ", employee=" + employee + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", leaveType=" + leaveType + ", status=" + status + ", leavesTaken="
				+ leavesTaken + ", manager=" + manager + ", approvingManagers=" + approvingManagers + ", reason="
				+ reason +  "]";
	}

}