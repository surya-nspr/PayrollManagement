package com.hexaware.payrollmanagementsystem.service;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.UserDTO;

public interface UserService {
	   
	    void deleteUser(Long userId) throws ResourceNotFoundException;
	    List<UserDTO> getAllUsers();
		void addUser(UserDTO userDTO);
		void updateUser(Long userId, UserDTO updatedUserDTO);
		UserDTO getUserById(Long userId) throws ResourceNotFoundException;
		Long getEmployeeIdByUserEmail(String userEmail);
		Long getPayrollProcessorIdByEmail(String payrollProcessorEmail);
		Long getManagerIdByEmail(String managerEmail);

}
