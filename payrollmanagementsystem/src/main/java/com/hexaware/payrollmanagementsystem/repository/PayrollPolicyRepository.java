package com.hexaware.payrollmanagementsystem.repository;

import com.hexaware.payrollmanagementsystem.entity.PayrollPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollPolicyRepository extends JpaRepository<PayrollPolicy, Long> {

}