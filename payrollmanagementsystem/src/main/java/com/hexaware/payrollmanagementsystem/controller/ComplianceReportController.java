package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.dto.ComplianceReportDTO;
import com.hexaware.payrollmanagementsystem.service.CompilanceReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceReportController {

    private final CompilanceReportService compilanceReportService;

    @Autowired
    public ComplianceReportController(CompilanceReportService compilanceReportService) {
        this.compilanceReportService = compilanceReportService;
    }

    @GetMapping("/generate")
    public ResponseEntity<ComplianceReportDTO> generateComplianceReport() throws InternalServerErrorException{
        ComplianceReportDTO reportDTO = compilanceReportService.generateComplianceReport();
        return ResponseEntity.ok(reportDTO);
    }
}
