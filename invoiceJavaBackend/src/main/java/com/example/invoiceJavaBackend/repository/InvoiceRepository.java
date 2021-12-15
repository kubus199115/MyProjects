package com.example.invoiceJavaBackend.repository;

import java.util.List;

import com.example.invoiceJavaBackend.dto.InvoiceDTO;
import com.example.invoiceJavaBackend.dto.InvoiceDetailsDTO;

public interface InvoiceRepository {

    public void addInvoice(InvoiceDetailsDTO invoiceDetailsDTO);
    public InvoiceDTO findInvoiceByNumber(String number);
    public List<InvoiceDTO> findAllInvoices();

}
