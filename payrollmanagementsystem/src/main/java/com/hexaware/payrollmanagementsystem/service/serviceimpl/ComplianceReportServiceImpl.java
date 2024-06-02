package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.dto.ComplianceReportDTO;
import com.hexaware.payrollmanagementsystem.entity.ComplianceReport;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.repository.ComplianceReportRepository;
import com.hexaware.payrollmanagementsystem.repository.EmployeeRepository;
import com.hexaware.payrollmanagementsystem.service.CompilanceReportService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplianceReportServiceImpl implements CompilanceReportService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ComplianceReportRepository complianceReportRepository;

    @Autowired
    public ComplianceReportServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, ComplianceReportRepository complianceReportRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.complianceReportRepository = complianceReportRepository;
    }

    @Override
    public ComplianceReportDTO generateComplianceReport()throws InternalServerErrorException {
    	try {
        List<Employee> employees = employeeRepository.findAll();

        int totalLeavesTaken = 0;
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalAnnualSalary = BigDecimal.ZERO;

        for (Employee employee : employees) {

            totalLeavesTaken += employee.getTotalLeaves();

            BigDecimal taxRate = BigDecimal.valueOf(0.15); 
            BigDecimal taxAmount = BigDecimal.valueOf(employee.getSalary()).multiply(taxRate);
            totalTax = totalTax.add(taxAmount);

            totalAnnualSalary = totalAnnualSalary.add(BigDecimal.valueOf( employee.getSalary()).multiply(BigDecimal.valueOf(12))); // Assuming monthly salary
        }

        ComplianceReport complianceReport = new ComplianceReport();
        complianceReport.setTotalLeavesTaken(totalLeavesTaken);
        complianceReport.setTaxReport(totalTax);
        complianceReport.setAnnualSalary(totalAnnualSalary);
        complianceReport.setYear(Year.now());

        ComplianceReport savedComplianceReport = complianceReportRepository.save(complianceReport);

        ComplianceReportDTO complianceReportDTO = modelMapper.map(savedComplianceReport, ComplianceReportDTO.class);

        return complianceReportDTO;
    	}
    	catch (Exception e) {
        	throw new InternalServerErrorException("Failed to geenerate compliance report");
        }
    }

    @Override
    public List<ComplianceReportDTO> getAllComplianceReports() throws InternalServerErrorException{
    	try {
        List<ComplianceReport> reports = complianceReportRepository.findAll();
        return reports.stream()
                .map(report -> modelMapper.map(report, ComplianceReportDTO.class))
                .collect(Collectors.toList());
    }
    catch (Exception e) {
    	throw new InternalServerErrorException("Failed to retreive compliance reports");
    }
}
}
