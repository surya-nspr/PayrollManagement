package com.hexaware.payrollmanagementsystem.repository;

import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {

    List<TimeSheet> findByEmployee(EmployeeDTO employee);

	List<TimeSheet> findByEmployee(Employee employee);

	List<TimeSheet> findByEmployeeEmployeeIdAndStartDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);

	
	
}
