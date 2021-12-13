package com.example.invoiceJavaBackend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import com.example.invoiceJavaBackend.customException.UniqueContractorNameException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.FieldError;

// return exception info
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // handle empty result exception
    @ExceptionHandler(value = {NoResultException.class})
    protected ResponseEntity<Object> handleEmptyResult(
      RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "No results found", 
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // handle unique contractor name exception
    @ExceptionHandler(value = {UniqueContractorNameException.class})
    protected ResponseEntity<Object> handleUniqueContructorName(
      RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "This contractor name already exist", 
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // handle validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error) ->{
        
        String fieldName = ((FieldError) error).getField();
        String message = error.getDefaultMessage();
        errors.put(fieldName, message);
      });

		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

}
