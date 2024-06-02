package com.hexaware.payrollmanagementsystem.entity;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "compliance_report")
public class ComplianceReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "year")
    private Year year;

    @Column(name = "total_leaves_taken")
    private int totalLeavesTaken;

    @Column(name = "tax_report")
    private BigDecimal taxReport;

    @Column(name = "salary_timesheets_report")
    private int salaryTimesheetsReport;
    
    @Column(name="annual_salary")
    private BigDecimal annualSalary;
    
    //@JsonManagedReference
    @OneToMany(mappedBy = "complianceReport", cascade = CascadeType.ALL)
    private List<Employee> employees;
    
 // ComplianceReport entity
    //@JsonBackReference
    @ManyToOne
    //@JoinColumn(name = "admin_id")
    //private Admin admin;
    @JoinTable(name = "compliance_report_admin",
    joinColumns = @JoinColumn(name = "report_id"),
    inverseJoinColumns = @JoinColumn(name = "admin_id"))
private Admin admin;

	public ComplianceReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComplianceReport(Long reportId, Year year, int totalLeavesTaken, BigDecimal taxReport,
			int salaryTimesheetsReport, BigDecimal annualSalary, List<Employee> employees, Admin admin) {
		super();
		this.reportId = reportId;
		this.year = year;
		this.totalLeavesTaken = totalLeavesTaken;
		this.taxReport = taxReport;
		this.salaryTimesheetsReport = salaryTimesheetsReport;
		this.annualSalary = annualSalary;
		this.employees = employees;
		this.admin = admin;
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

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public int getSalaryTimesheetsReport() {
		return salaryTimesheetsReport;
	}

	public void setSalaryTimesheetsReport(int salaryTimesheetsReport) {
		this.salaryTimesheetsReport = salaryTimesheetsReport;
	}

	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}

	@Override
	public String toString() {
		return "ComplianceReport [reportId=" + reportId + ", year=" + year + ", totalLeavesTaken=" + totalLeavesTaken
				+ ", taxReport=" + taxReport + ", salaryTimesheetsReport=" + salaryTimesheetsReport + ", annualSalary="
				+ annualSalary + ", employees=" + employees + ", admin=" + admin + "]";
	}
}