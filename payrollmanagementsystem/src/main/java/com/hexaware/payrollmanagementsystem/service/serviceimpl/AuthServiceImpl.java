package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.dto.JWTAuthResponse;
import com.hexaware.payrollmanagementsystem.dto.LoginDto;
import com.hexaware.payrollmanagementsystem.dto.RegisterDto;
import com.hexaware.payrollmanagementsystem.dto.UserDTO;
import com.hexaware.payrollmanagementsystem.dto.UserRoleDTO;
import com.hexaware.payrollmanagementsystem.entity.User;
import com.hexaware.payrollmanagementsystem.entity.UserRole;
import com.hexaware.payrollmanagementsystem.entity.UserRoles;
import com.hexaware.payrollmanagementsystem.repository.UserRepository;
import com.hexaware.payrollmanagementsystem.repository.UserRoleRepository;
import com.hexaware.payrollmanagementsystem.security.JwtTokenProvider;
import com.hexaware.payrollmanagementsystem.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
    }
    
   /* @Override
    public JWTAuthResponse login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByName(dto.getUserName()).orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "User not found"));
        UserDTO userDto = new UserDTO();
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUsername());
        
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        
        // Assuming the user's role will not change frequently, use the role set during registration
        userDto.setUserRole(user.getUserRole());
        
        return new JWTAuthResponse(token, userDto);
    }*/
    @Override
    public JWTAuthResponse login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "User not found"));

        // Map UserRole to UserRoleDTO
        UserRoleDTO userRoleDTO = modelMapper.map(user.getUserRole(), UserRoleDTO.class);

        // Map User to UserDTO
        UserDTO userDto = modelMapper.map(user, UserDTO.class);

        // Set UserRoleDTO to UserDTO
        userDto.setUserRole(userRoleDTO);

        return new JWTAuthResponse(token, userDto);
    }



    @Override
    public String register(RegisterDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // Fetch role from DTO and assign it to the user
        UserRole userRole = userRoleRepository.findByName(UserRoles.valueOf(dto.getRole().toUpperCase()));
        if (userRole == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found");
        }
        user.setUserRole(userRole);
        
        userRepository.save(user);
        return "Registration successful!";
    }



}
