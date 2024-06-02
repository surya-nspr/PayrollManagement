package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.LeaveRequestDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.dto.TimeSheetDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getemployee/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long employeeId)throws ResourceNotFoundException {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
            if (employeeDTO != null) {
                return ResponseEntity.ok(employeeDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
    }


    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees()throws InternalServerErrorException  {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDTOList);
    }

    @PutMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> manageEmployeeInformation(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDTO, @RequestParam List<String> fieldsToUpdate) throws InternalServerErrorException {
 
            employeeService.manageEmployeeInformation(employeeDTO, fieldsToUpdate);
            return ResponseEntity.ok("Employee information updated successfully.");
    }
    
    @PostMapping("/{id}/leave-request")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> submitLeaveRequest(@PathVariable("id") Employee employeeId,
                                                    @RequestBody LeaveRequestDTO leaveRequest) {
        // Assuming you have a method in the service to submit leave requests
        employeeService.submitLeaveRequest(employeeId, leaveRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/time-sheet/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> submitTimeSheet(@PathVariable("id") Employee employeeId,
                                                @RequestBody TimeSheetDTO timeSheet) throws ResourceNotFoundException {

        employeeService.submitTimeSheet(employeeId, timeSheet);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/time-sheets")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<TimeSheetDTO>> getTimeSheetsByEmployeeId(@PathVariable("id") Long employeeId) throws ResourceNotFoundException {
        List<TimeSheetDTO> timeSheets = employeeService.getTimeSheetsByEmployeeId(employeeId);
        return ResponseEntity.ok(timeSheets);
    }

    @GetMapping("/payrollrecords/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<PayrollRecordDTO>> getPayrollRecordsByEmployeeId(@PathVariable Long employeeId) throws ResourceNotFoundException {
        List<PayrollRecordDTO> payrollRecords = employeeService.getPayrollRecordsByEmployeeId(employeeId);
        if (!payrollRecords.isEmpty()) {
            return new ResponseEntity<>(payrollRecords, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/delete/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long employeeId) throws ResourceNotFoundException {
        employeeService.deleteEmployeeById(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/time-sheets/{timeSheetId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> updateTimeSheet(
                                                 @PathVariable("timeSheetId") Long timeSheetId,
                                                 @RequestBody TimeSheetDTO updatedTimeSheet) throws ResourceNotFoundException {

            // Delegate the update operation to the service layer
            employeeService.updateTimeSheet(timeSheetId, updatedTimeSheet);
            return ResponseEntity.ok().build();
       
    }
    
    @GetMapping("/{employeeId}/total-leaves")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Integer> getTotalLeavesByEmployeeId(@PathVariable Long employeeId) throws ResourceNotFoundException {
    	int totalLeaves = employeeService.getTotalLeavesByEmployeeId(employeeId);
        return ResponseEntity.ok(totalLeaves);
    }

    @GetMapping("/{employeeId}/leaves")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<LeaveRequestDTO>> getLeavesByEmployeeId(@PathVariable Long employeeId) throws ResourceNotFoundException {
       
            List<LeaveRequestDTO> leaveRequests = employeeService.getLeavesByEmployeeId(employeeId);
            return ResponseEntity.ok(leaveRequests);
        
    }

}
