package com.hexaware.payrollmanagementsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.Manager;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	//List<Employee> findByDepartment(DepartmentDTO department);

	List<Employee> findByManager(Manager manager);

	//List<Employee> findByManager(ManagerDTO manager);

	Optional<Employee> findByEmailId(String userEmployeeEmail);

	
    
}