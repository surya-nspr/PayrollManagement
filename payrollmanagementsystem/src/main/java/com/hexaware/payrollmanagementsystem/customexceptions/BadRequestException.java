package com.hexaware.payrollmanagementsystem.customexceptions;
 
import org.springframework.http.HttpStatus;
public class BadRequestException extends RuntimeException{
	private HttpStatus status;
	private String message;
	public BadRequestException(HttpStatus sataus, String message) {
		super(message);
		this.status = sataus;
		this.message = message;
	}
	public BadRequestException(HttpStatus status, String message, String message2) {
		super(message);
		this.status = status;
		this.message = message2;
	}
	public BadRequestException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}