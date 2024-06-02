package com.hexaware.payrollmanagementsystem.dto;

import com.hexaware.payrollmanagementsystem.entity.UserRoles;

import jakarta.validation.constraints.NotNull;

public class UserRoleDTO {
    private Long id;
    @NotNull(message = "Name cannot be null")
    private UserRoles name;
	public UserRoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserRoleDTO(Long id, UserRoles name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserRoles getName() {
		return name;
	}
	public void setName(UserRoles name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "UserRoleDTO [id=" + id + ", name=" + name + "]";
	}
    
}