package com.hexaware.payrollmanagementsystem.controller;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.service.AuditTrailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
public class AuditTrailController {

    private final AuditTrailService auditTrailService;

    @Autowired
    public AuditTrailController(AuditTrailService auditTrailService) {
        this.auditTrailService = auditTrailService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuditTrailDTO>> getAllAuditTrails() throws InternalServerErrorException{
        List<AuditTrailDTO> auditTrails = auditTrailService.getAllAuditTrails();
        return ResponseEntity.ok(auditTrails);
    }

}
