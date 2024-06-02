package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.dto.UserRoleDTO;
import com.hexaware.payrollmanagementsystem.entity.UserRole;
import com.hexaware.payrollmanagementsystem.entity.UserRoles;
import com.hexaware.payrollmanagementsystem.repository.UserRoleRepository;
import com.hexaware.payrollmanagementsystem.service.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleDTO getUserRoleById(Long userRoleId) {
        UserRole userRole = userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new RuntimeException("User role not found with id: " + userRoleId));
        return modelMapper.map(userRole, UserRoleDTO.class);
    }

    @Override
    public UserRoleDTO createUserRole(UserRoleDTO userRoleDTO) {
        UserRole userRole = modelMapper.map(userRoleDTO, UserRole.class);
        UserRole savedUserRole = userRoleRepository.save(userRole);
        return modelMapper.map(savedUserRole, UserRoleDTO.class);
    }

    @Override
    public UserRoleDTO updateUserRole(UserRoleDTO userRoleDTO) {
        UserRole existingUserRole = userRoleRepository.findById(userRoleDTO.getId())
                .orElseThrow(() -> new RuntimeException("User role not found with id: " + userRoleDTO.getId()));

        // Update the existing user role with the new data
        existingUserRole.setName(userRoleDTO.getName());

        // Save the updated user role
        UserRole updatedUserRole = userRoleRepository.save(existingUserRole);
        return modelMapper.map(updatedUserRole, UserRoleDTO.class);
    }

    @Override
    public void deleteUserRole(Long userRoleId) {
        userRoleRepository.deleteById(userRoleId);
    }

    @Override
    public UserRole getUserRoleByName(UserRoles name) {
        return userRoleRepository.findByName(name);
    }
}
