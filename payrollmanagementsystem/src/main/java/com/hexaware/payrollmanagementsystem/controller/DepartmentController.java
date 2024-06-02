package com.hexaware.payrollmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;
import com.hexaware.payrollmanagementsystem.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/get/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) throws ResourceNotFoundException {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(departmentId);
        return departmentDTO != null ? ResponseEntity.ok(departmentDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments()throws BadRequestException,InternalServerErrorException {

            List<DepartmentDTO> departmentDTOs = departmentService.getAllDepartments();
            return ResponseEntity.ok(departmentDTOs);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<String> updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO departmentDto) {
        try {
            departmentService.updateDepartment(departmentId, departmentDto);
            return ResponseEntity.ok("Department updated successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating department: " + e.getMessage());
        }
        }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId) throws InternalServerErrorException, ResourceNotFoundException {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department deleted successfully.");
    }
}
