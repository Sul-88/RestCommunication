package com.sul.person.service.dto;

/**
 * @author Sulaiman Abboud
 */
public class PersonError extends PersonDTO {

	private String message;

	public PersonError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
