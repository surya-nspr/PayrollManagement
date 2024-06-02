package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leave-requests")
@CrossOrigin("*")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO)throws InternalServerErrorException {
        leaveRequestService.submitLeaveRequest(leaveRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
