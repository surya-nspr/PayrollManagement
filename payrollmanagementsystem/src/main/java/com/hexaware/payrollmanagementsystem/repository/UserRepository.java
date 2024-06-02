package com.hexaware.payrollmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.payrollmanagementsystem.entity.User;
import com.hexaware.payrollmanagementsystem.entity.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
 
    Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
	//Optional<User> findByUserId(Long userId);
	//void deleteById(User user);
	



}