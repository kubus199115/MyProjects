package com.example.invoiceJavaBackend.customException;

public class UniqueContractorNameException extends Exception{

    public UniqueContractorNameException() {}

    public UniqueContractorNameException(String message) {
        super(message);
    }
    
}
