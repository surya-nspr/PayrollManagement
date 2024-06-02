package com.hexaware.payrollmanagementsystem.service;

import com.hexaware.payrollmanagementsystem.dto.JWTAuthResponse;
import com.hexaware.payrollmanagementsystem.dto.LoginDto;
import com.hexaware.payrollmanagementsystem.dto.RegisterDto;

public interface AuthService {
	JWTAuthResponse login(LoginDto dto);
	String register(RegisterDto dto);
}