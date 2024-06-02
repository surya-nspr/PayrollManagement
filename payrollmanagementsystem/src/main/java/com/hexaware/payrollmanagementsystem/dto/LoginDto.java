package com.hexaware.payrollmanagementsystem.dto;
public class LoginDto {
	private String username;
	private String password;
	public LoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	@Override
	public String toString() {
		return "LoginDto [username=" + username + ", password=" + password + "]";
	}

}