package com.hexaware.payrollmanagementsystem.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {
	private String resourceName;
	private String fieldName;
	private Long feildValue;
	public ResourceNotFoundException(String resourceName, String fieldName, Long feildValue) {
		super(String.format("%s not found with%s : %s", resourceName,fieldName,feildValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.feildValue = feildValue;
	}
	
	
}
