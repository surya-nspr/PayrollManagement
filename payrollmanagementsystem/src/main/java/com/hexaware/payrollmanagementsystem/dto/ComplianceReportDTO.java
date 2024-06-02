package com.hexaware.payrollmanagementsystem.dto;

import java.math.BigDecimal;
import java.time.Year;

import jakarta.validation.constraints.NotNull;

public class ComplianceReportDTO {
    private Long reportId;
    @NotNull(message = "Year is required")
    private Year year;
    @NotNull(message = "Total leaves should not be zero")
    private int totalLeavesTaken;
    @NotNull(message = "Tax report is required")
    private BigDecimal taxReport;
    @NotNull(message = "Annual salary is required")
    private BigDecimal annualSalary;

	public ComplianceReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ComplianceReportDTO(Long reportId, Year year, int totalLeavesTaken, BigDecimal taxReport,
			BigDecimal annualSalary) {
		super();
		this.reportId = reportId;
		this.year = year;
		this.totalLeavesTaken = totalLeavesTaken;
		this.taxReport = taxReport;
		this.annualSalary = annualSalary;
	}

	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public int getTotalLeavesTaken() {
		return totalLeavesTaken;
	}
	public void setTotalLeavesTaken(int totalLeavesTaken) {
		this.totalLeavesTaken = totalLeavesTaken;
	}
	public BigDecimal getTaxReport() {
		return taxReport;
	}
	public void setTaxReport(BigDecimal taxReport) {
		this.taxReport = taxReport;
	}

	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}

	@Override
	public String toString() {
		return "ComplianceReportDTO [reportId=" + reportId + ", year=" + year + ", totalLeavesTaken=" + totalLeavesTaken
				+ ", taxReport=" + taxReport + ", annualSalary=" + annualSalary +  "]";
	}
    
}