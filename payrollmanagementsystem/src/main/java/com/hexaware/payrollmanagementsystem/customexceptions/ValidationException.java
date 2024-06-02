package com.hexaware.payrollmanagementsystem.customexceptions;
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
