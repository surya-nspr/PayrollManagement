package com.hexaware.payrollmanagementsystem.repository;

import com.hexaware.payrollmanagementsystem.dto.EmployeeDTO;
import com.hexaware.payrollmanagementsystem.dto.ManagerDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollProcessorDTO;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.Manager;
import com.hexaware.payrollmanagementsystem.entity.PayrollProcessor;
import com.hexaware.payrollmanagementsystem.entity.PayrollRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface PayrollRecordRepository extends JpaRepository<PayrollRecord, Long> {


	List<PayrollRecord> findByEmployee(Employee employee);


	List<PayrollRecord> findByPayrollProcessor(PayrollProcessor payrollProcessor);

	List<PayrollRecord> findByManager(Manager manager);

	PayrollRecord findTopByEmployeeOrderByPayrollDateDesc(Employee employee);



}