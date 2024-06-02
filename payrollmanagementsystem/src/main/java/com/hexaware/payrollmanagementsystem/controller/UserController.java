package com.hexaware.payrollmanagementsystem.controller;

import java.util.List;

import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.UserDTO;
import com.hexaware.payrollmanagementsystem.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDTO updatedUserDTO) {
        try {
            userService.updateUser(userId, updatedUserDTO);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws ResourceNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) throws ResourceNotFoundException {
        
            UserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
       
    }
    
    
    @GetMapping("/email")
    public ResponseEntity<Object> getEmployeeIdByUserEmail(@RequestParam String userEmail) {
        Long employeeId = userService.getEmployeeIdByUserEmail(userEmail);
        if (employeeId != null) {
            return ResponseEntity.ok(employeeId);
        } else {
            String errorMessage = "Employee not found for email: " + userEmail;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
    
    @GetMapping("/manager/{email}")
    public ResponseEntity<Long> getManagerIdByEmail(@PathVariable String email) {
        Long managerId = userService.getManagerIdByEmail(email);
        if (managerId != null) {
            return ResponseEntity.ok(managerId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/payroll-processor/{email}")
    public ResponseEntity<Long> getPayrollProcessorIdByEmail(@PathVariable String email) {
        Long payrollProcessorId = userService.getPayrollProcessorIdByEmail(email);
        if (payrollProcessorId != null) {
            return ResponseEntity.ok(payrollProcessorId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
