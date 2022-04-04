package com.user.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(UserNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "U404");
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ErrorResponse> exceptionHandlerLogin(LoginFailedException ex){
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "BC404");
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleException(MethodArgumentNotValidException ex){
		ValidationErrorResponse errorResponse =new ValidationErrorResponse("Invalid Arguments Passed", "MA404");
		
		ex.getBindingResult().getFieldErrors().stream().forEach(error -> {
			errorResponse.getInvalidArguments().put(error.getField(), error.getDefaultMessage());
		});
		
		
		return new ResponseEntity<ValidationErrorResponse>(errorResponse, HttpStatus.OK);
	}
}
