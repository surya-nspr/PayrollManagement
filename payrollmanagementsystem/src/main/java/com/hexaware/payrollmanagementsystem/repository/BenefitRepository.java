package com.hexaware.payrollmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.payrollmanagementsystem.entity.Benefits;
import com.hexaware.payrollmanagementsystem.entity.Employee;

@Repository
public interface BenefitRepository extends JpaRepository<Benefits, Long> {

}
