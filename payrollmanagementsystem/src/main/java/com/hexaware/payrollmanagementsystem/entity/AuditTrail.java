package com.hexaware.payrollmanagementsystem.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_trail")
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

   /* @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "payroll_processor_id")
    private PayrollProcessor payrollProcessor;
*/

    @ManyToOne
    @JoinTable(name = "audit_trail_user_role",
    joinColumns = @JoinColumn(name = "log_id"),
    inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private UserRole userRole;
    
    @Column(name = "activity")
    private String activity;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

	public AuditTrail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuditTrail(Long logId, UserRole userRole, String activity, LocalDateTime timestamp) {
		super();
		this.logId = logId;
		this.userRole = userRole;
		this.activity = activity;
		this.timestamp = timestamp;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "AuditTrail [logId=" + logId + ", userRole=" + userRole + ", activity=" + activity + ", timestamp="
				+ timestamp + "]";
	}

}