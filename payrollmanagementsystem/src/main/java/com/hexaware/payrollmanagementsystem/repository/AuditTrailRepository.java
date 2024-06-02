package com.hexaware.payrollmanagementsystem.repository;

/*import com.hexaware.payrollmanagementsystem.entity.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
    List<AuditTrail> findByactivityContainingIgnoreCase(String keyword);
}*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.hexaware.payrollmanagementsystem.entity.AuditTrail;
import com.hexaware.payrollmanagementsystem.entity.UserRole;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
}
