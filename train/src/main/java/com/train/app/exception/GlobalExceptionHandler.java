package com.train.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(TrainNotFoundException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(TrainNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "T404");
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}

	@ExceptionHandler(JourneyNotfoundException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(JourneyNotfoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "J404");
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
