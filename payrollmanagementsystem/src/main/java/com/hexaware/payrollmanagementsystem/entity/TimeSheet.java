package com.hexaware.payrollmanagementsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "timesheet")
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_id")
    private Long timeSheetId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "hoursworked")
    private int hoursWorked;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "overtime")
    private int overTime;
    
    @Column(name="total_hours_worked")
    private int totalHoursWorked;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TimeSheetStatus status;

    @ManyToOne
    @JoinTable(name="manager_timesheet_review_mapping",joinColumns = @JoinColumn(name="timesheet_id"),inverseJoinColumns=@JoinColumn(name="manager_id"))
    private Manager manager;

    public TimeSheet() {
        super();
    }

    public TimeSheet(Long timeSheetId, Employee employee, int hoursWorked, LocalDate startDate, LocalDate endDate, int overTime,
            TimeSheetStatus status, Manager manager, int totalHoursWorked) {
        super();
        this.timeSheetId = timeSheetId;
        this.employee = employee;
        this.hoursWorked = hoursWorked;
        this.startDate = startDate;
        this.endDate = endDate;
        this.overTime = overTime;
        this.status = status;
        this.manager = manager;
        this.totalHoursWorked=totalHoursWorked;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getTotalHoursWorked() {
		return totalHoursWorked;
	}

	public void setTotalHoursWorked(int totalHoursWorked) {
		this.totalHoursWorked = totalHoursWorked;
	}

	@Override
	public String toString() {
		return "TimeSheet [timeSheetId=" + timeSheetId + ", employee=" + employee + ", hoursWorked=" + hoursWorked
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", overTime=" + overTime + ", totalHoursWorked="
				+ totalHoursWorked + ", status=" + status + ", manager=" + manager + "]";
	}

}