package com.hexaware.payrollmanagementsystem.repository;

import com.hexaware.payrollmanagementsystem.entity.User;
import com.hexaware.payrollmanagementsystem.entity.UserRole;
import com.hexaware.payrollmanagementsystem.entity.UserRoles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

		//Optional<UserRole> findByName(String name);

		UserRole findByName(UserRoles name);
 

}
