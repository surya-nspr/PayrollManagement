package com.hexaware.payrollmanagementsystem.service;


public interface NotificationService {
    void sendEmail(String to, String subject, String body);
}


