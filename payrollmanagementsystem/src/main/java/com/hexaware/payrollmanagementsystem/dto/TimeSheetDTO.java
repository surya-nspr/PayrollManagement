package com.hexaware.payrollmanagementsystem.dto;

import java.time.LocalDate;
import com.hexaware.payrollmanagementsystem.entity.TimeSheetStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class TimeSheetDTO {
    private Long timeSheetId;
    private EmployeeDTO employee;
    @Positive( message = "Hours worked cannot be negative")
    private int hoursWorked;
    @NotNull(message = "Date cannot be null")
    @Past(message = "Date should be past")
    private LocalDate startDate;
    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date should be past or present")
    private LocalDate endDate;
    @Positive(message = "Overtime hours cannot be negative")
    private int overTime;
    @NotNull(message = "Status cannot be null")
    private TimeSheetStatus status;
	public TimeSheetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TimeSheetDTO(Long timeSheetId, EmployeeDTO employee,int hoursWorked,LocalDate startDate,LocalDate endDate,int overTime,TimeSheetStatus status) {
		super();
		this.timeSheetId = timeSheetId;
		this.employee = employee;
		this.hoursWorked = hoursWorked;
		this.startDate = startDate;
		this.endDate = endDate;
		this.overTime = overTime;
		this.status = status;
	}
	public Long getTimeSheetId() {
		return timeSheetId;
	}
	public void setTimeSheetId(Long timeSheetId) {
		this.timeSheetId = timeSheetId;
	}
	public EmployeeDTO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}
	public int getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
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
	public int getOverTime() {
		return overTime;
	}
	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
	public TimeSheetStatus getStatus() {
		return status;
	}
	public void setStatus(TimeSheetStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TimeSheetDTO [timeSheetId=" + timeSheetId + ", employee=" + employee + ", hoursWorked=" + hoursWorked
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", overTime=" + overTime + ", status=" + status
				+ "]";
	}
}