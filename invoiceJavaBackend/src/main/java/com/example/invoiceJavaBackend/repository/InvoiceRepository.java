package com.example.invoiceJavaBackend.repository;

import java.util.List;

import com.example.invoiceJavaBackend.dto.InvoiceDTO;
import com.example.invoiceJavaBackend.dto.InvoiceDetailsDTO;

public interface InvoiceRepository {

    public void addInvoice(InvoiceDetailsDTO invoiceDetailsDTO);
    public InvoiceDTO findInvoiceByNumber(String invoiceNumber);
    public InvoiceDetailsDTO findInvoiceDetailsByNumber(String invoiceNumber);
    public List<InvoiceDTO> findAllInvoices();
    public void removeInvoice(String invoiceNumber);
    public String getNextInvoiceNumber();

}
