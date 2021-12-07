package com.example.invoiceJavaBackend.controller;

import javax.persistence.NoResultException;

import com.example.invoiceJavaBackend.customException.UniqueContractorNameException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// return exception info as JSON object
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public static class RestErrorResponse {
        String name;
        String errorValue;

        public RestErrorResponse(String name, String errorValue) {
            this.name = name;
            this.errorValue = errorValue;
        }
    }
    
    // handle empty result exception
    @ExceptionHandler(value = {NoResultException.class})
    protected ResponseEntity<Object> handleEmptyResult(
      RuntimeException ex, WebRequest request) {
        RestErrorResponse restErrorResponse = new RestErrorResponse("NoResultException", "No results found");
        return handleExceptionInternal(ex, restErrorResponse, 
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // handle unique contractor name exception
    @ExceptionHandler(value = {UniqueContractorNameException.class})
    protected ResponseEntity<Object> handleUniqueContructorName(
      RuntimeException ex, WebRequest request) {
        RestErrorResponse restErrorResponse = new RestErrorResponse("UniqueContractorNameException", "This contractor name already exist");
        return handleExceptionInternal(ex, restErrorResponse, 
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
