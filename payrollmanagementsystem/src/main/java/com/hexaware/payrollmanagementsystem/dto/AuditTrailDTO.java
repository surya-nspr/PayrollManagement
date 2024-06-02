package com.hexaware.payrollmanagementsystem.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuditTrailDTO {
    private Long logId;

    @NotBlank(message = "Activity is required")
    private String activity;
    @CreatedDate
    private LocalDateTime timestamp;
    @NotNull(message = "User role is required")
    private UserRoleDTO userRole;
	public AuditTrailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuditTrailDTO(Long logId, String activity, LocalDateTime timestamp, UserRoleDTO userRole) {
		super();
		this.logId = logId;
		this.activity = activity;
		this.timestamp = timestamp;
		this.userRole = userRole;
	}
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
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
	public UserRoleDTO getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleDTO userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "AuditTrailDTO [logId=" + logId + ", activity=" + activity + ", timestamp=" + timestamp + ", userRole="
				+ userRole + "]";
	}
    
    
}
