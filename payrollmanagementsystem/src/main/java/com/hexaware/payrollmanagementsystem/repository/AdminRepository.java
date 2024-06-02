package com.hexaware.payrollmanagementsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.payrollmanagementsystem.entity.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // You can add custom query methods here if needed
}
