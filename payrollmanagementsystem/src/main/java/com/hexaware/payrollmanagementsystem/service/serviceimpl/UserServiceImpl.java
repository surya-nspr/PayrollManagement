package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.UserDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.Manager;
import com.hexaware.payrollmanagementsystem.entity.PayrollProcessor;
import com.hexaware.payrollmanagementsystem.entity.User;
import com.hexaware.payrollmanagementsystem.entity.UserRole;
import com.hexaware.payrollmanagementsystem.repository.EmployeeRepository;
import com.hexaware.payrollmanagementsystem.repository.ManagerRepository;
import com.hexaware.payrollmanagementsystem.repository.PayrollProcessorRepository;
import com.hexaware.payrollmanagementsystem.repository.UserRepository;
import com.hexaware.payrollmanagementsystem.repository.UserRoleRepository;
import com.hexaware.payrollmanagementsystem.service.UserService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final PayrollProcessorRepository payrollProcessorRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder
    		,EmployeeRepository employeeRepository,ManagerRepository managerRepository,PayrollProcessorRepository payrollProcessorRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository= employeeRepository;
        this.managerRepository=managerRepository;
        this.payrollProcessorRepository=payrollProcessorRepository;
    }



    @Override
    public void deleteUser(Long userId) throws ResourceNotFoundException {

        User user = userRepository.findById(userId)
    	        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + userId));

    	    
        userRepository.deleteById(userId);
    }


    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addUser(UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Optional<UserRole> userRoleOptional = userRoleRepository.findById(userDTO.getUserRole().getId());
        if (userRoleOptional.isEmpty()) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found");
        }        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserRole userRole = userRoleOptional.get();
        user.setUserRole(userRole);
        userRepository.save(user);
    }

	 @Override
	 public void updateUser(Long userId, UserDTO updatedUserDTO) {
	    
	     User userToUpdate = userRepository.findById(userId)
	         .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

	     if (updatedUserDTO.getUserRole() != null) {
	         
	         Optional<UserRole> existingUserRoleOptional = userRoleRepository.findById(updatedUserDTO.getUserRole().getId());
	         existingUserRoleOptional.ifPresent(existingUserRole -> {
	           
	             userToUpdate.setUserRole(existingUserRole);
	         });
	     }

	     userToUpdate.setUsername(updatedUserDTO.getUsername());
	     userToUpdate.setEmail(updatedUserDTO.getEmail());

	     String newPassword = updatedUserDTO.getPassword();
	     if (newPassword != null && !newPassword.isEmpty()) {

	         userToUpdate.setPassword(passwordEncoder.encode(newPassword));
	     }

	     userRepository.save(userToUpdate);
	 }
	 
	 @Override
	 public UserDTO getUserById(Long userId) throws ResourceNotFoundException {

		 try {
	     User user = userRepository.findById(userId)
	             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

	     return modelMapper.map(user, UserDTO.class);
		 }
		 catch (Exception e) {
	            throw new ResourceNotFoundException("User not found with"," id: " , userId);
	        }
	 }
	 
	 @Override
	 public Long getEmployeeIdByUserEmail(String userEmail) {

	     Optional<User> userOptional = userRepository.findByEmail(userEmail);
	     if (userOptional.isPresent()) {
	         User user = userOptional.get();
	         String userEmployeeEmail = user.getEmail();

	         Optional<Employee> employeeOptional = employeeRepository.findByEmailId(userEmployeeEmail);
	         if (employeeOptional.isPresent()) {
	             Employee employee = employeeOptional.get();
	             return employee.getEmployeeId(); 
	         }
	     }
	     return null; 
	 }

	 @Override
	 public Long getManagerIdByEmail(String managerEmail) {
	     // Find the user by email in the user repository
	     Optional<User> userOptional = userRepository.findByEmail(managerEmail);
	     if (userOptional.isPresent()) {
	         User user = userOptional.get();
	         String userManagerEmail = user.getEmail();

	         // Fetch the manager from the manager database based on the manager email
	         Optional<Manager> managerOptional = managerRepository.findByEmailId(userManagerEmail);
	         if (managerOptional.isPresent()) {
	             Manager manager = managerOptional.get();
	             return manager.getManagerId(); // Return the manager ID if found
	         }
	     }
	     return null; // Return null if manager ID is not found or emails don't match
	 }

	 @Override
	 public Long getPayrollProcessorIdByEmail(String payrollProcessorEmail) {
	     
	     Optional<User> userOptional = userRepository.findByEmail(payrollProcessorEmail);
	     if (userOptional.isPresent()) {
	         User user = userOptional.get();
	         String userPayrollProcessorEmail = user.getEmail();

	         Optional<PayrollProcessor> payrollProcessorOptional = payrollProcessorRepository.findByEmailId(userPayrollProcessorEmail);
	         if (payrollProcessorOptional.isPresent()) {
	             PayrollProcessor payrollProcessor = payrollProcessorOptional.get();
	             return payrollProcessor.getProcessorId(); 
	     }
	     }
	     return null; 
	 }




}
