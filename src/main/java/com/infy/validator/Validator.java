package com.infy.validator;

import java.util.List;

import com.infy.exception.MobileServiceException;
import com.infy.model.ServiceRequest;

public class Validator {

	public void validate(ServiceRequest service) throws MobileServiceException {	
		//your code goes here
		if(Boolean.FALSE.equals(this.isValidBrand(service.getBrand()))) {
			throw new MobileServiceException("Sorry! we do not provide service for this brand");
		} else if(Boolean.FALSE.equals(this.isValidIssues(service.getIssues()))) {
			throw new MobileServiceException("Please provide the device only if there are issues");
		} else if(Boolean.FALSE.equals(this.isValidIMEINumber(service.getiMEINumber()))) {
			throw new MobileServiceException("Sorry! we’re not able to detect the IMEI number for this device");
		} else if(Boolean.FALSE.equals(this.isValidCustomerName(service.getCustomerName()))) {
			throw new MobileServiceException("Please provide a valid customer name");
		} else if(Boolean.FALSE.equals(this.isValidContactNumber(service.getContactNumber()))) {
			throw new MobileServiceException("Please provide a valid contact number");
		} else {
			
		}
	}	

	
	// validates the brand
	// brand should always start with a upper case alphabet 
	// and can be followed by one or more alphabets (lower case or upper case) 
	public Boolean isValidBrand(String brand){
		
		if(brand.matches("^[A-Z][A-Za-z]{1,}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// validates the list of issues
	// checks if the list is null or empty
	public Boolean isValidIssues(List<String> issues) {
		
		if(issues.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// validates the IMEINumber
	// it should be a 16 digit number 
	public Boolean isValidIMEINumber(Long iMEINumber) {
		
		String imei = iMEINumber.toString();
		if(imei.matches("^[0-9]{16}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	// validates the contact number
	// should contain 10 numeric characters and should not contain 10 repetitive characters
	public Boolean isValidContactNumber(Long contactNumber) {
		
		String number = contactNumber.toString();
		if(number.length() == 10) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// validates the customer name
	// should contain at least one word and each word separated by a single space should contain at least one letter.
	// the first letter of every word should be an upper case character 
	public Boolean isValidCustomerName(String customerName) {
		
		if(customerName.matches("^[A-Z][a-z]+(?: [A-Z][a-z]+)*$")) {
			return true;
		} else {
			return false;
		}
	}
}
