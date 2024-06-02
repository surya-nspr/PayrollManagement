package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import java.time.LocalDateTime;
/*import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.entity.AuditTrail;
import com.hexaware.payrollmanagementsystem.repository.AuditTrailRepository;
import com.hexaware.payrollmanagementsystem.service.AuditTrailService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditTrailServiceImpl implements AuditTrailService {

    private final AuditTrailRepository auditTrailRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuditTrailServiceImpl(AuditTrailRepository auditTrailRepository, ModelMapper modelMapper) {
        this.auditTrailRepository = auditTrailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AuditTrailDTO> viewSystemActivityLogs() {
        List<AuditTrail> auditTrails = auditTrailRepository.findAll();
        return mapToAuditTrailDTOs(auditTrails);
    }

    @Override
    public List<AuditTrailDTO> searchSystemActivityLogs(String keyword) {
        List<AuditTrail> auditTrails = auditTrailRepository.findByactivityContainingIgnoreCase(keyword);
        return mapToAuditTrailDTOs(auditTrails);
    }

    private List<AuditTrailDTO> mapToAuditTrailDTOs(List<AuditTrail> auditTrails) {
        return auditTrails.stream()
                .map(auditTrail -> modelMapper.map(auditTrail, AuditTrailDTO.class))
                .collect(Collectors.toList());
    }
}*/
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.payrollmanagementsystem.customexceptions.BadRequestException;
import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.dto.AuditTrailDTO;
import com.hexaware.payrollmanagementsystem.entity.AuditTrail;
import com.hexaware.payrollmanagementsystem.entity.UserRole;
import com.hexaware.payrollmanagementsystem.entity.UserRoles;
import com.hexaware.payrollmanagementsystem.repository.AuditTrailRepository;
import com.hexaware.payrollmanagementsystem.service.AuditTrailService;
import com.hexaware.payrollmanagementsystem.service.UserRoleService;

@Service
@Transactional
public class AuditTrailServiceImpl implements AuditTrailService {

    private final AuditTrailRepository auditTrailRepository;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;

    @Autowired
    public AuditTrailServiceImpl(AuditTrailRepository auditTrailRepository, ModelMapper modelMapper,UserRoleService userRoleService) {
        this.auditTrailRepository = auditTrailRepository;
        this.modelMapper = modelMapper;
        this.userRoleService=userRoleService;
    }


    @Override
    public void logAdminActivity(String activity) throws BadRequestException{
    	try {
        UserRole adminRole = userRoleService.getUserRoleByName(UserRoles.ROLE_ADMIN);
        logActivity(activity, adminRole);
    	}
    	catch (Exception e) {
    		throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to log admin activity", e.getMessage());
    	}
        
    }

    @Override
    public void logPayrollProcessorActivity(String activity)throws BadRequestException {
    	try {
        UserRole payrollProcessorRole = userRoleService.getUserRoleByName(UserRoles.ROLE_PAYROLL_PROCESSOR);
        logActivity(activity, payrollProcessorRole);
    	}
    	catch (Exception e) {
    		throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to log PayrollProcessor activity", e.getMessage());
    	}
    }

    @Override
    public void logEmployeeActivity(String activity)throws BadRequestException {
    	try {
    	
        UserRole employeeRole = userRoleService.getUserRoleByName(UserRoles.ROLE_EMPLOYEE);
        logActivity(activity, employeeRole);
    	}
    	catch (Exception e) {
    		throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to log Employee activity", e.getMessage());
    	}
    }

    @Override
    public void logManagerActivity(String activity)throws BadRequestException {
    	try {
        UserRole managerRole = userRoleService.getUserRoleByName(UserRoles.ROLE_MANAGER);
        logActivity(activity, managerRole);
    	}
    	catch (Exception e) {
    		throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to log Manager activity", e.getMessage());
    	}
    }


    @Override
    public List<AuditTrailDTO> getAllAuditTrails()throws InternalServerErrorException {
        try {
            List<AuditTrail> auditTrails = auditTrailRepository.findAll();
            return auditTrails.stream()
                    .map(auditTrail -> modelMapper.map(auditTrail, AuditTrailDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
        	throw new InternalServerErrorException("Failed to get all audit trails");
        }
    }


    private void logActivity(String activity, UserRole userRole)throws BadRequestException {
    	try {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setActivity(activity);
        auditTrail.setUserRole(userRole);
        auditTrail.setTimestamp(LocalDateTime.now()); // Assuming you want to set the current timestamp
        auditTrailRepository.save(auditTrail);
    	}
    	catch (Exception e) {
    		throw new BadRequestException(HttpStatus.BAD_REQUEST, "Failed to log activity", e.getMessage());
    	}
    }
}
