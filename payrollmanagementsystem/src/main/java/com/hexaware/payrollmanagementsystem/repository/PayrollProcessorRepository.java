package com.hexaware.payrollmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.payrollmanagementsystem.entity.PayrollProcessor;
import com.hexaware.payrollmanagementsystem.entity.PayrollRecord;

@Repository
public interface PayrollProcessorRepository extends JpaRepository<PayrollProcessor, Long>{

	//List<PayrollRecord> findByProcessorId(Long processorId);

	Optional<PayrollProcessor> findByEmailId(String userPayrollProcessorEmail);

}
