package com.hexaware.payrollmanagementsystem.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "UserRole is required")
    private UserRoleDTO userRole;
    private Set<UserRoleDTO> roles;

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDTO(Long userId, String username, String password, String email, Set<UserRoleDTO> roles,UserRoleDTO userRole) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
		this.userRole=userRole;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<UserRoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(Set<UserRoleDTO> roles) {
		this.roles = roles;
	}
	public UserRoleDTO getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleDTO userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "UserDTO [UserId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", userRole=" + userRole + ", roles=" + roles + "]";
	}
}