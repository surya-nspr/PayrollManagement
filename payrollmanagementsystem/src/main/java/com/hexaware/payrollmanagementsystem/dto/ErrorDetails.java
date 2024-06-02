package com.hexaware.payrollmanagementsystem.dto;
/*
import java.util.Date;
 
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
 
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
 
    public Date getTimestamp() {
        return timestamp;
    }
 
    public String getMessage() {
        return message;
    }
 
    public String getDetails() {
        return details;
    }
}
*/

import java.time.LocalDateTime;

public class ErrorDetails {
 private LocalDateTime timeStamp;
 private String message;
 private String path;
 private String errorCode;
public ErrorDetails(LocalDateTime timeStamp, String message, String path, String errorCode) {
	super();
	this.timeStamp = timeStamp;
	this.message = message;
	this.path = path;
	this.errorCode = errorCode;
}
public LocalDateTime getTimeStamp() {
	return timeStamp;
}
public void setTimeStamp(LocalDateTime timeStamp) {
	this.timeStamp = timeStamp;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public String getErrorCode() {
	return errorCode;
}
public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
}
 
}
