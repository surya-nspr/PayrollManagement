package com.hexaware.payrollmanagementsystem.service;

import com.hexaware.payrollmanagementsystem.dto.UserRoleDTO;
import com.hexaware.payrollmanagementsystem.entity.UserRole;
import com.hexaware.payrollmanagementsystem.entity.UserRoles;

public interface UserRoleService {

    UserRoleDTO getUserRoleById(Long userRoleId);

    UserRoleDTO createUserRole(UserRoleDTO userRoleDTO);

    UserRoleDTO updateUserRole(UserRoleDTO userRoleDTO);

    void deleteUserRole(Long userRoleId);

	UserRole getUserRoleByName(UserRoles Name);

}
