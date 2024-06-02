package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@CrossOrigin("*")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/{managerId}/payrolls")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<PayrollRecordDTO>> reviewTeamPayrolls(@PathVariable("managerId") Long managerId) throws BadRequestException, ResourceNotFoundException{
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setManagerId(managerId);
        List<PayrollRecordDTO> payrollRecordDTO = managerService.reviewTeamPayrolls(managerId);
        return ResponseEntity.ok(payrollRecordDTO);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> rejectLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException {

            managerService.rejectLeaveRequest(leaveRequestDTO);
            return new ResponseEntity<>("Leave request rejected successfully.", HttpStatus.OK);

    }
    
    @GetMapping("/leaverequests/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByManagerId(@PathVariable("managerId") Long managerId) throws ResourceNotFoundException {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setManagerId(managerId);
        List<LeaveRequestDTO> leaveRequests = managerService.getLeaveRequestsByManagerId(managerId);
        return ResponseEntity.ok(leaveRequests);
    }
    
    
    @GetMapping("/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ManagerDTO> getManagerById(@PathVariable Long managerId) throws InternalServerErrorException, ResourceNotFoundException {
        ManagerDTO managerDTO = managerService.getManagerById(managerId);
        return ResponseEntity.ok(managerDTO);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ManagerDTO>> getAllManagers() throws InternalServerErrorException{
        List<ManagerDTO> managers = managerService.getAllManagers();
        return ResponseEntity.ok(managers);
    }
    
    @PostMapping("/leaveRequests/approve")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> approveLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) throws ResourceNotFoundException {
        managerService.approveLeaveRequest(leaveRequestDTO);
        return ResponseEntity.ok().build();
    }
}
