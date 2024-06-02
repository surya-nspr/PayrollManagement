package com.hexaware.payrollmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressDTO {
	@NotBlank(message = "Id  is required")
    private Long id;
	@NotBlank(message = "Street address is required")
    private String streetAddress;
	@NotBlank(message = "City is required")
    private String city;
	@NotBlank(message = "State is required")
    private String state;
	@NotBlank(message = "Postal code is required")
    private String postalCode;
	@NotBlank(message = " country is required")
    private String country;
	public AddressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressDTO( Long id,String streetAddress,String city, String state,String postalCode,String country) {
		super();
		this.id = id;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", streetAddress=" + streetAddress + ", city=" + city + ", state=" + state
				+ ", postalCode=" + postalCode + ", country=" + country + "]";
	}
    
}