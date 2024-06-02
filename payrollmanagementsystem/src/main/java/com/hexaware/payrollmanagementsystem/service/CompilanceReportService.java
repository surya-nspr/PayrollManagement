package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.dto.ComplianceReportDTO;

public interface CompilanceReportService {
	 ComplianceReportDTO generateComplianceReport();

	List<ComplianceReportDTO> getAllComplianceReports();

}
