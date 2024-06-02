package com.hexaware.payrollmanagementsystem.controller;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.TimeSheetDTO;
import com.hexaware.payrollmanagementsystem.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timesheets")
@CrossOrigin("*")
public class TimeSheetController {

    private final TimeSheetService timeSheetService;

    @Autowired
    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<TimeSheetDTO>> getAllTimeSheets() {
        List<TimeSheetDTO> timeSheets = timeSheetService.getAllTimeSheets();
        return ResponseEntity.ok(timeSheets);
    }

    @PutMapping("/{timeSheetId}")
    public ResponseEntity<Void> updateTimeSheet(@PathVariable Long timeSheetId, @RequestBody TimeSheetDTO updatedTimeSheetDTO) throws ResourceNotFoundException {
        timeSheetService.updateTimeSheet(timeSheetId, updatedTimeSheetDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{timeSheetId}")
    public ResponseEntity<Void> deleteTimeSheet(@PathVariable Long timeSheetId) throws InternalServerErrorException, ResourceNotFoundException {
        timeSheetService.deleteTimeSheet(timeSheetId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{timeSheetId}")
    public ResponseEntity<TimeSheetDTO> getTimeSheetById(@PathVariable Long timeSheetId) throws ResourceNotFoundException {
        TimeSheetDTO timeSheetDTO = timeSheetService.getTimeSheetById(timeSheetId);
        return ResponseEntity.ok().body(timeSheetDTO);
    }
    
    @GetMapping("/{employeeId}/totalHoursWorked")
    public ResponseEntity<?> getTotalHoursWorkedByEmployee(@PathVariable Long employeeId) throws ResourceNotFoundException {
        
            int totalHoursWorked = timeSheetService.getTotalHoursWorkedByEmployee(employeeId);
            return ResponseEntity.ok(totalHoursWorked);
        } 
}
