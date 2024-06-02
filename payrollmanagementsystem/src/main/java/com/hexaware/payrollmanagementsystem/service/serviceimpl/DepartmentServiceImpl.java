package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;
import com.hexaware.payrollmanagementsystem.entity.Department;
import com.hexaware.payrollmanagementsystem.repository.DepartmentRepository;
import com.hexaware.payrollmanagementsystem.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addDepartment(DepartmentDTO departmentDTO) throws InternalServerErrorException {
        try {
            Department department = modelMapper.map(departmentDTO, Department.class);
            departmentRepository.save(department);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to add department");
        }
    }


    public void updateDepartment(Long departmentId, DepartmentDTO departmentDto) throws ResourceNotFoundException {
        try {
            Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"," with id: " , departmentId));

            // Update the existing department with the new data from departmentDto
            existingDepartment.setDepartmentName(departmentDto.getDepartmentName());

            departmentRepository.save(existingDepartment);
        } catch (ResourceNotFoundException e) {
            // If the department with the given ID is not found
            throw new ResourceNotFoundException("Department not found", "with id: " , departmentId);
        } catch (Exception e) {
            // Handle other exceptions, such as database errors
            throw new RuntimeException("Error updating department: " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteDepartment(Long departmentId) throws InternalServerErrorException,ResourceNotFoundException {
        try {
        	if (departmentRepository.existsById(departmentId)) {
                departmentRepository.deleteById(departmentId);
            } else {
                throw new ResourceNotFoundException("Department", "ID", departmentId);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete department");
        }
        
    }

    @Override
    public DepartmentDTO getDepartmentById(Long departmentId) throws ResourceNotFoundException {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "ID", departmentId));
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() throws BadRequestException,InternalServerErrorException {
        try {
            List<Department> departments = departmentRepository.findAll();
            return departments.stream()
                    .map(department -> modelMapper.map(department, DepartmentDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to retrieve all departments", e.getMessage());
        }
    }

}
