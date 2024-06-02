package com.hexaware.payrollmanagementsystem.repository;

import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.LeaveRequest;
import com.hexaware.payrollmanagementsystem.entity.Manager;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {	

	List<LeaveRequest> findByEmployee(Employee employee);

	List<LeaveRequest> findByManager(Manager manager);

	 @Query("SELECT lr FROM LeaveRequest lr WHERE lr.startDate BETWEEN :startDate AND :endDate AND lr.endDate BETWEEN :startDate AND :endDate")
	    List<LeaveRequest> findByStartDateBetweenAndEndDateBetween(LocalDate startDate, LocalDate endDate);

	


}
