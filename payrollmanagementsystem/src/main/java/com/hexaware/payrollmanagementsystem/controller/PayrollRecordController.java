package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.service.PayrollRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrollrecords")
@CrossOrigin("*")
public class PayrollRecordController {

    private final PayrollRecordService payrollRecordService;

    @Autowired
    public PayrollRecordController(PayrollRecordService payrollRecordService) {
        this.payrollRecordService = payrollRecordService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PayrollRecordDTO>> getAllPayrollRecords() {
        List<PayrollRecordDTO> payrollRecords = payrollRecordService.getAllPayrollRecords();
        return ResponseEntity.ok(payrollRecords);
    }


}
