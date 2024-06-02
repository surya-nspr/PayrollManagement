package com.hexaware.payrollmanagementsystem.dto;

import com.hexaware.payrollmanagementsystem.entity.ApprovalStatus;
import com.hexaware.payrollmanagementsystem.entity.LeaveType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LeaveRequestDTO {
    private Long requestId;
    @NotNull(message = "Employee information is required")
    private EmployeeDTO employee;
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the future")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    @NotNull(message = "Leave type is required")
    private LeaveType leaveType;
    @NotNull(message = "Status is required")
    private ApprovalStatus status;
    @NotNull(message = "Manager information is required")
    private ManagerDTO manager;
    @NotBlank(message = "Reason is required")
    private String reason;
    private int leavesTaken;
	public LeaveRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LeaveRequestDTO(Long requestId, EmployeeDTO employee, LocalDate startDate, LocalDate endDate,
			LeaveType leaveType, ApprovalStatus status, ManagerDTO manager, String reason, int leavesTaken) {
		super();
		this.requestId = requestId;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.status = status;
		this.manager = manager;
		this.reason = reason;
		this.leavesTaken = leavesTaken;
	}

	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public EmployeeDTO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDTO employee) {
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
	public ManagerDTO getManager() {
		return manager;
	}
	public void setManager(ManagerDTO manager) {
		this.manager = manager;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getLeavesTaken() {
		return leavesTaken;
	}

	public void setLeavesTaken(int leavesTaken) {
		this.leavesTaken = leavesTaken;
	}

	@Override
	public String toString() {
		return "LeaveRequestDTO [requestId=" + requestId + ", employee=" + employee + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", leaveType=" + leaveType + ", status=" + status + ", manager=" + manager
				+ ", reason=" + reason + ", leavesTaken=" + leavesTaken + "]";
	}
}