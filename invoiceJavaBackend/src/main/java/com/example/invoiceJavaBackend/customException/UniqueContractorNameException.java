package com.example.invoiceJavaBackend.customException;

public class UniqueContractorNameException extends RuntimeException{

    public UniqueContractorNameException() {}

    public UniqueContractorNameException(String message) {
        super(message);
    }
    
}
