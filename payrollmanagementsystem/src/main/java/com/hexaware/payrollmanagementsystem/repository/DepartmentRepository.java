package com.hexaware.payrollmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.payrollmanagementsystem.dto.DepartmentDTO;
import com.hexaware.payrollmanagementsystem.entity.Department;
import com.hexaware.payrollmanagementsystem.entity.Employee;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {



}
