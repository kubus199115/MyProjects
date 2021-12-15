package com.example.invoiceJavaBackend.customException;

public class UniqueInvoiceNumberException extends RuntimeException{

    public UniqueInvoiceNumberException() {
    }

    public UniqueInvoiceNumberException(String message) {
        super(message);
    }
    
}
