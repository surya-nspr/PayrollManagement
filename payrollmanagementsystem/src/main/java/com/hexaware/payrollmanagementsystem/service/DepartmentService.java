package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;

public interface DepartmentService {
    
    void deleteDepartment(Long departmentId) throws InternalServerErrorException, ResourceNotFoundException;
    DepartmentDTO getDepartmentById(Long departmentId) throws ResourceNotFoundException;
    List<DepartmentDTO> getAllDepartments();
	void addDepartment(DepartmentDTO departmentDTO);
	void updateDepartment(Long departmentId, DepartmentDTO departmentDto) throws ResourceNotFoundException;
}
