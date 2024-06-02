package com.hexaware.payrollmanagementsystem.service.serviceimpl;

import com.hexaware.payrollmanagementsystem.customexceptions.InternalServerErrorException;
import com.hexaware.payrollmanagementsystem.customexceptions.ResourceNotFoundException;
import com.hexaware.payrollmanagementsystem.dto.PayrollRecordDTO;
import com.hexaware.payrollmanagementsystem.entity.Employee;
import com.hexaware.payrollmanagementsystem.entity.LeaveRequest;
import com.hexaware.payrollmanagementsystem.entity.Manager;
import com.hexaware.payrollmanagementsystem.entity.PayrollProcessor;
import com.hexaware.payrollmanagementsystem.entity.PayrollRecord;
import com.hexaware.payrollmanagementsystem.entity.TimeSheet;
import com.hexaware.payrollmanagementsystem.repository.EmployeeRepository;
import com.hexaware.payrollmanagementsystem.repository.LeaveRequestRepository;
import com.hexaware.payrollmanagementsystem.repository.ManagerRepository;
import com.hexaware.payrollmanagementsystem.repository.PayrollProcessorRepository;
import com.hexaware.payrollmanagementsystem.repository.PayrollRecordRepository;
import com.hexaware.payrollmanagementsystem.repository.TimeSheetRepository;
import com.hexaware.payrollmanagementsystem.service.PayrollRecordService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollRecordServiceImpl implements PayrollRecordService {

    private final PayrollRecordRepository payrollRecordRepository;
    private final ModelMapper modelMapper;
    private final PayrollProcessorRepository payrollProcessorRepository; // Injected service
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    private final ManagerRepository managerRepository;
    private final TimeSheetRepository timeSheetRepository;

    @Autowired
    public PayrollRecordServiceImpl(PayrollRecordRepository payrollRecordRepository, 
                                    ModelMapper modelMapper, 
                                    PayrollProcessorRepository payrollProcessorRepository,EmployeeRepository employeeRepository
                                    ,LeaveRequestRepository leaveRequestRepository,ManagerRepository managerRepository,TimeSheetRepository timeSheetRepository) {
        this.payrollRecordRepository = payrollRecordRepository;
        this.modelMapper = modelMapper;
        this.payrollProcessorRepository = payrollProcessorRepository; // Initialize the injected service
        this.employeeRepository=employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.managerRepository = managerRepository;
        this.timeSheetRepository = timeSheetRepository;
    }


    @Override
    public List<PayrollRecordDTO> getAllPayrollRecords() throws InternalServerErrorException{
    	try {
        List<PayrollRecord> payrollRecords = payrollRecordRepository.findAll();
        return payrollRecords.stream()
                .map(payrollRecord -> modelMapper.map(payrollRecord, PayrollRecordDTO.class))
                .collect(Collectors.toList());
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Internal server error.Failed to retrive all payroll records");
         }
    }

    
    //Manager ServiceIMPL
    @Override
    public List<PayrollRecordDTO> reviewTeamPayrolls(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + managerId));

        List<Employee> employees = employeeRepository.findByManager(manager);

        List<PayrollRecordDTO> latestPayrollRecords = new ArrayList<>();
        for (Employee employee : employees) {
            // Fetch the latest payroll record for each employee
            PayrollRecord latestPayrollRecord = payrollRecordRepository.findTopByEmployeeOrderByPayrollDateDesc(employee);

            if (latestPayrollRecord != null) {
                latestPayrollRecords.add(modelMapper.map(latestPayrollRecord, PayrollRecordDTO.class));
            }
        }

        return latestPayrollRecords;
    }

    //ProcessorIMPL
    @Override
    public PayrollRecordDTO calculatePayroll() throws InternalServerErrorException {
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate lastMonth = currentDate.minusMonths(1);

            List<Employee> employees = employeeRepository.findAll();

            PayrollProcessor payrollProcessor = payrollProcessorRepository.findById(1L)
                    .orElseThrow(() -> new EntityNotFoundException("PayrollProcessor not found with id: 1"));

            // Fetch leave requests for the last month
            List<LeaveRequest> leaveRequests = leaveRequestRepository.findByStartDateBetweenAndEndDateBetween(
                    lastMonth.withDayOfMonth(1),
                    lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));

            List<PayrollRecord> payrollRecords = new ArrayList<>();
            for (Employee employee : employees) {
                PayrollRecord payrollRecord = new PayrollRecord();
                payrollRecord.setPayrollProcessor(payrollProcessor);
                payrollRecord.setEmployee(employee);
                payrollRecord.setManager(employee.getManager());

                BigDecimal leaveDeduction = BigDecimal.ZERO;
                BigDecimal salary = employee.getSalary() != null ? BigDecimal.valueOf(employee.getSalary()) : BigDecimal.ZERO;

                // Deduct leave only for leaves taken in the last month
                if (employee.getTotalLeaves() < 0) {
                    for (LeaveRequest leaveRequest : leaveRequests) {
                        if (leaveRequest.getEmployee().getEmployeeId().equals(employee.getEmployeeId()) &&
                                leaveRequest.getStartDate().getMonth().equals(lastMonth.getMonth())) {
                            leaveDeduction = leaveDeduction.add(BigDecimal.valueOf(leaveRequest.getLeavesTaken()));
                        }
                    }

                    leaveDeduction = leaveDeduction.multiply(salary).divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP);
                }

                // Calculate total hours worked and overtime hours
                BigDecimal totalRegularHours = BigDecimal.ZERO;
                BigDecimal totalOvertimeHours = BigDecimal.ZERO;
                List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeEmployeeIdAndStartDateBetween(
                        employee.getEmployeeId(),
                        lastMonth.withDayOfMonth(1),
                        lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));

                BigDecimal regularHoursPay = BigDecimal.ZERO; // Initialize regular hours pay

                for (TimeSheet timeSheet : timeSheets) {
                    // Check if the time sheet is from the last month
                    if (timeSheet.getStartDate().getMonth().equals(lastMonth.getMonth())) {
                        totalRegularHours = totalRegularHours.add(BigDecimal.valueOf(timeSheet.getTotalHoursWorked()));
                        totalOvertimeHours = totalOvertimeHours.add(BigDecimal.valueOf(timeSheet.getOverTime()));

                        // Calculate hourly rate deduction for less than 40 hours for each timesheet record
                        BigDecimal hourlyRate = salary.divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP).divide(BigDecimal.valueOf(8), RoundingMode.HALF_UP);
                        if (BigDecimal.valueOf(timeSheet.getTotalHoursWorked()).compareTo(BigDecimal.valueOf(40)) < 0) {
                            BigDecimal hoursShort = BigDecimal.valueOf(40).subtract(BigDecimal.valueOf(timeSheet.getTotalHoursWorked()));
                            regularHoursPay = regularHoursPay.add(hourlyRate.multiply(hoursShort));  // Accumulate regular hours pay
                        }
                    }
                }

                // Calculate overtime pay
                BigDecimal overtimeHourlyRate = salary.divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP).divide(BigDecimal.valueOf(8), RoundingMode.HALF_UP);
                BigDecimal overtimePay = overtimeHourlyRate.multiply(totalOvertimeHours);
                
                // Calculate total deductions, allowances, tax, and net salary
                BigDecimal taxRate = BigDecimal.valueOf(0.15);
                BigDecimal taxAmount = salary.multiply(taxRate);
                BigDecimal totalDeductions = leaveDeduction.add(regularHoursPay).add(taxAmount);
                BigDecimal allowances = BigDecimal.valueOf(1000.0);
                BigDecimal netSalary = salary.subtract(totalDeductions).add(allowances).add(overtimePay);
                System.out.println("over time"+overtimeHourlyRate);
                payrollRecord.setBasicSalary(salary);
                payrollRecord.setNetSalary(netSalary);
                payrollRecord.setTax(taxAmount);
                payrollRecord.setAllowances(allowances);
                payrollRecord.setDeductions(totalDeductions);
                payrollRecord.setOvertimePay(overtimePay);
                payrollRecord.setPayrollDate(currentDate);
                payrollRecord.setStatus("PENDING");
                System.out.println("overtime hourly "+regularHoursPay);
                System.out.println("oevrtime"+overtimePay);

                payrollRecords.add(payrollRecord);
            }

            payrollRecords = payrollRecordRepository.saveAll(payrollRecords);

            PayrollRecordDTO aggregatedPayroll = new PayrollRecordDTO();
            aggregatedPayroll.setPayrollRecords(payrollRecords.stream()
                    .map(payrollRecord -> modelMapper.map(payrollRecord, PayrollRecordDTO.class))
                    .collect(Collectors.toList()));

            return aggregatedPayroll;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to Calculate: " + e.getMessage());
        }
    }

    
    @Override
    public PayrollRecordDTO verifyPayrollData() {
        BigDecimal tolerance = new BigDecimal("0.01"); // Define your tolerance level here

        List<PayrollRecord> payrollRecords = payrollRecordRepository.findAll();

        for (PayrollRecord payrollRecord : payrollRecords) {
            if (!"VERIFIED".equals(payrollRecord.getStatus()) && !"PAID".equals(payrollRecord.getStatus())) {
                BigDecimal storedNetSalary = payrollRecord.getNetSalary();

                if (storedNetSalary == null) {
                    payrollRecord.setStatus("Verification Failed (Net Salary is Null)");
                    System.out.println("Salary is null " + storedNetSalary);
                } else {
                    BigDecimal calculatedNetSalary = calculateNetSalaryFromStoredData(payrollRecord);

                    if (calculatedNetSalary != null && calculatedNetSalary.subtract(storedNetSalary).abs().compareTo(tolerance) <= 0) {
                        payrollRecord.setStatus("VERIFIED");
                    } else {
                        payrollRecord.setStatus("Verification Failed ");
                        System.out.println("Stored Salary: " + storedNetSalary);
                        System.out.println("Calculated Salary: " + calculatedNetSalary);
                    }
                }
            }
        }

        payrollRecordRepository.saveAll(payrollRecords);

        return null;
    }

    private BigDecimal calculateNetSalaryFromStoredData(PayrollRecord payrollRecord)throws InternalServerErrorException {
    	try {

        BigDecimal basicSalary = payrollRecord.getBasicSalary();
        BigDecimal tax = payrollRecord.getTax();
        BigDecimal allowances = payrollRecord.getAllowances();
        BigDecimal deductions = payrollRecord.getDeductions();
        BigDecimal overTimePay = payrollRecord.getOvertimePay();

        if (basicSalary == null || tax == null || allowances == null || deductions == null || overTimePay == null) {

            return null;
        }

        BigDecimal netSalary = basicSalary.subtract(deductions).add(allowances).add(overTimePay);
        return netSalary;
    	}
    	catch (Exception e) {
         	throw new InternalServerErrorException("Failed to Calculate");
         }
    }
    
    @Override
    public List<PayrollRecordDTO> getPayrollRecordsByProcessorId(Long processorId) throws ResourceNotFoundException {
    	try{PayrollProcessor processor = payrollProcessorRepository.findById(processorId)
                .orElseThrow(() -> new EntityNotFoundException("processor not found with id: " + processorId));

        List<PayrollRecord> payrollRecords = payrollRecordRepository.findByPayrollProcessor(processor);
        

        return payrollRecords.stream()
                .map(payrollRecord -> modelMapper.map(payrollRecord, PayrollRecordDTO.class))
                .collect(Collectors.toList());
    	}
    	catch (Exception e)
    	{
    		throw new  ResourceNotFoundException("Records  not found ","with ID: " , processorId);
    	}
    }

}
