package com.user.app.dto;

public class ResponseDTO {
	
	private String message;
	private int statusCode;
	
	
	public ResponseDTO(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
