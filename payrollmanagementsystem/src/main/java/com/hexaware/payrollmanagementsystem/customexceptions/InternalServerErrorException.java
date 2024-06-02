package com.hexaware.payrollmanagementsystem.customexceptions;
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }
}
