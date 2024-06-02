package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;


public interface AuditTrailService {

	void logAdminActivity(String activity);

	void logPayrollProcessorActivity(String action);

	void logEmployeeActivity(String activity);

	void logManagerActivity(String activity);

	List<AuditTrailDTO> getAllAuditTrails();
}

